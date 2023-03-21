package dao.impl;

import dao.EmployeeDAO;
import lombok.RequiredArgsConstructor;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeDAOImpl implements EmployeeDAO {
    private final Connection connection;

    @Override
    public Employee createEmployee(Employee employee) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO employee (first_name, last_name, gender, age) " +
                "VALUES ((?), (?), (?), (?))")) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee findById(int id) {
        Employee employee = new Employee();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE id=(?)")){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(Integer.parseInt(resultSet.getString("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employee = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = Integer.parseInt(resultSet.getString("age"));
                employee.add(new Employee(name, lastName, gender ,age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
    @Override
    public Employee updateEmployeeById(Employee employee, int id) {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE employee SET first_name = (?), last_name = (?), gender = (?), " +
                "age = (?) WHERE id = (?)")){
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee deleteById(int id) {
        Employee employee = findById(id);
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE id = (?)")){
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
