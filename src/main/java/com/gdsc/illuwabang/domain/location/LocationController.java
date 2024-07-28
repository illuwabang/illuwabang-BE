package com.gdsc.illuwabang.domain.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final UserService userService;
    private final LocationService locationService;

    @GetMapping("/api/locations")
    public ResponseEntity<?> getLocationAll(Authentication authentication){
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if(exist_user.isPresent()) {
            Map<String, List<LocationDto>> response = new HashMap<>();
            response.put("locationList", locationService.AllLocal());
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @PostMapping("/api/interest-location")
    public ResponseEntity<?> addInterestedLocation(Authentication authentication, @RequestBody Map<String, String> data){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            String city = data.get("city");
            locationService.addInterestedLocation(user, city);
            return ResponseEntity.ok("success");
        }
        else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @GetMapping("/api/interest-location")
    public ResponseEntity<?> userInterestedLocation(Authentication authentication){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            List<ResponseInterestLocationDto> response = locationService.interestedLocationView(user);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @DeleteMapping("/api/interest-location/{interestLocationId}")
    public ResponseEntity<?> deleteInterestedLocation(Authentication authentication, @PathVariable Long interestLocationId){
        Optional<User> existUser = userService.findBySub(authentication.getName());
        if(existUser.isPresent()) {
            User user = existUser.get();
            locationService.deleteInterestedLocation(user, interestLocationId);
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.badRequest().body("fail");
        }
    }
}
