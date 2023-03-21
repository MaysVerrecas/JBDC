package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee createEmployee(Employee employee);

    Employee findById(int id);

    List<Employee> findAll();

    Employee updateEmployeeById(Employee employee, int id);

    Employee deleteById(int id);
}
