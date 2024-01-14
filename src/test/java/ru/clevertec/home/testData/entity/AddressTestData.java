package ru.clevertec.home.testData.entity;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.entity.Address;

@Data
@Builder(setterPrefix = "with")
public class AddressTestData {

    @Builder.Default
    Long id = 500L;

    @Builder.Default
    String country = "Belarus";

    @Builder.Default
    String city = "Minsk";

    @Builder.Default
    String street = "Zaharova str";

    @Builder.Default
    String number = "27A";

    public Address buildAddress() {
        return new Address(id, country, city, street, number);
    }
}
