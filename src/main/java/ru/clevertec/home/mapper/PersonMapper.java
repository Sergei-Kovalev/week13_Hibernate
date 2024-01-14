package ru.clevertec.home.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.clevertec.home.dto.request.PersonRequest;
import ru.clevertec.home.dto.response.PersonResponse;
import ru.clevertec.home.entity.Person;

@Mapper(uses = PassportMapper.class)
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "request.passport", target = "passport")
    Person requestToPerson(PersonRequest request);

    @Mapping(source = "person.passport", target = "passport")
    PersonResponse personToResponse(Person person);
}
