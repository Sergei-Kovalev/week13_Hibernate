package ru.clevertec.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.home.dto.request.PersonRequest;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping
    public ResponseEntity<String> findByID(@RequestParam("uuid") String uuid) {
        try {
            return new ResponseEntity<>(personService.findByID(UUID.fromString(uuid)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<String> findAll(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "size", defaultValue = "15") int pageSize) {
        return new ResponseEntity<>(personService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> savePerson(@RequestBody PersonRequest personRequest,
                                             @RequestParam(value = "residence", defaultValue = "homeless") String residence,
                                             @RequestParam(value = "owner", defaultValue = "empty") List<String> ownedHouses) {
        UUID residenceUUID;
        List<UUID> ownedUUIDs;
        try {
            residenceUUID = UUID.fromString(residence);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("please enter the uuid of the house as \"residence\", a person cannot be homeless", HttpStatus.BAD_REQUEST);
        }
        if (ownedHouses.get(0).equalsIgnoreCase("empty")) {
            ownedUUIDs = new ArrayList<>();
        } else {
            ownedUUIDs = ownedHouses.stream().map(UUID::fromString).toList();
        }
        try {
            return new ResponseEntity<>(personService.savePerson(personRequest, residenceUUID, ownedUUIDs), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@RequestParam("uuid") String uuid, @RequestBody PersonRequest personRequest) {
        try {
            return new ResponseEntity<>(personService.updatePerson(UUID.fromString(uuid), personRequest), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam("uuid") String uuid) {
        String message = personService.deletePerson(UUID.fromString(uuid));
        System.out.println(message);
        if (message.contains("not present")) {
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PutMapping("/moving")
    public ResponseEntity<String> movingPerson(@RequestParam("personUUID") String personUUID,
                                               @RequestParam("houseUUID") String houseUUID) {
        try {
            return new ResponseEntity<>(
                    personService.movingPerson(UUID.fromString(personUUID), UUID.fromString(houseUUID)), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/ownership")
    public ResponseEntity<String> addOwnershipPerson(@RequestParam("personUUID") String personUUID,
                                                     @RequestParam("houseUUID") String houseUUID) {
        try {
            return new ResponseEntity<>(
                    personService.addOwnership(UUID.fromString(personUUID), UUID.fromString(houseUUID)), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ownership")
    public ResponseEntity<String> deleteOwnershipPerson(@RequestParam("personUUID") String personUUID,
                                                        @RequestParam("houseUUID") String houseUUID) {
        try {
            return new ResponseEntity<>(
                    personService.deleteOwnership(UUID.fromString(personUUID), UUID.fromString(houseUUID)), HttpStatus.OK
            );
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
