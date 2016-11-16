package com.mkr.managedbeans;


import com.mkr.dao.CarsDAO;
import com.mkr.dao.ClientsDAO;
import com.mkr.dao.OrdersDAO;
import com.mkr.entity.Car;
import com.mkr.entity.Client;
import com.mkr.entity.Order;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ManagedBean(name="orders")
@SessionScoped
public class OrdersController {

    @EJB
    OrdersDAO ordersDAO;

    @EJB
    ClientsDAO clientsDAO;

    @EJB
    CarsDAO carsDAO;

    public Client getClient() {
        return clientsDAO.getClientByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
    }

    public List<Order> getClientOrders() {
        return ordersDAO.getByClientIdSorted(getClient().getId());
    }

    public List<Order> getAllOrders() {
        return ordersDAO.getAll();
    }

    public int getProfit(List<Order> orders) {
        int total = 0;
        for (Order o: orders) {
            total += o.getDuration() * o.getCar().getPrice();
        }

        return total;
    }

    public String getStatus(int orderId) {
        Order o = ordersDAO.find(orderId);

        if (o.getEndDate().getTime() - System.currentTimeMillis() < 0) {
            return "Completed";
        } else if (o.getStartDate().getTime() - System.currentTimeMillis() < 0) {
            return "Current";
        } else {
            return "Future";
        }
    }

    public void cancelOrder(int orderId) {
        ordersDAO.deleteByID(orderId);
    }

    private Date startDate;
    private Date endDate;

    private boolean datesAreValid() {

        if (startDate == null) {
            message =  "Enter start date";
            return false;
        }

        if (endDate == null) {
            message =  "Enter end date";
            return false;
        }

        //if (new Date(System.currentTimeMillis() - 86_400_000).after(startDate)) {
        if (System.currentTimeMillis() - startDate.getTime() > 86_400_000) {
            message =  "You cant order car in past";
            return false;
        }

        if (!(endDate.after(startDate))) {
            message = "End date must be after start";
            return false;
        }

        return true;
    }

    List<Car> foundCars;

    public String searchCar() {
        if (!datesAreValid()) {
            return "/cars/newOrder";
        }

        foundCars = carsDAO.getFreeCars(startDate, endDate);

        return "/cars/selectCar";
    }

    public List<Car> getFoundCars() {
        return foundCars;
    }

    public int getOrderDuration() {
        long diff = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void submitOrder(int carId) {
        Order o = new Order();
        o.setCar(carsDAO.find(carId));
        o.setStartDate(startDate);
        o.setEndDate(endDate);
        o.setClient(getClient());

        ordersDAO.add(o);
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

    private String message;

    public String getMessage() {
        String tmp = message;
        message = null;
        return tmp;
    }

    public boolean hasMessage() {
        return message != null;
    }
}
