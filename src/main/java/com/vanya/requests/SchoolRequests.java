package com.vanya.requests;

import com.vanya.entities.School;
import com.vanya.config.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SchoolRequests {
    public void saveSchool(School school) {
        Transaction transaction = null;
        try (Session session = Configuration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(school);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<School> getSchools() {
        try (Session session = Configuration.getSessionFactory().openSession()) {
            return session.createQuery("from School", School.class).list();
        }
    }

    public School getById(int id) {
        return this.getSchools().stream().filter(x -> x.id == id).findFirst().get();
    }
}