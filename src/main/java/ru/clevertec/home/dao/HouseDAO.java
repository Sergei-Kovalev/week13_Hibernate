package ru.clevertec.home.dao;

import ru.clevertec.home.entity.House;
import ru.clevertec.home.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseDAO {

    Optional<House> findHouseByID(UUID uuid);

    List<House> findAll(int pageNumber, int pageSize);

    House saveHouse(House house);

    House updateHouse(UUID uuid, House house);

    String deleteHouse(UUID uuid) throws EntityNotFoundException;
}
