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


package eu.fp7.scase.restreviews.utilities;



import java.util.Set;
import java.util.HashSet;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

import eu.fp7.scase.restreviews.account.JavaaccountModel;
import eu.fp7.scase.restreviews.product.JavaproductModel;
import eu.fp7.scase.restreviews.order.JavaorderModel;
import eu.fp7.scase.restreviews.review.JavareviewModel;

/* HibernateController class is responsible to handle the low level activity between Hibernate and the service database.
 You may not alter existing functions, or the service may not function properly.
 Should you need more functions these could be added at the end of this file.
 You may add any exception handling to existing and/or new functions of this file.*/

public class HibernateController{

    private static HibernateController oHibernateController = new HibernateController();

    /* Since the class follows the singleton design pattern its constructor is kept private. The unique instance of it is accessed through its public API "getHibernateControllerHandle()".*/
    private HibernateController(){}

    /* Since this class follows the singleton design pattern, this function offers to the rest of the system a handle to its unique instance.*/
    public static HibernateController getHibernateControllerHandle(){
        return oHibernateController;
    }


    /* This function handles the low level JPA activities so as to add a new account resource to the service database.*/
    public JavaaccountModel postaccount(JavaaccountModel oJavaaccountModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Insert the new account to database*/
        int accountId = (Integer) hibernateSession.save(oJavaaccountModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();

        /* Return the JavaaccountModel with updated accountId*/
        oJavaaccountModel.setaccountId(accountId);
        return oJavaaccountModel;
    }
	
    /* This function handles the low level hibernate activities so as to update an existing account resource of the service database.*/
    public JavaaccountModel putaccount(JavaaccountModel oJavaaccountModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Update the existing account of the database*/
        hibernateSession.update(oJavaaccountModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaaccountModel;
    }

    /* This function handles the low level hibernate activities so as to retrieve an existing account resource from the service database.*/
    public JavaaccountModel getaccount(JavaaccountModel oJavaaccountModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing account from the database*/
        oJavaaccountModel = (JavaaccountModel) hibernateSession.get(JavaaccountModel.class, oJavaaccountModel.getaccountId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaaccountModel;
    }

    /* This function handles the low level hibernate activities so as to delete an existing account resource from the service database.*/
    public JavaaccountModel deleteaccount(JavaaccountModel oJavaaccountModel){

   		/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing account from the database*/
        oJavaaccountModel = (JavaaccountModel) hibernateSession.get(JavaaccountModel.class, oJavaaccountModel.getaccountId());

        /* Delete any collection related with the existing account from the database.
        Note: this is needed because some hibernate versions do not handle correctly cascade delete on collections.*/
        oJavaaccountModel.deleteAllCollections(hibernateSession);

        /* Delete the existing account from the database*/
        hibernateSession.delete(oJavaaccountModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaaccountModel;
    }

    /* This function handles the low level hibernate activities so as to retrieve all the account resources from the service database.*/

    public Set<JavaaccountModel> getaccountList(Set<JavaaccountModel> SetOfaccountList){

        /* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the list of account resources that are needed.*/
        String strHibernateQuery = "FROM JavaaccountModel";
        Query hibernateQuery = hibernateSession.createQuery(strHibernateQuery);
        SetOfaccountList = new HashSet(hibernateQuery.list());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return SetOfaccountList;
    }

	
	
    /* This function handles the low level JPA activities so as to add a new product resource to the service database.*/
    public JavaproductModel postproduct(JavaproductModel oJavaproductModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Insert the new product to database*/
        int productId = (Integer) hibernateSession.save(oJavaproductModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();

        /* Return the JavaproductModel with updated productId*/
        oJavaproductModel.setproductId(productId);
        return oJavaproductModel;
    }
	
    /* This function handles the low level hibernate activities so as to update an existing product resource of the service database.*/
    public JavaproductModel putproduct(JavaproductModel oJavaproductModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Update the existing product of the database*/
        hibernateSession.update(oJavaproductModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaproductModel;
    }

    /* This function handles the low level hibernate activities so as to retrieve an existing product resource from the service database.*/
    public JavaproductModel getproduct(JavaproductModel oJavaproductModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing product from the database*/
        oJavaproductModel = (JavaproductModel) hibernateSession.get(JavaproductModel.class, oJavaproductModel.getproductId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaproductModel;
    }

    /* This function handles the low level hibernate activities so as to delete an existing product resource from the service database.*/
    public JavaproductModel deleteproduct(JavaproductModel oJavaproductModel){

   		/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing product from the database*/
        oJavaproductModel = (JavaproductModel) hibernateSession.get(JavaproductModel.class, oJavaproductModel.getproductId());

        /* Delete any collection related with the existing product from the database.
        Note: this is needed because some hibernate versions do not handle correctly cascade delete on collections.*/
        oJavaproductModel.deleteAllCollections(hibernateSession);

        /* Delete the existing product from the database*/
        hibernateSession.delete(oJavaproductModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaproductModel;
    }

	/* This function handles the low level hibernate activities so as to retrieve all the product resources from the service database
    that are related to a specific account resource.*/

    public JavaaccountModel getaccountproductList(JavaaccountModel oJavaaccountModel){

        /* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Find the account of which the product resource list is needed*/
        oJavaaccountModel = (JavaaccountModel) hibernateSession.get(JavaaccountModel.class, oJavaaccountModel.getaccountId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaaccountModel;
    }
	/* This function handles the low level hibernate activities so as to retrieve all the product resources from the service database
    that are related to a specific order resource.*/

    public JavaorderModel getorderproductList(JavaorderModel oJavaorderModel){

        /* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Find the order of which the product resource list is needed*/
        oJavaorderModel = (JavaorderModel) hibernateSession.get(JavaorderModel.class, oJavaorderModel.getorderId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaorderModel;
    }

	
	
    /* This function handles the low level JPA activities so as to add a new order resource to the service database.*/
    public JavaorderModel postorder(JavaorderModel oJavaorderModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Insert the new order to database*/
        int orderId = (Integer) hibernateSession.save(oJavaorderModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();

        /* Return the JavaorderModel with updated orderId*/
        oJavaorderModel.setorderId(orderId);
        return oJavaorderModel;
    }
	
    /* This function handles the low level hibernate activities so as to update an existing order resource of the service database.*/
    public JavaorderModel putorder(JavaorderModel oJavaorderModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Update the existing order of the database*/
        hibernateSession.update(oJavaorderModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaorderModel;
    }

    /* This function handles the low level hibernate activities so as to retrieve an existing order resource from the service database.*/
    public JavaorderModel getorder(JavaorderModel oJavaorderModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing order from the database*/
        oJavaorderModel = (JavaorderModel) hibernateSession.get(JavaorderModel.class, oJavaorderModel.getorderId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaorderModel;
    }

    /* This function handles the low level hibernate activities so as to delete an existing order resource from the service database.*/
    public JavaorderModel deleteorder(JavaorderModel oJavaorderModel){

   		/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing order from the database*/
        oJavaorderModel = (JavaorderModel) hibernateSession.get(JavaorderModel.class, oJavaorderModel.getorderId());

        /* Delete any collection related with the existing order from the database.
        Note: this is needed because some hibernate versions do not handle correctly cascade delete on collections.*/
        oJavaorderModel.deleteAllCollections(hibernateSession);

        /* Delete the existing order from the database*/
        hibernateSession.delete(oJavaorderModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaorderModel;
    }

	/* This function handles the low level hibernate activities so as to retrieve all the order resources from the service database
    that are related to a specific account resource.*/

    public JavaaccountModel getorderList(JavaaccountModel oJavaaccountModel){

        /* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Find the account of which the order resource list is needed*/
        oJavaaccountModel = (JavaaccountModel) hibernateSession.get(JavaaccountModel.class, oJavaaccountModel.getaccountId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaaccountModel;
    }

	
	
    /* This function handles the low level JPA activities so as to add a new review resource to the service database.*/
    public JavareviewModel postreview(JavareviewModel oJavareviewModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Insert the new review to database*/
        int reviewId = (Integer) hibernateSession.save(oJavareviewModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();

        /* Return the JavareviewModel with updated reviewId*/
        oJavareviewModel.setreviewId(reviewId);
        return oJavareviewModel;
    }
	
    /* This function handles the low level hibernate activities so as to update an existing review resource of the service database.*/
    public JavareviewModel putreview(JavareviewModel oJavareviewModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Update the existing review of the database*/
        hibernateSession.update(oJavareviewModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavareviewModel;
    }

    /* This function handles the low level hibernate activities so as to retrieve an existing review resource from the service database.*/
    public JavareviewModel getreview(JavareviewModel oJavareviewModel){

    	/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing review from the database*/
        oJavareviewModel = (JavareviewModel) hibernateSession.get(JavareviewModel.class, oJavareviewModel.getreviewId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavareviewModel;
    }

    /* This function handles the low level hibernate activities so as to delete an existing review resource from the service database.*/
    public JavareviewModel deletereview(JavareviewModel oJavareviewModel){

   		/* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Retrieve the existing review from the database*/
        oJavareviewModel = (JavareviewModel) hibernateSession.get(JavareviewModel.class, oJavareviewModel.getreviewId());


        /* Delete the existing review from the database*/
        hibernateSession.delete(oJavareviewModel);

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavareviewModel;
    }

	/* This function handles the low level hibernate activities so as to retrieve all the review resources from the service database
    that are related to a specific product resource.*/

    public JavaproductModel getreviewList(JavaproductModel oJavaproductModel){

        /* Create a new hibernate session and begin the transaction*/
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction hibernateTransaction = hibernateSession.beginTransaction();

        /* Find the product of which the review resource list is needed*/
        oJavaproductModel = (JavaproductModel) hibernateSession.get(JavaproductModel.class, oJavaproductModel.getproductId());

        /* Commit and terminate the session*/
        hibernateTransaction.commit();
        hibernateSession.close();
        return oJavaproductModel;
    }

	
	
}
