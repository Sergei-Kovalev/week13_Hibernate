package ru.clevertec.home.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.home.dao.PersonDAO;
import ru.clevertec.home.dto.LocalDateTimeTypeAdapter;
import ru.clevertec.home.dto.request.PersonRequest;
import ru.clevertec.home.entity.Person;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.mapper.HouseMapper;
import ru.clevertec.home.mapper.HouseMapperImpl;
import ru.clevertec.home.mapper.PersonMapper;
import ru.clevertec.home.mapper.PersonMapperImpl;
import ru.clevertec.home.service.PersonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDAO personDAO;

    private final PersonMapper personMapper;

    private final HouseMapper houseMapper;

    private final Gson gson;

    public PersonServiceImpl() {
        this.houseMapper = new HouseMapperImpl();
        this.personMapper = new PersonMapperImpl();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Override
    public String findByID(UUID uuid) throws EntityNotFoundException {
        return gson.toJson(
                personDAO.findPersonByID(uuid)
                        .map(personMapper::personToResponse)
                        .orElseThrow(() -> EntityNotFoundException.of(Person.class, uuid))
        );
    }

    @Override
    public String findAll(int pageNumber, int pageSize) {
        return gson.toJson(
                personDAO.findAll(pageNumber, pageSize)
                        .stream()
                        .map(personMapper::personToResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String savePerson(PersonRequest personRequest, UUID residenceId, List<UUID> ownedUUIDs) {
        Person person = personDAO.savePerson(personMapper.requestToPerson(personRequest), residenceId, ownedUUIDs);
        return gson.toJson(personMapper.personToResponse(person));
    }

    @Override
    public String updatePerson(UUID uuid, PersonRequest personRequest) {
        Person person = personDAO.updatePerson(uuid, personMapper.requestToPerson(personRequest));
        return gson.toJson(personMapper.personToResponse(person));
    }

    @Override
    public String deletePerson(UUID uuid) throws EntityNotFoundException {
        return personDAO.deletePerson(uuid);
    }

    @Override
    public String movingPerson(UUID personUUID, UUID houseUUID) {
        return personDAO.movingPerson(personUUID, houseUUID);
    }

    @Override
    public String addOwnership(UUID personUUID, UUID houseUUID) {
        return personDAO.addOwnership(personUUID, houseUUID);
    }

    @Override
    public String deleteOwnership(UUID personUUID, UUID houseUUID) {
        return personDAO.deleteOwnership(personUUID, houseUUID);
    }

    @Override
    public String findPersonsLivingInHouse(UUID houseUUID) {
        return gson.toJson(
                personDAO.findPersonsLivingInHouse(houseUUID)
                        .stream()
                        .map(personMapper::personToResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String findOwnedHouses(UUID personUUID) {
        Person person = personDAO.findPersonByID(personUUID)
                .orElseThrow(() -> EntityNotFoundException.of(Person.class, personUUID));

        return gson.toJson(
                person.getOwnerHouses().stream()
                        .map(houseMapper::houseToResponse)
                        .collect(Collectors.toList())
        );
    }
}
