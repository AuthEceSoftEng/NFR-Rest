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


package eu.fp7.scase.restreviews.search;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.QueryParam; 

/* JavaAlgosearchController class is responsible to handle incoming HTTP requests for the search resource. More specifically
this resource controller handles search requests by keyword for the following resources/properties:
From resource JavaproductModel :
-- description
-- title
From resource JavareviewModel :
-- title
-- description
*/
@Path("/Algosearch")
public class JavaAlgosearchController{

    @Context
    private UriInfo oApplicationUri;

	/* This function handles http  requests  
    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
	@Path("/")
	@GET
	@Produces("application/JSON")
    public JavaAlgosearchModel getsearch(@QueryParam("searchKeyword") String searchKeyword, @QueryParam("searchProductDescription") String searchProductDescription, @QueryParam("searchProductTitle") String searchProductTitle, @QueryParam("searchReviewTitle") String searchReviewTitle, @QueryParam("searchReviewDescription") String searchReviewDescription){

		//create a new GetsearchHandler
		GetsearchHandler oGetsearchHandler = new GetsearchHandler(searchKeyword, searchProductDescription, searchProductTitle, searchReviewTitle, searchReviewDescription, oApplicationUri);
		return oGetsearchHandler.getsearch();

    }
}
