package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.w3c.dom.ls.LSOutput;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String query = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(45),
                    lastname VARCHAR(45),
                    age SMALLINT
                );
                """;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(query);
            System.out.println("Создана база данных users");
        } catch (SQLException e) {
            System.out.println("База данных users не создана");
        }
    }

    public void dropUsersTable() {

        String query = """
                DROP TABLE IF EXISTS users;
                """;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(query);
            System.out.println("Удалена база данных users");
        } catch (SQLException e) {
            System.out.println("База данных users не удалена");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String query = """
                INSERT INTO users(name, lastname, age)
                VALUES (?, ?, ?)
                """;
        try (PreparedStatement preparedStatement =
                     Util.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.printf("Добавлен пользователь с именем %s, фамилией %s и возрастом %s", name, lastName, age);
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Пользователь не добавлен");
        }
    }

    public void removeUserById(long id) {

        String query = """
                DELETE FROM users
                WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.printf("Пользователь номер %s удалён", id);
            System.out.println();
        } catch (SQLException e) {
            System.out.printf("Пользователь номер %s не удалён", id);
        }
    }

    public List<User> getAllUsers() {

        String query = """
                SELECT * FROM users;
                """;
        List<User> userList = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet != null && resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                userList.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить все данные из базы");
        }
        return userList;
    }

    public void cleanUsersTable() {

        String query = """
                DELETE FROM users;
                """;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
        }
    }

    public void updateUserById(long id, String name, String lastName, byte age) {

        String query = """
                UPDATE users
                SET name = ?, lastname = ?, age = ?
                WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.setLong(4, id);
            preparedStatement.execute();
            System.out.printf("Пользователь номер %s изменён.\n", id);
        } catch (SQLException e) {
            System.out.printf("Пользователь номер %s не изменён.\n", id);
        }
    }
}
