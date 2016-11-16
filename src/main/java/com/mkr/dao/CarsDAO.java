package com.mkr.dao;

import com.mkr.entity.Car;
import com.mkr.entity.Order;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;


@Stateless
public class CarsDAO extends AbstractDAO<Car> {
    @Override
    protected Class getEntityClass () {
        return Car.class;
    }


    private boolean intersects(Order o, Date start, Date end) {
        long a = o.getStartDate().getTime();
        long b = o.getEndDate().getTime();

        long x = start.getTime();
        long y = end.getTime();

        return b > x && a < y;
    }

    private boolean canUse(Car c, Date start, Date end) {
        for (Order o : c.getOrders()) {
            if (intersects(o, start, end)) {
                return false;
            }
        }

        return true;
    }

    public List<Car> getFreeCars(Date start, Date end) {
        List<Car> cars = getAll();
        for (int i = 0; i < cars.size(); i++) {
            Car c = cars.get(i);

            if (!canUse(c, start, end))  {
                cars.remove(i);
                i--;
            }
        }

        return cars;
    }
}
