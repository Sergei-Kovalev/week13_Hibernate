package ru.clevertec.home.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.home.dto.request.AddressRequest;
import ru.clevertec.home.dto.response.AddressResponse;
import ru.clevertec.home.entity.Address;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address requestToAddress(AddressRequest request);

    AddressResponse addressToResponse(Address address);
}
