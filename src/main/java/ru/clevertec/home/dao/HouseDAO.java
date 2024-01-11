package ru.clevertec.home.dao;

import ru.clevertec.home.entity.House;

import java.util.Optional;
import java.util.UUID;

public interface HouseDAO {

    Optional<House> findHouseByID(UUID uuid);
//
//    House saveHouse(House house);

}
