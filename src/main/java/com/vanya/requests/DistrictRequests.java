package com.vanya.requests;

import com.vanya.entities.District;
import com.vanya.config.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DistrictRequests {
    public void saveDistrict(District district) {
        Transaction transaction = null;
        try (Session session = Configuration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(district);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<District> getDistricts() {
        try (Session session = Configuration.getSessionFactory().openSession()) {
            return session.createQuery("from District", District.class).list();
        }
    }
}