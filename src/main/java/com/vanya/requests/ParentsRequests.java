package com.vanya.requests;

import java.util.List;

import com.vanya.entities.Parents;
import com.vanya.config.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ParentsRequests {

    public void transFunc(Parents parents){
        Transaction transaction = null;
        try (Session session = Configuration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(parents);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void saveParents(Parents parents) {
        transFunc(parents);
    }
    public void updateParents(Parents parents) {
        transFunc(parents);
    }

    public List<Parents> getParents() {
        try (Session session = Configuration.getSessionFactory().openSession()) {
            return session.createQuery("from Parents", Parents.class).list();
        }
    }

    public Parents getById(int id) {
        return this.getParents().stream().filter(x -> x.id == id).findFirst().get();
    }
}