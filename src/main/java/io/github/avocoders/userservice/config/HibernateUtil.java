package io.github.avocoders.userservice.config;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
    private HibernateUtil(){

    }
    private static SessionFactory buildSessionFactory(){
        try {
            return new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
    public static void shutdown(){
        if(!SESSION_FACTORY.isClosed()){
            SESSION_FACTORY.close();
        }
    }

}
