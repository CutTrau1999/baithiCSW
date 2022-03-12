package com.example.baithicsw.model;

import com.example.baithicsw.entity.Employee;
import com.example.baithicsw.utils.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    private Connection conn;

    public EmployeeModel() {
        conn = ConnectionHelper.getConnection();
    }

    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("select * from employees ");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double salary = rs.getInt("salary");
            employees.add(new Employee(id, name, salary));
        }
        return employees;
    }

    public Employee findById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from employees id = ?");
        stmt.setInt(1, 1);
        stmt.setInt(2, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            double salary = rs.getInt("salary");
            return new Employee(id, name, salary);
        }
        return null;
    }

    public Employee save(Employee employee) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into employees (name, salary) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, employee.getName());
        stmt.setDouble(2, employee.getSalary());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            ResultSet resultSetGeneratedKeys = stmt.getGeneratedKeys();
            if (resultSetGeneratedKeys.next()) {
                int id = resultSetGeneratedKeys.getInt(1);
                employee.setId(id);
                return employee;
            }
        }
        return null;
    }

    public Employee update(int id, Employee updateEmoloyee) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("update employees set name = ?, salary = ? where id = ?");
        stmt.setString(1, updateEmoloyee.getName());
        stmt.setDouble(2, updateEmoloyee.getSalary());
        stmt.setInt(3, id);
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            updateEmoloyee.setId(id);
            return updateEmoloyee;
        }
        return null;
    }

    public boolean delete(int id) throws SQLException {
        PreparedStatement stmtDelete = conn.prepareStatement("delete from employees where id = ?");
        stmtDelete.setInt(1, id);
        int affectedRows = stmtDelete.executeUpdate();
        if (affectedRows > 0) {
            return true;
        }
        return false;
    }
}
