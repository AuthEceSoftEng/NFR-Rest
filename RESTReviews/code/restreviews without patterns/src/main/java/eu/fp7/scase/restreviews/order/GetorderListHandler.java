/*
 * ARISTOSTLE UNIVERSITY OF THESSALONIKI
 * Copyright (C) 2015
 * Aristotle University of Thessaloniki
 * Department of Electrical & Computer Engineering
 * Division of Electronics & Computer Engineering
 * Intelligent Systems & Software Engineering Lab
 *
 * Project             : restreviews
 * WorkFile            : 
 * Compiler            : 
 * File Description    : 
 * Document Description: 
* Related Documents	   : 
* Note				   : 
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas
* Contact			   : christopherzolotas@issel.ee.auth.gr
*/


package eu.fp7.scase.restreviews.order;


import javax.ws.rs.core.UriInfo;

import java.util.Iterator;
import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;
import eu.fp7.scase.restreviews.account.JavaaccountModel;

/* This class processes GET requests for order resources and creates the hypermedia to be returned to the client*/
public class GetorderListHandler{

    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavaaccountModel oJavaaccountModel;

    public GetorderListHandler(int accountId, UriInfo oApplicationUri){
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.strResourcePath = calculateProperResourcePath();
        oJavaaccountModel = new JavaaccountModel();
        oJavaaccountModel.setaccountId(accountId);
    }

	public String calculateProperResourcePath(){
    	if(this.oApplicationUri.getPath().lastIndexOf('/') == this.oApplicationUri.getPath().length() - 1){
        	return this.oApplicationUri.getPath().substring(0, this.oApplicationUri.getPath().length() - 1);
    	}
    	else{
        	return this.oApplicationUri.getPath();
    	}
	}

    public JavaorderModelManager getJavaorderModelManager(){


        oJavaaccountModel = oHibernateController.getorderList(oJavaaccountModel);
        return createHypermedia(oJavaaccountModel);
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavaorderModelManager createHypermedia(JavaaccountModel oJavaaccountModel){
        JavaorderModelManager oJavaorderModelManager = new JavaorderModelManager();

        /* Create hypermedia links towards this specific order resource. These must be GET and POST as it is prescribed in the meta-models.*/
        oJavaorderModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get all orders of this account", "GET", "Sibling"));
        oJavaorderModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Create a new order", "POST", "Sibling"));

        /* Then calculate the relative path to any related resource of this one and add for each one a hypermedia link to the Linklist.*/
        String oRelativePath;
        oRelativePath = this.strResourcePath;
        Iterator<JavaorderModel> setIterator = oJavaaccountModel.getSetOfJavaorderModel().iterator();
        while(setIterator.hasNext()){
            JavaorderModel oNextJavaorderModel = new JavaorderModel();
            oNextJavaorderModel = setIterator.next();
            oJavaorderModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s/%d", oApplicationUri.getBaseUri(), oRelativePath, oNextJavaorderModel.getorderId()), String.valueOf(oNextJavaorderModel.getcoupon()), "GET", "Child", oNextJavaorderModel.getorderId()));
        }

        /* Finally calculate the relative path towards the resources of which this one is related and add one hypermedia link for each one of them in the Linklist.*/
		oRelativePath = this.strResourcePath;
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).lastIndexOf("/");
        oJavaorderModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Delete the parent JavaaccountModel", "DELETE", "Parent"));
        oJavaorderModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Get the parent JavaaccountModel", "GET", "Parent"));
        oJavaorderModelManager.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), oRelativePath).substring(0, iLastSlashIndex), "Update the JavaaccountModel", "PUT", "Parent"));
        return oJavaorderModelManager;
    }
}
