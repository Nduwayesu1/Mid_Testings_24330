package Utils;

import Models.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create the Configuration instance
                Configuration configuration = new Configuration();

                // Set properties manually
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/AucaLibraryDB");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "Olivier@@96");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");

                // Enable SQL logging
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.format_sql", "true");
                configuration.setProperty("hibernate.use_sql_comments", "true");

                // Enable logging of SQL parameters
                configuration.setProperty("hibernate.generate_statistics", "true");
                configuration.setProperty("hibernate.query.substitutions", "true");
                // Add mappings manually
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
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}
