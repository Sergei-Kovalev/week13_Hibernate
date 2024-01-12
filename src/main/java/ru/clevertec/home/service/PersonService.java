package ru.clevertec.home.service;

import ru.clevertec.home.dto.request.PersonRequest;
import ru.clevertec.home.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    String findByID(UUID uuid) throws EntityNotFoundException;

    String findAll(int pageNumber, int pageSize);

    String savePerson(PersonRequest personRequest, UUID residenceId, List<UUID> ownedUUIDs);

    String updatePerson(UUID uuid, PersonRequest personRequest);

    String deletePerson(UUID uuid) throws EntityNotFoundException;

    String movingPerson(UUID personUUID, UUID houseUUID);

    String addOwnership(UUID personUUID, UUID houseUUID);

    String deleteOwnership(UUID personUUID, UUID houseUUID);
}
