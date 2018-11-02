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
public class PostpaymentHandler extends AbstractPostpaymentHandler{

   

    public PostpaymentHandler(String searchKeyword, , UriInfo oApplicationUri){
        super(searchKeyword,oApplicationUri);
    }

    public JavaAlgopaymentModel postpayment(){

        //validate the search keyword
        validateSearchString();


		//Return any results in the hypermedia links form.
        return searchDatabase();
    }


    /* This function produces hypermedia links to be sent to the client that include all the search results, so as it will be able to forward the application state in a valid way.*/
    public JavaAlgopaymentModel searchDatabase(){
		

		return this.oJavaAlgopaymentModel;
	}


    public void validateSearchString(){
        String[] arraySearchKeywordTokens = this.searchKeyword.split(" ");
        this.searchKeyword = "";
        boolean bSearchKeywordIsEmpty = true;
        
        for(int n = 0; n < arraySearchKeywordTokens.length; n++){
            if(isValidSearchKeywordToken(arraySearchKeywordTokens[n])){
                searchKeyword += arraySearchKeywordTokens[n];
                bSearchKeywordIsEmpty = false;
            }
        }
        
        if(bSearchKeywordIsEmpty){
            this.searchKeyword = null;
        }
    }
    
    public boolean isValidSearchKeywordToken(String strSearchKeywordTerm){
        
        for(int n = 0; n < this.arrayInvalidSearchTerms.length; n++){
            if(strSearchKeywordTerm.equalsIgnoreCase(arrayInvalidSearchTerms[n])){
                return false;
            }
        }
        return true;
    }	
}
