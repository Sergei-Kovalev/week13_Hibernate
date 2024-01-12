package ru.clevertec.home.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.dao.PersonDAO;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.entity.Person;
import ru.clevertec.home.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PersonDAOImpl implements PersonDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HouseDAO houseDAO;

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
}
