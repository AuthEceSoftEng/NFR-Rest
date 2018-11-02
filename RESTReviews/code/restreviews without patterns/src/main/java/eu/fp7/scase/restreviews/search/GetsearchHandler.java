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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.core.UriInfo;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.utilities.HibernateController;
import eu.fp7.scase.restreviews.utilities.PersistenceUtil;
import eu.fp7.scase.restreviews.product.JavaproductModel;
import eu.fp7.scase.restreviews.review.JavareviewModel;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

/* This class processes search requests for search resource and creates the hypermedia links with the search results to be returned to the client*/
public class GetsearchHandler{

    private HibernateController oHibernateController;
    private UriInfo oApplicationUri; //Standard datatype that holds information on the URI info of this request
    private JavaAlgosearchModel oJavaAlgosearchModel;
	private String searchProductDescription;
	private String searchProductTitle;
	private String searchReviewTitle;
	private String searchReviewDescription;
	private String searchKeyword;
	private final static String arrayInvalidSearchTerms[] = { "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with"};

    public GetsearchHandler(String searchKeyword, String searchProductDescription, String searchProductTitle, String searchReviewTitle, String searchReviewDescription, UriInfo oApplicationUri){
        oJavaAlgosearchModel = new JavaAlgosearchModel();
        this.oHibernateController = HibernateController.getHibernateControllerHandle();
        this.oApplicationUri = oApplicationUri;
		this.searchProductDescription = searchProductDescription;
		this.searchProductTitle = searchProductTitle;
		this.searchReviewTitle = searchReviewTitle;
		this.searchReviewDescription = searchReviewDescription;
		this.searchKeyword = searchKeyword;
    }

    public JavaAlgosearchModel getsearch(){

        //validate the search keyword
        validateSearchString();


		//Return any results in the hypermedia links form.
        return searchDatabase();
    }


    /* This function produces hypermedia links to be sent to the client that include all the search results, so as it will be able to forward the application state in a valid way.*/
    public JavaAlgosearchModel searchDatabase(){
		
		// if any searchable property of resource Product is included in clients search request
    	if((this.searchProductDescription != null && this.searchProductDescription.equalsIgnoreCase("true")) || (this.searchProductTitle != null && this.searchProductTitle.equalsIgnoreCase("true")))
    	{
			//then add hypermedia links to any search results from this resource
    		addProductHypermediaLinks();
    	}
		// if any searchable property of resource Review is included in clients search request
    	if((this.searchReviewTitle != null && this.searchReviewTitle.equalsIgnoreCase("true")) || (this.searchReviewDescription != null && this.searchReviewDescription.equalsIgnoreCase("true")))
    	{
			//then add hypermedia links to any search results from this resource
    		addReviewHypermediaLinks();
    	}

		return this.oJavaAlgosearchModel;
	}

	/* This functions produces hypermedia links to be sent to the client that include search results of resource Product search requests
	 */
    public void addProductHypermediaLinks(){

        //check if the validated search keyword is non empty
        if(this.searchKeyword == null){
            return;
        }   

    	FullTextEntityManager oFullTextEntityManager = PersistenceUtil.getFullTextEntityManager();
    	PersistenceUtil.beginEntityManagerTransaction();

		ArrayList<String> strQueryParams = new ArrayList<String>();		

		if((this.searchProductDescription != null && this.searchProductDescription.equalsIgnoreCase("true"))){
			strQueryParams.add("description");
		}

		if((this.searchProductTitle != null && this.searchProductTitle.equalsIgnoreCase("true"))){
			strQueryParams.add("title");
		}

    	QueryBuilder oQueryBuilder = oFullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(JavaproductModel.class).get();
    	org.apache.lucene.search.Query oLuceneQuery = oQueryBuilder.keyword().onFields(strQueryParams.toArray(new String[strQueryParams.size()])).matching(this.searchKeyword).createQuery();
    	// wrap Lucene query in a javax.persistence.Query
    	javax.persistence.Query oJpaQuery = oFullTextEntityManager.createFullTextQuery(oLuceneQuery, JavaproductModel.class);

    	// execute search
    	List<JavaproductModel> JavaproductModelList =(List<JavaproductModel>) oJpaQuery.getResultList();

    	Iterator<JavaproductModel> iteratorOfJavaproductModelList = JavaproductModelList.iterator();
    	while(iteratorOfJavaproductModelList.hasNext())
    	{
    		JavaproductModel oJavaproductModel = iteratorOfJavaproductModelList.next();
			if(oJavaproductModel.getaccount() != null){
				oJavaAlgosearchModel.getlinklist().add(new HypermediaLink(String.format("%smultiproduct/%s/%d/%s/%d", oApplicationUri.getBaseUri(), "account", oJavaproductModel.getaccount().getaccountId(), "product", oJavaproductModel.getproductId()), "Search result", "GET", "product"));
			}
			if(oJavaproductModel.getorder() != null){
				oJavaAlgosearchModel.getlinklist().add(new HypermediaLink(String.format("%smultiproduct/%s/%d/%s/%d", oApplicationUri.getBaseUri(), "order", oJavaproductModel.getorder().getorderId(), "product", oJavaproductModel.getproductId()), "Search result", "GET", "product"));
			}
    	}
    	
    	PersistenceUtil.endEntityManagerTransaction();    	
    }	
	/* This functions produces hypermedia links to be sent to the client that include search results of resource Review search requests
	 */
    public void addReviewHypermediaLinks(){

        //check if the validated search keyword is non empty
        if(this.searchKeyword == null){
            return;
        }   

    	FullTextEntityManager oFullTextEntityManager = PersistenceUtil.getFullTextEntityManager();
    	PersistenceUtil.beginEntityManagerTransaction();

		ArrayList<String> strQueryParams = new ArrayList<String>();		

		if((this.searchReviewTitle != null && this.searchReviewTitle.equalsIgnoreCase("true"))){
			strQueryParams.add("title");
		}

		if((this.searchReviewDescription != null && this.searchReviewDescription.equalsIgnoreCase("true"))){
			strQueryParams.add("description");
		}

    	QueryBuilder oQueryBuilder = oFullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(JavareviewModel.class).get();
    	org.apache.lucene.search.Query oLuceneQuery = oQueryBuilder.keyword().onFields(strQueryParams.toArray(new String[strQueryParams.size()])).matching(this.searchKeyword).createQuery();
    	// wrap Lucene query in a javax.persistence.Query
    	javax.persistence.Query oJpaQuery = oFullTextEntityManager.createFullTextQuery(oLuceneQuery, JavareviewModel.class);

    	// execute search
    	List<JavareviewModel> JavareviewModelList =(List<JavareviewModel>) oJpaQuery.getResultList();

    	Iterator<JavareviewModel> iteratorOfJavareviewModelList = JavareviewModelList.iterator();
    	while(iteratorOfJavareviewModelList.hasNext())
    	{
    		JavareviewModel oJavareviewModel = iteratorOfJavareviewModelList.next();
    		oJavaAlgosearchModel.getlinklist().add(new HypermediaLink(String.format("%s%s/%d/%s/%d", oApplicationUri.getBaseUri(), "product", oJavareviewModel.getproduct().getproductId(), "review", oJavareviewModel.getreviewId()), "Search result", "GET", "review"));
    	}
    	
    	PersistenceUtil.endEntityManagerTransaction();    	
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
