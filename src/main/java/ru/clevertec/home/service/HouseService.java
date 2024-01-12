package ru.clevertec.home.service;

import ru.clevertec.home.dto.request.HouseRequest;
import ru.clevertec.home.exception.EntityNotFoundException;

import java.util.UUID;

public interface HouseService {

    String findByID(UUID uuid) throws EntityNotFoundException;

    String findAll(int pageNumber, int pageSize);

    String saveHouse(HouseRequest houseRequest);

    String updateHouse(UUID uuid, HouseRequest houseRequest);

    String deleteHouse(UUID uuid) throws EntityNotFoundException;
}
