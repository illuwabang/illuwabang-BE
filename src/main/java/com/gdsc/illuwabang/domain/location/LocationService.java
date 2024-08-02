package com.gdsc.illuwabang.domain.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.illuwabang.domain.user.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserInterestLocationRepository userInterestLocationRepository;

    @Value("${locations.file.path}")
    private Resource locationsFile;

    @PostConstruct
    public void init() {
        try (InputStream inputStream = locationsFile.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> locations = objectMapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
            saveLocations(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLocations(List<Map<String, Object>> locations) {
        List<Location> locationList = new ArrayList<>();
        for (Map<String, Object> location : locations) {
            String cityName = (String) location.get("name");
            List<Map<String, String>> gugunList = (List<Map<String, String>>) location.get("gugun");

            for (Map<String, String> gugun : gugunList) {
                String gugunName = gugun.get("name");
                String fullName = cityName + " " + gugunName;
                Location loc = new Location();
                loc.setName(fullName);
                locationList.add(loc);
            }
        }
        locationRepository.saveAll(locationList);
    }

    public List<LocationDto> AllLocal(){
        List<LocationDto> locationDtos = locationRepository.findALL();
        return locationDtos;
    }


    public void addInterestedLocation(User user, String city){
        Location location = locationRepository.findByName(city);
        if(location != null && userInterestLocationRepository.findByUserIdAndLocationId(user,location) == null){
            UserInterestLocation userInterestLocation = new UserInterestLocation(user, location);
            userInterestLocationRepository.save(userInterestLocation);
        }
    }

    public List<ResponseInterestLocationDto> interestedLocationView(User user){
        return userInterestLocationRepository.findByUserIdTransResponse(user);
    }

    public void deleteInterestedLocation(User user, Long locationId){
        Optional<Location> existLocation = locationRepository.findById(locationId);
        if(existLocation.isPresent()){
            Location location = existLocation.get();
            userInterestLocationRepository.deleteByUserIdAndLocationId(user, location);
        }
    }
}
