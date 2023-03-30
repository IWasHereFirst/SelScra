package com.example.selscra.controllers;

import com.example.selscra.common.User;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.dto_lidl.Product;
import com.example.selscra.services.LidlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/lidl")
public class LidlController {

    @Autowired
    LidlServiceImpl service;

    @GetMapping("/users")
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
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/products/get-extract")
    @ResponseBody
    public ConcurrentHashMap<Category, Integer> newExtract(){
        return service.newExtract();
    }

    @PostMapping("/products/add-product-from-url/")
    @ResponseBody
    public Product addProductFromUrl(@RequestBody String url) {
        return service.addProductFromUrl(url);
    }

    @PostMapping("/products/refresh-prices")
    @ResponseBody
    public void refreshPrices(){
        service.refreshPrices();
    }

    @DeleteMapping("/products/delete-all")
    @ResponseBody
    public String deleteAll(){
        return service.deleteAll();
    }

}
