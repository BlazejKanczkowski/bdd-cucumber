package solvd.carina.demo.db.mappers;

import solvd.carina.demo.db.models.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> getOrdersByUserId(int userId);
}
