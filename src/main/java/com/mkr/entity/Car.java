package com.mkr.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cars")
@NamedQuery(name="Car.selectAll", query="SELECT c FROM Car c")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String manufacturerName;
    private String color;
    private String modelName;
    private String registrationNumber;
    private Integer manufacturingYear;
    private Integer price;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Order> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    private boolean isAviavableNow(Order o) {
        return !(new Date().before(o.getStartDate()) || new Date().after(o.getEndDate()));
    }

    public boolean isUsed() {
        for (Order o : orders)
            if (isAviavableNow(o))
                return true;
        return false;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
