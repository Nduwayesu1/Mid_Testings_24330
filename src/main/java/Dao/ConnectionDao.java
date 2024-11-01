package Dao;

import model1.HibernateUtil;
import org.hibernate.Session;


public class ConnectionDao {

    HibernateUtil con= new HibernateUtil();
    public String establishCon(){
        try{
            Session session =HibernateUtil.getSessionFactory().openSession();
            return "Connected";
        }catch (Exception ex){
            ex.printStackTrace();
            return  null;
        }
    }
}
