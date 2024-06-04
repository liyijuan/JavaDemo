package org.example.controller;

import org.example.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StockService service;

    @GetMapping("/app")
    public int deduct(@RequestParam("id") Long id) {
        int deduct = service.deduct(id);
        return deduct;
    }

    @GetMapping("/cache/count")
    public int getCountById(@RequestParam("id") Long id) {
        int countById = service.getCountById(id);
        return countById;
    }

    @GetMapping("/nocache/count")
    public int getCountNoCache(@RequestParam("id") Long id) {
        int countById = service.getCountNoCache(id);
        return countById;
    }
}
