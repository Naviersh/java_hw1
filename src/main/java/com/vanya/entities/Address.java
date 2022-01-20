package com.vanya.entities;

import com.vanya.requests.AddressRequests;

import javax.persistence.*;

@Table(name = "addresses")
@Entity
public class Address {
    @Id
    @GeneratedValue
    public int id;

    @ManyToOne
    @JoinColumn(name = "districtId")
    public District district;

    public Address() {}

    public Address(String value) {
        this.value = value;
    }

    public String value;

    public void saveAddress() {
        new AddressRequests().saveAddress(this);
    }
}
