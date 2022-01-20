package com.vanya.requests;

import com.vanya.entities.Address;
import com.vanya.config.Configuration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AddressRequests {

    public void saveAddress(Address address) {
        Transaction address_transaction = null;
        try (Session session = Configuration.getSessionFactory().openSession()) {
            address_transaction = session.beginTransaction();
            session.save(address);
            address_transaction.commit();
        } catch (Exception e) {
            if (address_transaction != null) {
                address_transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Address> getAddresss() {
        try (Session session = Configuration.getSessionFactory().openSession()) {
            return session.createQuery("from Address", Address.class).list();
        }
    }

    public Address getById(int id) {
        return this.getAddresss().stream().filter(x -> x.id == id).findFirst().get();
    }
}