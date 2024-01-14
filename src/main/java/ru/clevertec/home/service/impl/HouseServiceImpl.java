package ru.clevertec.home.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.home.dao.HouseDAO;
import ru.clevertec.home.dto.LocalDateTimeTypeAdapter;
import ru.clevertec.home.dto.request.HouseRequest;
import ru.clevertec.home.entity.House;
import ru.clevertec.home.exception.EntityNotFoundException;
import ru.clevertec.home.mapper.HouseMapper;
import ru.clevertec.home.mapper.HouseMapperImpl;
import ru.clevertec.home.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public String findByID(UUID uuid) throws EntityNotFoundException {
        return gson.toJson(
                houseDAO.findHouseByID(uuid)
                        .map(houseMapper::houseToResponse).
                        orElseThrow(() -> EntityNotFoundException.of(House.class, uuid))
        );
    }

    @Override
    public String findAll(int pageNumber, int pageSize) {
        return gson.toJson(
                houseDAO.findAll(pageNumber, pageSize)
                        .stream()
                        .map(houseMapper::houseToResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public String saveHouse(HouseRequest houseRequest) {
        House house = houseDAO.saveHouse(houseMapper.requestToHouse(houseRequest));
        return gson.toJson(houseMapper.houseToResponse(house));
    }

    @Override
    public String updateHouse(UUID uuid, HouseRequest houseRequest) throws EntityNotFoundException {
        House house = houseDAO.updateHouse(uuid, houseMapper.requestToHouse(houseRequest));
        return gson.toJson(houseMapper.houseToResponse(house));
    }

    @Override
    public String deleteHouse(UUID uuid) {
        return houseDAO.deleteHouse(uuid);
    }

    @Override
    public String findHousesSubstring(String substring) {
        List<House> houses = houseDAO.findHousesSubstring(substring);
        return gson.toJson(
                houses.stream()
                        .map(houseMapper::houseToResponse)
                        .collect(Collectors.toList())
        );
    }
}
