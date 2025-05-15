package solvd.carina.demo.db.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int id;
    private String username;
    private String password;
    private String fullName;
    private String email;
}
