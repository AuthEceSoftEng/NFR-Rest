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
* Programmer		   : RESTful MDE Engine created by Christoforos Zolotas / Design Patterns Layer Implementation created by Dontsios Dimitrios
* Contact			   : christopherzolotas@issel.ee.auth.gr / dontsios@gmail.com
*/


package eu.fp7.scase.restreviews.payment;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;
import eu.fp7.scase.restreviews.utilities.PersistenceUtil;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

/* This class processes search requests for payment resource and creates the hypermedia links with the search results to be returned to the client*/
public abstract class AbstractPostpaymentHandler{

    protected HibernateController oHibernateController;
    protected UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
    protected JavaAlgopaymentModel oJavaAlgopaymentModel;
	protected String searchKeyword;
	protected final static String arrayInvalidSearchTerms[] = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with"};

    public AbstractPostpaymentHandler(String searchKeyword, UriInfo oApplicationUri){
        oJavaAlgopaymentModel = new JavaAlgopaymentModel();
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.searchKeyword = searchKeyword;
    }

    abstract public JavaAlgopaymentModel postpayment();


    /* This function produces hypermedia links to be sent to the client that include all the search results, so as it will be able to forward the application state in a valid way.*/
    abstract public JavaAlgopaymentModel searchDatabase();


    abstract public void validateSearchString();
    
    abstract public boolean isValidSearchKeywordToken(String strSearchKeywordTerm);
}
