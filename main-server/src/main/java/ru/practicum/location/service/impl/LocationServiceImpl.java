package ru.practicum.location.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.location.model.Location;
import ru.practicum.location.service.LocationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }
}
