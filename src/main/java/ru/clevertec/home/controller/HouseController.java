package ru.clevertec.home.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.home.dto.request.HouseRequest;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.service.HouseService;

import java.util.UUID;

@RestController
@RequestMapping("/houses")
public class HouseController {

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

    @GetMapping("/")
    public ResponseEntity<String> findAll(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                           @RequestParam(value = "size", defaultValue = "15") int pageSize) {
        return new ResponseEntity<>(houseService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> saveHouse(@RequestBody HouseRequest houseRequest) {
        try {
            return new ResponseEntity<>(houseService.saveHouse(houseRequest), HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(
                    "Sorry but house with uuid " + houseRequest.uuid() + " already exist at Database",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateHouse(@RequestParam("uuid") String uuid, @RequestBody HouseRequest houseRequest) {
        try {
            return new ResponseEntity<>(houseService.updateHouse(UUID.fromString(uuid), houseRequest), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHouse(@RequestParam("uuid") String uuid) {
        String message = houseService.deleteHouse(UUID.fromString(uuid));
        System.out.println(message);
        if (message.contains("not present")) {
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/substring")
    public ResponseEntity<String> findHousesSubstring(@RequestParam("substring") String substring) {
        return new ResponseEntity<>(houseService.findHousesSubstring(substring), HttpStatus.OK);
    }
}
