import dao.EmployeeDAO;
import dao.impl.EmployeeDAOImpl;
import model.Employee;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        final String user = "postgres";
        final String password = "Verrecas1317";
        final String url = "jdbc:postgresql://localhost:5432/skypro";
        Map<String, String> stringMap = new HashMap<>();

        try(final Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE id = (?)"))
        {
            preparedStatement.setInt(1, 1);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String firstName = resultSet.getString("first_name");
                stringMap.put("First name: ", firstName);
                String lastName = resultSet.getString("last_name");
                stringMap.put("Last name: ", lastName);
                String gender = resultSet.getString("gender");
                stringMap.put("Gender: ", gender);
                String age = "" + resultSet.getInt("age");
                stringMap.put("Age: ", age);
                EmployeeDAO employeeDao = new EmployeeDAOImpl(connection);
                System.out.println(employeeDao.createEmployee(new Employee("Vasya", "Vasilyev", "m", 45)));
                System.out.println(employeeDao.updateEmployeeById(new Employee("Igor", "Vasilyev", "m", 45), 1));
                List<Employee> list = employeeDao.findAll();
                list.stream().forEach(System.out::println);
                System.out.println(employeeDao.findById(4));
                System.out.println(employeeDao.deleteById(5));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        stringMap.entrySet().stream()
                .forEach(System.out::println);
    }
}
