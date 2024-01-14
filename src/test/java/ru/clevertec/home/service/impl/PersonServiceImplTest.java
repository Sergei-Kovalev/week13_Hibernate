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
import ru.clevertec.home.dao.PersonDAO;
import ru.clevertec.home.dto.LocalDateTimeTypeAdapter;
import ru.clevertec.home.dto.request.PersonRequest;
import ru.clevertec.home.dto.response.PersonResponse;
import ru.clevertec.home.entity.Person;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.testData.dto.request.PersonRequestTestData;
import ru.clevertec.home.testData.dto.response.PassportResponseTestData;
import ru.clevertec.home.testData.dto.response.PersonResponseTestData;
import ru.clevertec.home.testData.entity.HouseTestData;
import ru.clevertec.home.testData.entity.PassportTestData;
import ru.clevertec.home.testData.entity.PersonTestData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonDAO personDAO;

    Gson gson;

    @InjectMocks
    PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
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
            Person person = PersonTestData.builder().build().buildPerson();
            UUID uuid = person.getUuid();
            PersonResponse personResponse = PersonResponseTestData.builder().build().buildPersonResponse();

            doReturn(Optional.of(person))
                    .when(personDAO).findPersonByID(uuid);

            String expected = gson.toJson(personResponse);

            // when
            String actual = personService.findByID(uuid);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void findByIDIfNotExists() {

            // given
            UUID uuid = UUID.randomUUID();

            doThrow(EntityNotFoundException.of(Person.class, uuid))
                    .when(personDAO).findPersonByID(uuid);

            // when then
            assertThatThrownBy(() -> personService.findByID(uuid))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Not found " + Person.class.getSimpleName() + " with uuid = " + uuid);
        }
    }

    @Test
    void findAll() {

        // given
        List<Person> persons = new ArrayList<>();
        Person person1 = PersonTestData.builder().build().buildPerson();
        Person person2 = PersonTestData.builder()
                .withUuid(UUID.fromString("5e7013da-f44a-4702-aafc-4baee83162c9"))
                .withPassport(PassportTestData.builder()
                        .withPassportSeries("AA")
                        .withPassportNumber("7654321")
                        .build().buildPassport())
                .build().buildPerson();
        persons.add(person1);
        persons.add(person2);

        List<PersonResponse> personResponseList = new ArrayList<>();
        PersonResponse personResponse1 = PersonResponseTestData.builder().build().buildPersonResponse();
        PersonResponse personResponse2 = PersonResponseTestData.builder()
                .withUuid(UUID.fromString("5e7013da-f44a-4702-aafc-4baee83162c9"))
                .withPassport(PassportResponseTestData.builder()
                                .withPassportSeries("AA")
                                .withPassportNumber("7654321")
                                .build().buildPassportResponse())
                .build().buildPersonResponse();
        personResponseList.add(personResponse1);
        personResponseList.add(personResponse2);

        doReturn(persons)
                .when(personDAO).findAll(1, 100);

        String expected = gson.toJson(personResponseList);

        // when
        String actual = personService.findAll(1, 100);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void savePerson() {

        // given
        PersonRequest personRequest = PersonRequestTestData.builder().build().buildPersonRequest();
        Person personBeforeSave = PersonTestData.builder()
                .withId(null)
                .withCreateDate(null)
                .withUpdateDate(null)
                .withPassport(PassportTestData.builder()
                        .withId(null)
                        .build().buildPassport())
                .withResidence(null)
                .withOwnerHouses(null)
                .build().buildPerson();
        Person personAfterSaving = PersonTestData.builder().build().buildPerson();
        PersonResponse personResponse = PersonResponseTestData.builder().build().buildPersonResponse();

        doReturn(personAfterSaving)
                .when(personDAO).savePerson(personBeforeSave, null, null);
        String expected = gson.toJson(personResponse);

        // when
        String actual = personService.savePerson(personRequest, null, null);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Nested
    class UpdateTests {
        @Test
        void updatePersonThatExists() {

            // given
            PersonRequest personRequest = PersonRequestTestData.builder().build().buildPersonRequest();
            UUID uuid = personRequest.uuid();
            PersonResponse personResponse = PersonResponseTestData.builder()
                    .withName("Nikolai")
                    .build().buildPersonResponse();

            Person personBeforeMerge = PersonTestData.builder()
                    .withId(null)
                    .withCreateDate(null)
                    .withUpdateDate(null)
                    .withPassport(PassportTestData.builder()
                            .withId(null)
                            .build().buildPassport())
                    .withResidence(null)
                    .withOwnerHouses(null)
                    .build().buildPerson();
            Person personAfterMerge = PersonTestData.builder()
                    .withName("Nikolai")
                    .build().buildPerson();

            doReturn(personAfterMerge)
                    .when(personDAO).updatePerson(uuid, personBeforeMerge);

            String expected = gson.toJson(personResponse);

            // when
            String actual = personService.updatePerson(uuid, personRequest);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void updatePersonThatNotExists() {
            // given
            PersonRequest personRequest = PersonRequestTestData.builder().build().buildPersonRequest();
            UUID uuid = personRequest.uuid();

            Person personBeforeMerge = PersonTestData.builder()
                    .withId(null)
                    .withCreateDate(null)
                    .withUpdateDate(null)
                    .withPassport(PassportTestData.builder()
                            .withId(null)
                            .build().buildPassport())
                    .withResidence(null)
                    .withOwnerHouses(null)
                    .build().buildPerson();

            doThrow(EntityNotFoundException.of(Person.class, uuid))
                    .when(personDAO).updatePerson(uuid, personBeforeMerge);

            // when then
            assertThatThrownBy(() -> personService.updatePerson(uuid, personRequest))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Not found " + Person.class.getSimpleName() + " with uuid = " + uuid);
        }
    }

    @Test
    void deletePerson() {

        // given
        UUID uuid = PersonTestData.builder().build().getUuid();
        String expected = "Hi";

        doReturn(expected)
                .when(personDAO).deletePerson(uuid);

        // when
        String actual = personService.deletePerson(uuid);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void movingPerson() {

        // given
        UUID personUUID = PersonTestData.builder().build().getUuid();
        UUID houseUUID = HouseTestData.builder().build().getUuid();

        String expected = "Hi";
        doReturn(expected)
                .when(personDAO).movingPerson(personUUID, houseUUID);

        // when
        String actual = personService.movingPerson(personUUID, houseUUID);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void addOwnership() {
        // given
        UUID personUUID = PersonTestData.builder().build().getUuid();
        UUID houseUUID = HouseTestData.builder().build().getUuid();

        String expected = "Hi";
        doReturn(expected)
                .when(personDAO).addOwnership(personUUID, houseUUID);

        // when
        String actual = personService.addOwnership(personUUID, houseUUID);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void deleteOwnership() {
        // given
        UUID personUUID = PersonTestData.builder().build().getUuid();
        UUID houseUUID = HouseTestData.builder().build().getUuid();

        String expected = "Hi";
        doReturn(expected)
                .when(personDAO).deleteOwnership(personUUID, houseUUID);

        // when
        String actual = personService.deleteOwnership(personUUID, houseUUID);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void findPersonsLivingInHouse() {

        // given
        UUID houseUUID = HouseTestData.builder().build().getUuid();
        List<Person> persons = new ArrayList<>();
        Person person1 = PersonTestData.builder().build().buildPerson();
        Person person2 = PersonTestData.builder()
                .withUuid(UUID.fromString("5e7013da-f44a-4702-aafc-4baee83162c9"))
                .withPassport(PassportTestData.builder()
                        .withPassportSeries("AA")
                        .withPassportNumber("7654321")
                        .build().buildPassport())
                .build().buildPerson();
        persons.add(person1);
        persons.add(person2);

        List<PersonResponse> personResponseList = new ArrayList<>();
        PersonResponse personResponse1 = PersonResponseTestData.builder().build().buildPersonResponse();
        PersonResponse personResponse2 = PersonResponseTestData.builder()
                .withUuid(UUID.fromString("5e7013da-f44a-4702-aafc-4baee83162c9"))
                .withPassport(PassportResponseTestData.builder()
                        .withPassportSeries("AA")
                        .withPassportNumber("7654321")
                        .build().buildPassportResponse())
                .build().buildPersonResponse();
        personResponseList.add(personResponse1);
        personResponseList.add(personResponse2);

        doReturn(persons)
                .when(personDAO).findPersonsLivingInHouse(houseUUID);

        String expected = gson.toJson(personResponseList);

        // when
        String actual = personService.findPersonsLivingInHouse(houseUUID);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }
}