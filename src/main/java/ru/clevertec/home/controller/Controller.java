package ru.clevertec.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.service.HouseService;

import java.util.UUID;

@RestController
@RequestMapping("/houses")
public class Controller {

    @Autowired
    private HouseService houseService;

    @GetMapping
    public ResponseEntity<String> findByID(@RequestParam("uuid") String uuid) {
        try {
            return new ResponseEntity<>(houseService.findByID(UUID.fromString(uuid)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("HI", HttpStatus.OK);
    }

//
//    @GetMapping("/test")
//    public String test() {
//        return "hi";
//    }


}
