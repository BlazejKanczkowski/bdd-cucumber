package solvd.carina.demo.db.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class Order {

    private int id;
    private int userId;
    private String productName;
    private BigDecimal price;
    private Timestamp orderDate;
}
