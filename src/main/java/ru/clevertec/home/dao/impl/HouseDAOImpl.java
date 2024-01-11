package ru.clevertec.home.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.entity.House;

import java.util.Optional;
import java.util.UUID;

@Repository
public class HouseDAOImpl implements HouseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<House> findHouseByID(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.byNaturalId(House.class)
                .using("uuid", uuid)
                .load());
    }
}
