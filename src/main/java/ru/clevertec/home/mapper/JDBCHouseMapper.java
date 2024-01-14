package ru.clevertec.home.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.home.entity.Address;
import ru.clevertec.home.entity.House;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class JDBCHouseMapper implements RowMapper<House> {
    @Override
    public House mapRow(ResultSet rs, int rowNum) throws SQLException {
        House house = new House();

        Address address = new Address();
        address.setCountry(rs.getString("country"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setNumber(rs.getString("number"));

        house.setId(rs.getLong("id"));
        house.setUuid(UUID.fromString(rs.getString("uuid")));
        house.setArea(rs.getDouble("area"));
        house.setAddress(address);
        house.setCreateDate(rs.getObject("create_date", LocalDateTime.class));

        return house;
    }
}
