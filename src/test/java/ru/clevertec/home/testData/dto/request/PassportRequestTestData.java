package ru.clevertec.home.testData.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.request.PassportRequest;

@Data
@Builder(setterPrefix = "with")
public class PassportRequestTestData {

    @Builder.Default
    private String passportSeries = "BB";

    @Builder.Default
    private String passportNumber = "1234567";

    public PassportRequest buildPassportRequest() {
        return new PassportRequest(passportSeries, passportNumber);
    }
}
