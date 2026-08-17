package io.github.avocoders.userservice;

import org.hibernate.SessionFactory;
import io.github.avocoders.userservice.config.HibernateUtil;

public class Application {
    public static void main(String[] args){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try{
            if (sessionFactory.isOpen()) {
                System.out.println("Connected");
            }
            else {
                System.out.println("Disconnected");
            }
        }
        finally{
            HibernateUtil.shutdown();
        }

    }
}
