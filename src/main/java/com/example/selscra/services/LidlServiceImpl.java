package com.example.selscra.services;

import com.example.selscra.dto_lidl.Product;
import com.example.selscra.lidl.Lidl;
import com.example.selscra.repositories.LidlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LidlServiceImpl implements LidlService {

    @Autowired
    private LidlRepository repository;

    public List<Product> getAllWishlistProducts() {
        return repository.findAll();
    }

    @Override
    public String addAllWishlistProducts() {

        Lidl lidl = new Lidl(true);
        List<Product> products = lidl.login().acceptCookies().getAllWishlistProducts();
        int nr = products.size();
        repository.saveAll(products);
        return (nr > 1 ? (nr + " products ") : (nr + " product ")) + "were added";
    }

    @Override
    public void removeAllWishlistProducts() {
        repository.deleteAll();
    }
}
