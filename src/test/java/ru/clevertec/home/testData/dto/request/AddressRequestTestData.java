package ru.clevertec.home.testData.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.request.AddressRequest;

@Data
@Builder(setterPrefix = "with")
public class AddressRequestTestData {

    @Builder.Default
    String country = "Belarus";

    @Builder.Default
    String city = "Minsk";

    @Builder.Default
    String street = "Zaharova str";

    @Builder.Default
    String number = "27A";

    public AddressRequest buildAddressRequest() {
        return new AddressRequest(country, city, street, number);
    }
}
