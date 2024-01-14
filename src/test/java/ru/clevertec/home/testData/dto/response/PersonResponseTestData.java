package ru.clevertec.home.testData.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.response.PassportResponse;
import ru.clevertec.home.dto.response.PersonResponse;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class PersonResponseTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("54882073-e105-43d6-8a7e-f01205848c21");

    @Builder.Default
    private String name = "Misha";

    @Builder.Default
    private String surname = "Ovsiannikov";

    @Builder.Default
    private String sex = "MALE";

    @Builder.Default
    private PassportResponse passport = PassportResponseTestData.builder().build().buildPassportResponse();

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(1999, Month.JANUARY, 23, 11, 15);

    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.of(1999, Month.JANUARY, 23, 11, 15);

    public PersonResponse buildPersonResponse() {
        return new PersonResponse(uuid, name, surname, sex, passport, createDate, updateDate);
    }
}
