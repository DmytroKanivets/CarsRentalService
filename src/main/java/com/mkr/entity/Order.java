package com.mkr.entity;


import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name="orders")
@NamedQueries({
        @NamedQuery(name = "Order.selectAll", query = "SELECT o FROM Order o"),
        @NamedQuery(name = "Order.selectByCar", query = "SELECT o from Order o WHERE o.car.id = :id ORDER BY o.startDate DESC"),
        @NamedQuery(name = "Order.selectByClient", query = "SELECT o from Order o WHERE o.client.id = :id ORDER BY o.startDate DESC")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="car")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private Client client;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return (int) TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
    }
}
