package com.example.selscra.controllers;

import com.example.selscra.common.User;
import com.example.selscra.dto_lidl.DiscountProduct;
import com.example.selscra.dto_lidl.WishlistProduct;
import com.example.selscra.dto_lidl.Category;
import com.example.selscra.services.LidlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/products/wishlist/get-all")
    @ResponseBody
    public List<WishlistProduct> getAllWishlistProducts(){
        return service.getAllWishlistProducts();
    }

    @PostMapping("/products/wishlist/add-all")
    @ResponseBody
    public String addAllWishlistProducts(){
        return service.addAllWishlistProducts();
    }

    @DeleteMapping("/products/wishlist/delete-all")
    @ResponseBody
    public void removeAllWishlistProducts(){
        service.removeAllWishlistProducts();
    }

    @PostMapping("/new-extract")
    @ResponseBody
    public List<Category> newExtract(){
        return service.newExtract();
    }

}
