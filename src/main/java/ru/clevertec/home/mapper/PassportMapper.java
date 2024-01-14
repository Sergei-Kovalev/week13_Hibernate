package ru.clevertec.home.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.home.dto.request.PersonRequest;
import ru.clevertec.home.dto.response.PassportResponse;
import ru.clevertec.home.entity.Passport;

@Mapper
public interface PassportMapper {

    PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);

    Passport requestToPassport(PersonRequest request);

    PassportResponse passportToResponse(Passport passport);
}
