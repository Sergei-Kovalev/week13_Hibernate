package ru.clevertec.home.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.dto.LocalDateTimeTypeAdapter;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.mapper.HouseMapper;
import ru.clevertec.home.mapper.HouseMapperImpl;
import ru.clevertec.home.service.HouseService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseDAO houseDAO;

    private final HouseMapper houseMapper;

    private final Gson gson;

    public HouseServiceImpl() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .setPrettyPrinting()
                .create();
        this.houseMapper = new HouseMapperImpl();
    }

    @Override
    @Transactional
    public String findByID(UUID uuid) throws EntityNotFoundException {
        return gson.toJson(houseDAO.findHouseByID(uuid)
                .map(houseMapper::houseToResponse).
                orElseThrow(() -> EntityNotFoundException.of(House.class, uuid)));
    }
}
