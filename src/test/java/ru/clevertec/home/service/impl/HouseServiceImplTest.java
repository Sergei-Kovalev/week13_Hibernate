package ru.clevertec.home.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.dto.LocalDateTimeTypeAdapter;
import ru.clevertec.home.dto.request.HouseRequest;
import ru.clevertec.home.dto.response.HouseResponse;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.testData.dto.response.AddressResponseTestData;
import ru.clevertec.home.testData.dto.request.HouseRequestTestData;
import ru.clevertec.home.testData.dto.response.HouseResponseTestData;
import ru.clevertec.home.testData.entity.AddressTestData;
import ru.clevertec.home.testData.entity.HouseTestData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    HouseDAO houseDAO;

    private Gson gson;

    @InjectMocks
    HouseServiceImpl houseService;


    @BeforeEach
    void setUp(){
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .setPrettyPrinting()
                .create();
    }

    @Nested
    class FindByIDTests {
        @Test
        void findByIDIfExists() {

            // given
            House house = HouseTestData.builder().build().buildHouseTestData();
            UUID uuid = house.getUuid();
            HouseResponse houseResponse = HouseResponseTestData.builder().build().buildHouseResponse();

            doReturn(Optional.of(house))
                    .when(houseDAO).findHouseByID(uuid);

            String expected = gson.toJson(houseResponse);

            // when
            String actual = houseService.findByID(uuid);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void findByIDIfNotExists() {

            // given
            UUID uuid = UUID.randomUUID();

            doThrow(EntityNotFoundException.of(House.class, uuid))
                    .when(houseDAO).findHouseByID(uuid);

            // when then
           assertThatThrownBy(() -> houseService.findByID(uuid))
                   .isInstanceOf(EntityNotFoundException.class)
                   .hasMessage("Not found " + House.class.getSimpleName() + " with uuid = " + uuid);
        }
    }

    @Test
    void findAll() {

        // given
        List<House> houses = new ArrayList<>();
        House house1 = HouseTestData.builder().build().buildHouseTestData();
        House house2 = HouseTestData.builder()
                .withUuid(UUID.fromString("e5013b1e-a1f6-499e-9845-693b59cb11ee"))
                .withAddress(AddressTestData.builder()
                        .withCity("Italy")
                        .withCity("Rome")
                        .build().buildAddress())
                .build().buildHouseTestData();
        houses.add(house1);
        houses.add(house2);

        List<HouseResponse> houseResponseList = new ArrayList<>();
        HouseResponse houseResponse1 = HouseResponseTestData.builder().build().buildHouseResponse();
        HouseResponse houseResponse2 = HouseResponseTestData.builder()
                .withUuid(UUID.fromString("e5013b1e-a1f6-499e-9845-693b59cb11ee"))
                .withAddressResponse(AddressResponseTestData.builder()
                        .withCity("Italy")
                        .withCity("Rome")
                        .build().buildAddressResponse())
                .build().buildHouseResponse();
        houseResponseList.add(houseResponse1);
        houseResponseList.add(houseResponse2);

        doReturn(houses)
                .when(houseDAO).findAll(1, 100);

        String expected = gson.toJson(houseResponseList);

        // when
        String actual = houseService.findAll(1, 100);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void saveHouse() {

        // given
        HouseRequest houseRequest = HouseRequestTestData.builder().build().buildHouseRequest();
        House houseBeforeSave = HouseTestData.builder()
                .withId(null)
                .withCreateDate(null)
                .withAddress(AddressTestData.builder()
                        .withId(null)
                        .build().buildAddress())
                .build().buildHouseTestData();
        House houseAfterSaving = HouseTestData.builder().build().buildHouseTestData();
        HouseResponse houseResponse = HouseResponseTestData.builder().build().buildHouseResponse();

        doReturn(houseAfterSaving)
                .when(houseDAO).saveHouse(houseBeforeSave);
        String expected = gson.toJson(houseResponse);

        // when
        String actual = houseService.saveHouse(houseRequest);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Nested
    class UpdateTests {
        @Test
        void updateHouseThatExists() {

            // given
            HouseRequest houseRequest = HouseRequestTestData.builder().build().buildHouseRequest();
            UUID uuid = houseRequest.uuid();
            HouseResponse houseResponse = HouseResponseTestData.builder()
                    .withArea(24.2)
                    .build().buildHouseResponse();

            House houseBeforeMerge = HouseTestData.builder()
                    .withId(null)
                    .withCreateDate(null)
                    .withAddress(AddressTestData.builder()
                            .withId(null)
                            .build().buildAddress())
                    .build().buildHouseTestData();
            House houseAfterMerge = HouseTestData.builder()
                    .withArea(24.2)
                    .build().buildHouseTestData();

            doReturn(houseAfterMerge)
                    .when(houseDAO).updateHouse(uuid, houseBeforeMerge);

            String expected = gson.toJson(houseResponse);

            // when
            String actual = houseService.updateHouse(uuid, houseRequest);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void updateHouseThatNotExists() {

            // given
            HouseRequest houseRequest = HouseRequestTestData.builder().build().buildHouseRequest();
            UUID uuid = houseRequest.uuid();

            House houseBeforeMerge = HouseTestData.builder()
                    .withId(null)
                    .withCreateDate(null)
                    .withAddress(AddressTestData.builder()
                            .withId(null)
                            .build().buildAddress())
                    .build().buildHouseTestData();

            doThrow(EntityNotFoundException.of(House.class, uuid))
                    .when(houseDAO).updateHouse(uuid, houseBeforeMerge);

            // when then
            assertThatThrownBy(() -> houseService.updateHouse(uuid, houseRequest))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Not found " + House.class.getSimpleName() + " with uuid = " + uuid);
        }
    }

    @Test
    void deleteHouse() {

        // given
        UUID uuid = HouseTestData.builder().build().getUuid();
        String expected = "Hi";

        doReturn(expected)
                .when(houseDAO).deleteHouse(uuid);

        // when
        String actual = houseService.deleteHouse(uuid);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }
}