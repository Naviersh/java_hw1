package com.vanya.entities;

import com.vanya.requests.DistrictRequests;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "districts")
@Entity
public class District {
    @Id
    @GeneratedValue
    public int id;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    public List<Address> addresses = new ArrayList<Address>();

    public void save_district() {
        new DistrictRequests().saveDistrict(this);
    }
}
