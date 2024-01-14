package ru.clevertec.home.testData.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.request.PassportRequest;
import ru.clevertec.home.dto.request.PersonRequest;

import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class PersonRequestTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("54882073-e105-43d6-8a7e-f01205848c21");

    @Builder.Default
    private String name = "Misha";

    @Builder.Default
    private String surname = "Ovsiannikov";

    @Builder.Default
    private String sex = "MALE";

    @Builder.Default
    private PassportRequest passport = PassportRequestTestData.builder().build().buildPassportRequest();

    public PersonRequest buildPersonRequest() {
        return new PersonRequest(uuid, name, surname, sex, passport);
    }
}
