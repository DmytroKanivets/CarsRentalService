package com.mkr.managedbeans;

import com.mkr.dao.CarsDAO;
import com.mkr.dao.OrdersDAO;
import com.mkr.entity.Car;
import com.mkr.entity.Order;
import org.apache.logging.log4j.LogManager;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name="cars")
@SessionScoped
public class CarsController {

    @EJB
    CarsDAO carsDAO;

    @EJB
    OrdersDAO ordersDAO;

    private Car selectedCar;

    public List<Car> getAll() {
        return carsDAO.getAll();
    }

    public List<Order> getSelectedOrders() {
        return ordersDAO.getByCarIdSorted(selectedCar.getId());
    }

    public void setSelectedCar(int id) {
        this.selectedCar = carsDAO.find(id);
    }

    public Car getSelectedCar() {
        LogManager.getLogger().warn("Number of orders: " + selectedCar.getOrders().size());
        return selectedCar;
    }

    private Car car;

    @PostConstruct
    public void init() {
        car = new Car();
    }

    public void addCar() {
        carsDAO.add(getCar());
        car = new Car();
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
