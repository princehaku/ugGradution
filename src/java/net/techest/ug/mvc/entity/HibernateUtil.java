/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.techest.ug.mvc.entity;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author princehaku
 */
public class HibernateUtil {

    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        if(sessionFactory==null){
            try{
                // Create the SessionFactory from standard (hibernate.cfg.xml) 
                // config file.
                sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                // Log the exception. 
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
    
    public Session getSession(){
        if(this.getSessionFactory().isClosed()){
            this.getSessionFactory().openSession();
        }
        
        return this.getSessionFactory().openSession();
    }
}
