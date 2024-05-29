package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        final UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 45);
        userService.saveUser("Sergey", "Petrov", (byte) 27);
        userService.saveUser("Ivan", "Ivov", (byte) 62);
        userService.saveUser("Vova", "Sidorov", (byte) 12);
        userService.getAllUsers().forEach(System.out::println);
        userService.removeUserById(2);
        userService.updateUserById(3, "Alexander", "Smirnov", (byte) 67);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
