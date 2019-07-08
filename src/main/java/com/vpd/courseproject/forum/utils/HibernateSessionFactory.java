package com.vpd.courseproject.forum.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder().configure()
                .build()).buildMetadata().buildSessionFactory();
    }

    private HibernateSessionFactory() {}

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
