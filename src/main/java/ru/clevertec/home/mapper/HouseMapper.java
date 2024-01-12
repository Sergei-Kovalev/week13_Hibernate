package ru.clevertec.home.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.clevertec.home.dto.request.HouseRequest;
import ru.clevertec.home.dto.response.HouseResponse;
import ru.clevertec.home.entity.House;

@Mapper(uses = AddressMapper.class)

public interface HouseMapper {

    HouseMapper INSTANCE = Mappers.getMapper(HouseMapper.class);

    @Mapping(source = "request.address", target = "address")
    House requestToHouse(HouseRequest request);

    @Mapping(source = "house.address", target = "address")
    HouseResponse houseToResponse(House house);
}
