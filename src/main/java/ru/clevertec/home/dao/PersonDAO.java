package ru.clevertec.home.dao;

import ru.clevertec.home.entity.Person;
import ru.clevertec.home.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDAO {
    Optional<Person> findPersonByID(UUID uuid);

    List<Person> findAll(int pageNumber, int pageSize);

    Person savePerson(Person person, UUID residenceId, List<UUID> ownedUUIDs) throws EntityNotFoundException;

    Person updatePerson(UUID uuid, Person person);

    String deletePerson(UUID uuid) throws EntityNotFoundException;

    String movingPerson(UUID personUUID, UUID houseUUID);

    String addOwnership(UUID personUUID, UUID houseUUID);

    String deleteOwnership(UUID personUUID, UUID houseUUID);

    List<Person> findPersonsLivingInHouse(UUID houseUUID);

    List<Person> findPersonsSubstring(String substring);
}
