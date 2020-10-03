package com.cr.reservation.service;

import org.junit.jupiter.api.Test;

import javax.persistence.Table;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    @Test
    public void testSomething() {
        List<Integer> list = Arrays.asList(3, 1, 8);
        List<Integer> sortedList = list.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        for(Integer n : sortedList) {
            System.out.println(n);
        }
    }

}