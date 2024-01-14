package ru.clevertec.home.testData.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.response.PassportResponse;

@Data
@Builder(setterPrefix = "with")
public class PassportResponseTestData {

    @Builder.Default
    private String passportSeries = "BB";

    @Builder.Default
    private String passportNumber = "1234567";

    public PassportResponse buildPassportResponse() {
        return new PassportResponse(passportSeries, passportNumber);
    }
}
