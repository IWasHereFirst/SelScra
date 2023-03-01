package com.example.selscra;

import com.example.selscra.common.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

//@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
/*
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection conn;
            PreparedStatement stat;
            Statement statement;
            ResultSet res;
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            // Get connection
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/SelScra?user=postgres&password=arcadosi&ssl=false");

            // Getting the highest ID
            statement = conn.createStatement();
            res = statement.executeQuery("SELECT MAX(id) as id FROM users");
            int maxId = 0;
            if(res.next()){
                maxId = res.getInt("id") + 1;
            }

            // Getting parameters from form
            String email = req.getParameter("email");
            String password = encoder.encode(req.getParameter("password"));
            String timestamp = req.getParameter("timestamp");

            // Creating user in DB

            String sql = "INSERT INTO users (id, email, password, last_logged) VALUES(?, ?, ?, ?)";
            stat = conn.prepareStatement(sql);

            stat.setInt(1, maxId);
            stat.setString(2,email);
            stat.setString(3,password);
            stat.setString(4,timestamp);

            stat.execute();

            Statement lastStat = conn.createStatement();
            ResultSet lastRes = lastStat.executeQuery("SELECT * FROM users WHERE id=" + maxId);

            int lastId;
            String lastEmail;
            String lastPassword;
            String last_logged;

            if(lastRes.next()){
                lastId = lastRes.getInt("id");
                lastEmail = lastRes.getString("email");
                lastPassword = lastRes.getString("password");
                last_logged = lastRes.getString("last_logged");
                User tempUser = new User(lastId, lastEmail, lastPassword, last_logged);
                req.setAttribute("CREATED_USER", tempUser);
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher("/registration");
            dispatcher.forward(req, resp);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
*/
}
