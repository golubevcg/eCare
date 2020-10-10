package eCare;


import eCare.model.enitity.User;
import org.hibernate.Session;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> usersList = session.createQuery(
                "select u " +
                        "from User u " +
                        "where u.secondname like:string", User.class)
                .setParameter("string", "%" + "Gotov" + "%")
                .list();
        session.close();

        System.out.println(usersList.size());

    }
}
