package ru.clevertec.home.testData.entity;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.entity.Passport;

@Data
@Builder(setterPrefix = "with")
public class PassportTestData {

    @Builder.Default
    private Long id = 800L;

    @Builder.Default
    private String passportSeries = "BB";

    @Builder.Default
    private String passportNumber = "1234567";

    public Passport buildPassport() {
        return new Passport(id, passportSeries, passportNumber);
    }
}
