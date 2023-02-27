package com.example.selscra.controller;

import com.example.selscra.common.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LidlController {

    @GetMapping("/lidl/users")
    public String index(@RequestParam(value="name", defaultValue = "World", required = true) String name, Model model){
        model.addAttribute("name", name);

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/SelScra?user=postgres&password=arcadosi&ssl=false");
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet res = stat.executeQuery(sql);
            List<User> usersList = new ArrayList<>();

            while (res.next()){
                int id = res.getInt("id");
                String email = res.getString("email");
                String password = res.getString("password");
                String timestamp = res.getString("last_logged");

                User user = new User(id, email, password, timestamp);
                usersList.add(user);
            }

            model.addAttribute("USERS_LIST", usersList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "users";
    }
    @GetMapping("/lidl")
    public String index(){
        return "index";
    }
}
