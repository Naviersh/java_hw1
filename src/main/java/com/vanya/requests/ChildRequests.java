package com.vanya.requests;

import com.vanya.entities.Child;
import com.vanya.config.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ChildRequests {

    public static void transFunc(Child child){
        Transaction transaction = null;
        try (Session session = Configuration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(child);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void saveChild(Child child) {
        transFunc(child);
    }

    public void updateChild(Child child) {
        transFunc(child);
    }

    public List<Child> getChildren() {
        try (Session session = Configuration.getSessionFactory().openSession()) {
            return session.createQuery("from Child", Child.class).list();
        }
    }

    public Child getById(int id) {
        return this.getChildren().stream().filter(x -> x.id == id).findFirst().get();
    }
}
