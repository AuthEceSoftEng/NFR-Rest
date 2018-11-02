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


package eu.fp7.scase.restreviews.payment;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("/Algopayment")
public class JavaAlgopaymentController{

    @Context
    private UriInfo oApplicationUri;

    //Please add any properties of this controller here.
	/* This function handles http  POST  requests  
    and returns any response formatted as stated in the @Produces JAX-RS annotation below.*/
	@Path("/")
	@POST
	@Produces("application/JSON")
	@Consumes("application/JSON")
    public JavaAlgopaymentModel postpayment(){

        //TODO add any code that uses the according HTTPHandler class in order to satisfy the client request.
		return new JavaAlgopaymentModel(); //TODO change the return value appropriately.

    }
    //Please add the constructors and any functions of this controller here.
}
