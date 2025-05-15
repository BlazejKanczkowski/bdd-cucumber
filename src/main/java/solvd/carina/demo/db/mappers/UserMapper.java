package solvd.carina.demo.db.mappers;

import solvd.carina.demo.db.models.User;

public interface UserMapper {
    User getUserByUsername(String username);
}
