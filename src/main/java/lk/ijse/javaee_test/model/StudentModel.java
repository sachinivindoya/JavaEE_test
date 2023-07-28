package lk.ijse.javaee_test.model;

import lk.ijse.javaee_test.dto.StudentDTO;

import java.sql.*;

public class StudentModel {
    public static int SaveStudent(StudentDTO studentDTO, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO student VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1,studentDTO.getId());
        ps.setString(2,studentDTO.getName());
        ps.setString(3,studentDTO.getCity());
        ps.setString(4,studentDTO.getEmail());
        ps.setInt(5,studentDTO.getLevel());

        int i = ps.executeUpdate();
        ResultSet rst = ps.getGeneratedKeys();
        rst.next();

        return i;
    }

}
