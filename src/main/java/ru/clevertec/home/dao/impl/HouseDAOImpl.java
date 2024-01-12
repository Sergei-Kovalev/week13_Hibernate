package ru.clevertec.home.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class HouseDAOImpl implements HouseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<House> findHouseByID(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.byNaturalId(House.class)
                .using("uuid", uuid)
                .load());
    }

    @Override
    public List<House> findAll(int pageNumber, int pageSize) {
        Session session = sessionFactory.getCurrentSession();

        Query<House> query = session.createQuery("SELECT h FROM House h", House.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public House saveHouse(House house) {
        Session session = sessionFactory.getCurrentSession();

        house.setCreateDate(LocalDateTime.now());
        session.persist(house);

        return house;
    }

    @Override
    public House updateHouse(UUID uuid, House house) throws EntityNotFoundException {
        Optional<House> houseByID = findHouseByID(uuid);
        if (houseByID.isPresent()) {
            House houseForMerge = houseByID.get();
            if (house.getArea() != 0) {
                houseForMerge.setArea(house.getArea());
            }
            if (house.getAddress() != null) {
                houseForMerge.setAddress(house.getAddress());
            }
            Session session = sessionFactory.getCurrentSession();

            return session.merge(houseForMerge);
        } else {
            throw EntityNotFoundException.of(House.class, uuid);
        }
    }

    @Override
    public String deleteHouse(UUID uuid) {
        Optional<House> houseByID = findHouseByID(uuid);
        if (houseByID.isPresent()) {
            House houseForDelete = houseByID.get();
            Session session = sessionFactory.getCurrentSession();
            session.remove(houseForDelete);
            return "House with uuid = " + uuid.toString() + " was successfully deleted";
        }
        return "House with uuid = " + uuid.toString() + " not present at database";
    }
}
