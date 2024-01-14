package ru.clevertec.home.testData.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.home.dto.request.AddressRequest;
import ru.clevertec.home.dto.request.HouseRequest;

import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class HouseRequestTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("1f87a75f-8964-4460-9156-dd39be12ac17");

    @Builder.Default
    private double area = 50.0;

    @Builder.Default
    private AddressRequest addressResponse = AddressRequestTestData.builder().build().buildAddressRequest();

    public HouseRequest buildHouseRequest() {
        return new HouseRequest(uuid, area, addressResponse);
    }
}
