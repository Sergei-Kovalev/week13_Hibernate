package ru.clevertec.home.testData.entity;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.entity.Passport;
import ru.clevertec.home.entity.Person;
import ru.clevertec.home.entity.PersonSex;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private Long id = 888L;

    @Builder.Default
    private UUID uuid = UUID.fromString("54882073-e105-43d6-8a7e-f01205848c21");

    @Builder.Default
    private String name = "Misha";

    @Builder.Default
    private String surname = "Ovsiannikov";

    @Builder.Default
    private PersonSex sex = PersonSex.MALE;

    @Builder.Default
    private Passport passport = PassportTestData.builder().build().buildPassport();

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(1999, Month.JANUARY, 23, 11, 15);

    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.of(1999, Month.JANUARY, 23, 11, 15);

    @Builder.Default
    private List<House> ownerHouses = null;

    @Builder.Default
    private House residence = HouseTestData.builder().build().buildHouseTestData();

    public Person buildPerson() {
        return new Person(id, uuid, name, surname, sex, passport, createDate, updateDate, ownerHouses, residence);
    }
}
