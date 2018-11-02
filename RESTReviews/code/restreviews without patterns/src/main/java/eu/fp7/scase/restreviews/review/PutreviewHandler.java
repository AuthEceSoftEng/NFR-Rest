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


package eu.fp7.scase.restreviews.review;


import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;
import eu.fp7.scase.restreviews.product.JavaproductModel;

/* This class processes PUT requests for review resources and creates the hypermedia to be returned to the client*/

public class PutreviewHandler{


    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
	private String strResourcePath; //relative path to the current resource
    private JavareviewModel oJavareviewModel;
    private JavaproductModel oJavaproductModel;


    public PutreviewHandler(int productId, int reviewId, JavareviewModel oJavareviewModel, UriInfo oApplicationUri){
        this.oJavareviewModel = oJavareviewModel;
        this.oJavareviewModel.setreviewId(reviewId);
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.strResourcePath = calculateProperResourcePath();
        oJavaproductModel = new JavaproductModel();
        oJavaproductModel.setproductId(productId);
        oJavareviewModel.setproduct(this.oJavaproductModel);
    }

    public String calculateProperResourcePath(){
        if(this.oApplicationUri.getPath().lastIndexOf('/') == this.oApplicationUri.getPath().length() - 1){
            return this.oApplicationUri.getPath().substring(0, this.oApplicationUri.getPath().length() - 1);
        }
        else{
            return this.oApplicationUri.getPath();
        }
    }

    public JavareviewModel putJavareviewModel(){


        return createHypermedia(oHibernateController.putreview(oJavareviewModel));
    }


    /* This function produces hypermedia links to be sent to the client so as it will be able to forward the application state in a valid way.*/
    public JavareviewModel createHypermedia(JavareviewModel oJavareviewModel){

        /* Create hypermedia links towards this specific Resource resource. These can be GET, PUT and/or delete depending on what was specified in the service CIM.*/
        oJavareviewModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Get the review", "GET", "Sibling"));
        oJavareviewModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Update the review", "PUT", "Sibling"));
        oJavareviewModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath), "Delete the review", "DELETE", "Sibling"));


        /* Finally, truncate the current URI so as to point to the resource manager of which this resource is related.
        Then create the hypermedia links towards the parent resources.*/
        int iLastSlashIndex = String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath).lastIndexOf("/");
        oJavareviewModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath).substring(0, iLastSlashIndex), "Create a new review", "POST", "Parent"));
        oJavareviewModel.getlinklist().add(new HypermediaLink(String.format("%s%s", oApplicationUri.getBaseUri(), this.strResourcePath).substring(0, iLastSlashIndex), "Get all reviews of this product", "GET", "Parent"));
        return oJavareviewModel;
    }
}
