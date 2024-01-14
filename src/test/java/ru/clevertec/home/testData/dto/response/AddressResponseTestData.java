package ru.clevertec.home.testData.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.response.AddressResponse;

@Data
@Builder(setterPrefix = "with")
public class AddressResponseTestData {

    @Builder.Default
    String country = "Belarus";

    @Builder.Default
    String city = "Minsk";

    @Builder.Default
    String street = "Zaharova str";

    @Builder.Default
    String number = "27A";

    public AddressResponse buildAddressResponse() {
        return new AddressResponse(country, city, street, number);
    }
}
