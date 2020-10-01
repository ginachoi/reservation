package com.cr.reservation.controller;

import com.cr.reservation.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation/v1/tables")
public class TableController {
    private TableRepository tableRepository;

    @Autowired
    public TableController(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }
    @GetMapping
    public String getTables()  {
        System.out.println("find table by id" + this.tableRepository.findByTableId("4"));
        return "Hello";
    }
}
