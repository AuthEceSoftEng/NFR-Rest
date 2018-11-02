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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import org.hibernate.Session;
import org.hibernate.annotations.ForeignKey;

import eu.fp7.scase.restreviews.utilities.HypermediaLink;
import eu.fp7.scase.restreviews.product.JavaproductModel;
import eu.fp7.scase.restreviews.account.JavaaccountModel;


/* This class models the data of a order resource. It is enhanced with JAXB annotations for automated representation
parsing/marshalling as well as with Hibernate annotations for ORM transformations.*/
@XmlRootElement
@Entity
@Table(name="\"order\"")
public class JavaorderModel{


    /* There follows a list with the properties that model the order resource, as prescribed in the service CIM*/
		@Column(name = "\"coupon\"")
		private String coupon;

		@Id
		@GeneratedValue
		@Column(name = "\"orderid\"")
		private int orderId;

		// The Linklist property holds all the hypermedia links to be sent back to the client
		@Transient
		private List<HypermediaLink> linklist = new ArrayList<HypermediaLink>();

		// This property models the Many to One relationship between two resources as it is defined by the Hibernate syntax below.
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="accountId")
		@ForeignKey(name = "fk_account_order")
		private JavaaccountModel account;

		// This property models the One to Many relationship between two resources as it is defined by the Hibernate syntax below.
		@OneToMany(fetch = FetchType.EAGER, mappedBy="order",orphanRemoval=true)
		@OnDelete(action=OnDeleteAction.CASCADE)
		private Set<JavaproductModel> SetOfJavaproductModel = new HashSet<JavaproductModel>();


    /* There follows a list of setter and getter functions.*/
	    public void setcoupon(String coupon){
        	this.coupon = coupon;
    	}

	    public void setorderId(int orderId){
        	this.orderId = orderId;
    	}

	    public void setlinklist(List<HypermediaLink> linklist){
        	this.linklist = linklist;
    	}

		@XmlTransient
	    public void setaccount(JavaaccountModel account){
        	this.account = account;
    	}

		@XmlTransient
	    public void setSetOfJavaproductModel(Set<JavaproductModel> SetOfJavaproductModel){
        	this.SetOfJavaproductModel = SetOfJavaproductModel;
    	}

	    public String getcoupon(){
        	return this.coupon;
    	}

	    public int getorderId(){
        	return this.orderId;
    	}

	    public List<HypermediaLink> getlinklist(){
        	return this.linklist;
    	}

	    public JavaaccountModel getaccount(){
        	return this.account;
    	}

	    public Set<JavaproductModel> getSetOfJavaproductModel(){
        	return this.SetOfJavaproductModel;
    	}


    /* This function deletes explicitly any collections of this resource that are stored in the database 
    and iteratively does so for any subsequent related resources.
    NOTE: this function is needed to handle erroneous handling of cascade delete of some hibernate versions.*/
    public void deleteAllCollections(Session hibernateSession){


    }
}
