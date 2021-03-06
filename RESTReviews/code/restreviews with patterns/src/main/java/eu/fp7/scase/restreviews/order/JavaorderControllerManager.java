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


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;


/* This class defines the web API of the manager order resource. It handles POST and GET HTTP requests, as it is prescribed by the meta-models.*/
@Path("/account/{accountId}/order")
public class JavaorderControllerManager{

    @Context
    private UriInfo oApplicationUri;
		/* This function handles POST requests that are sent with any media type stated in the @Consumes JAX-RS annotation below 
	     and returns any response in any media type stated in the @Produces JAX-RS annotation below.*/
		@Path("/")
		@POST
		@Produces("application/JSON")
		@Consumes("application/JSON")
	    public JavaorderModel postorder(@PathParam("accountId")int accountId, JavaorderModel oJavaorderModel){
	        PostorderHandler oPostorderHandler = new PostorderHandler(accountId, oJavaorderModel, oApplicationUri);
	        return oPostorderHandler.postJavaorderModel();
	    }

    /* This function handles GET requests  
     and returns any response in any media type stated in the @Produces JAX-RS annotation below.*/
	@Path("/")
	@GET
	@Produces("application/JSON")
    public JavaorderModelManager getorderList(@PathParam("accountId")int accountId){
        GetorderListHandler oGetorderListHandler = new GetorderListHandler(accountId, oApplicationUri);
        return oGetorderListHandler.getJavaorderModelManager();
    }

}
