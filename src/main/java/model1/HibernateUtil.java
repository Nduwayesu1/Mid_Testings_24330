package model1;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create the Configuration instance
                Configuration configuration = new Configuration();

                // Set Hibernate properties
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/auca_library_db");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "Olivier@@96");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.format_sql", "true");

                // Register all annotated entity classes
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Borrower.class);
                configuration.addAnnotatedClass(Location.class);
                configuration.addAnnotatedClass(Membership.class);
                configuration.addAnnotatedClass(Membership_type.class);
                configuration.addAnnotatedClass(Room.class);
                configuration.addAnnotatedClass(Shelf.class);
                configuration.addAnnotatedClass(User.class);

                // Build the SessionFactory
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("SessionFactory creation failed: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}
