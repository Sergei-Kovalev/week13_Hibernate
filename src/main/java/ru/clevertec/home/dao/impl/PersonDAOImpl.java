package ru.clevertec.home.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.dao.PersonDAO;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.entity.Person;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.mapper.JDBCPersonMapper;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonDAOImpl implements PersonDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HouseDAO houseDAO;

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private JDBCPersonMapper mapper;


    @Override
    public Optional<Person> findPersonByID(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.byNaturalId(Person.class)
                .using("uuid", uuid)
                .load());
    }

    @Override
    public List<Person> findAll(int pageNumber, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        Query<Person> query = session.createQuery("SELECT p FROM Person p", Person.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public Person savePerson(Person person, UUID residenceId, List<UUID> ownedUUIDs) throws EntityNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        LocalDateTime now = LocalDateTime.now();
        person.setCreateDate(now);
        person.setUpdateDate(now);

        House residence = houseDAO.findHouseByID(residenceId)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, residenceId));

        List<House> ownedHouses = null;
        if (!ownedUUIDs.isEmpty()) {
            ownedHouses = ownedUUIDs.stream()
                    .map(id -> houseDAO.findHouseByID(id)
                            .orElseThrow(() -> EntityNotFoundException.of(House.class, id)))
                    .collect(Collectors.toList());
        }

        person.setOwnerHouses(ownedHouses);
        person.setResidence(residence);

        session.persist(person);
        return person;
    }

    @Override
    public Person updatePerson(UUID uuid, Person person) {
        Optional<Person> personByID = findPersonByID(uuid);
        if (personByID.isPresent()) {
            Person personForMerge = personByID.get();

            fillPersonNewData(person, personForMerge);

            Session session = sessionFactory.getCurrentSession();

            return session.merge(personForMerge);
        } else {
            throw EntityNotFoundException.of(Person.class, uuid);
        }
    }

    @Override
    public String deletePerson(UUID uuid) throws EntityNotFoundException {
        Optional<Person> personById = findPersonByID(uuid);
        if (personById.isPresent()) {
            Person personForDelete = personById.get();
            Session session = sessionFactory.getCurrentSession();
            session.remove(personForDelete);
            return "Person with uuid = " + uuid.toString() + " was successfully deleted";
        }
        return "Person with uuid = " + uuid.toString() + " not present at database";
    }

    @Override
    public String movingPerson(UUID personUUID, UUID houseUUID) {
        Optional<Person> personByID = findPersonByID(personUUID);
        Optional<House> houseByID = houseDAO.findHouseByID(houseUUID);

        if (personByID.isPresent() && houseByID.isPresent()) {
            Person personForUpdate = personByID.get();
            House houseForUpdate = houseByID.get();

            personForUpdate.setResidence(houseForUpdate);

            Session session = sessionFactory.getCurrentSession();

            personForUpdate.setUpdateDate(LocalDateTime.now());

            session.merge(personForUpdate);

            return "Person with uuid = " + personUUID + " moved to a new house with uuid = " + houseUUID;
        }
        return "Person with uuid = " + personUUID + " or house with uuid = " + houseUUID + " not present at database";
    }

    @Override
    public String addOwnership(UUID personUUID, UUID houseUUID) {
        Optional<Person> personByID = findPersonByID(personUUID);
        Optional<House> houseByID = houseDAO.findHouseByID(houseUUID);

        if (personByID.isPresent() && houseByID.isPresent()) {
            Person personForUpdate = personByID.get();
            House houseForUpdate = houseByID.get();

            List<House> ownerHouses = personForUpdate.getOwnerHouses();
            if (ownerHouses == null) {
                ownerHouses = new ArrayList<>();
            }
            ownerHouses.add(houseForUpdate);

            Session session = sessionFactory.getCurrentSession();

            personForUpdate.setUpdateDate(LocalDateTime.now());

            session.merge(personForUpdate);

            return "For person with uuid = " + personUUID + " added ownership for a house with uuid = " + houseUUID;
        }
        return "Person with uuid = " + personUUID + " or house with uuid = " + houseUUID + " not present at database";
    }

    @Override
    public String deleteOwnership(UUID personUUID, UUID houseUUID) {
        Optional<Person> personByID = findPersonByID(personUUID);
        Optional<House> houseByID = houseDAO.findHouseByID(houseUUID);

        if (personByID.isPresent() && houseByID.isPresent()) {
            Person personForUpdate = personByID.get();
            House houseForUpdate = houseByID.get();

            List<House> ownerHouses = personForUpdate.getOwnerHouses();

            ownerHouses.remove(houseForUpdate);

            Session session = sessionFactory.getCurrentSession();

            personForUpdate.setUpdateDate(LocalDateTime.now());

            session.merge(personForUpdate);

            return "For person with uuid = " + personUUID + " deleted ownership for a house with uuid = " + houseUUID;
        }
        return "Person with uuid = " + personUUID + " or house with uuid = " + houseUUID + " not present at database";
    }

    @Override
    public List<Person> findPersonsLivingInHouse(UUID houseUUID) {
        String sql = """
                SELECT * FROM persons as p
                INNER JOIN passports as ps
                ON p.passport_id = ps.id
                WHERE p.residence_id =
                    (SELECT h.id FROM houses AS h
                     WHERE h.uuid = :uuid)
                """;
        SqlParameterSource namedParameters = new MapSqlParameterSource("uuid", houseUUID);
        return jdbcTemplate.query(sql, namedParameters, mapper);
    }

    @Override
    public List<Person> findPersonsSubstring(String substring) {
        String sql = """
                SELECT * FROM persons as p
                INNER JOIN passports as ps ON p.passport_id = ps.id
                WHERE name LIKE '%:substring%' OR surname LIKE '%:substring%' OR sex LIKE '%:substring%'
                OR passport_series LIKE '%:substring%' OR passport_number LIKE '%:substring%'
                """.replaceAll(":substring", substring);

        return jdbcTemplate.query(sql, mapper);
    }

    private static void fillPersonNewData(Person person, Person personForMerge) {
        if (person.getName() != null) {
            personForMerge.setName(person.getName());
        }
        if (person.getSurname() != null) {
            personForMerge.setSurname(person.getSurname());
        }
        if (person.getSex() != null) {
            personForMerge.setSex(person.getSex());
        }
        if (person.getPassport() != null) {
            personForMerge.setPassport(person.getPassport());
        }
        personForMerge.setUpdateDate(LocalDateTime.now());
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
