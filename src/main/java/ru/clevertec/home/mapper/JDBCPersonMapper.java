package ru.clevertec.home.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.clevertec.home.entity.Passport;
import ru.clevertec.home.entity.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class JDBCPersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        Passport passport = new Passport();
        passport.setPassportSeries(rs.getString("passport_series"));
        passport.setPassportNumber(rs.getString("passport_number"));

        person.setId(rs.getLong("id"));
        person.setUuid(UUID.fromString(rs.getString("uuid")));
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setSex(rs.getString("sex"));
        person.setPassport(passport);
        person.setResidence(null);
        person.setCreateDate(rs.getObject("create_date", LocalDateTime.class));
        person.setUpdateDate(rs.getObject("update_date", LocalDateTime.class));

        return person;
    }
}
