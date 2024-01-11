package ru.clevertec.home.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.home.dto.request.HouseRequest;
import ru.clevertec.home.dto.response.HouseResponse;
import ru.clevertec.home.entity.House;

@Mapper
public interface HouseMapper {

    House requestToHouse(HouseRequest request);

    HouseResponse houseToResponse(House house);
}
