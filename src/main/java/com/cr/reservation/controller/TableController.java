package com.cr.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation/v1/tables")
public class TableController {
    @GetMapping
    public String getTables()  {
        return "Hello";
    }
}
