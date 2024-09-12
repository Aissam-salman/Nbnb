package com.forme.nbnb.controller;

import com.forme.nbnb.dto.CreateHomeDto;
import com.forme.nbnb.dto.UpdateHomeDto;
import com.forme.nbnb.entity.Home;
import com.forme.nbnb.service.HomeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeServiceImpl homeService;


    //TODO: change response type to select info

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Home>> getHomes() {
        return ResponseEntity.ok(homeService.findAll());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Home> createHome(@RequestBody CreateHomeDto createHomeDto) {
        Home newHome = homeService.create(createHomeDto);
        return newHome != null ?
                ResponseEntity.ok(newHome) :
                ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{homeId}")
    @ResponseBody
    public ResponseEntity<?> updateHome(
            @RequestBody UpdateHomeDto updateHomeDto,
            @PathVariable Long homeId
    ) {
        return homeService.updateAll(homeId, updateHomeDto) ?
                ResponseEntity.ok().body("success updated home") :
                ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{homeId}")
    @ResponseBody
    public ResponseEntity<?> deleteHome(@PathVariable Long homeId) {
        return homeService.delete(homeId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok().build();
    }

    @PatchMapping("/availability/{homeId}")
    @ResponseBody
    public ResponseEntity<?> updateAvailability(
            @PathVariable Long homeId,
            @RequestBody boolean availability) {

        return homeService.updateAvailability(homeId, availability) ?
                ResponseEntity.ok("Update availability success") :
                ResponseEntity.badRequest().build();
    }


    @GetMapping("/{homeId}")
    @ResponseBody
    public ResponseEntity<Home> getHome(@PathVariable Long homeId) {
        Home home = homeService.getDetails(homeId);
        return home != null ?
                ResponseEntity.ok(home) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("/owner/{ownerId}")
    @ResponseBody
    public ResponseEntity<List<Home>> getHomeByOwner(@PathVariable Long ownerId) {
        List<Home> ownerHomes = homeService.findByOwnerId(ownerId);
        // TODO: need to change Home to dto
        return ownerHomes.isEmpty() ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(ownerHomes);
    }

    @GetMapping("/{city}")
    @ResponseBody
    public ResponseEntity<List<Home>> getHomeByCity(@PathVariable String city) {
        List<Home> homeList = homeService.findByCity(city);
        return homeList.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.ok(homeList);
    }

    @GetMapping("/{availability}")
    @ResponseBody
    public ResponseEntity<List<Home>> getHomeByAvailability(@PathVariable boolean availability) {
        List<Home> homeList = homeService.findByAvailability(availability);
        return homeList.isEmpty() ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(homeList);
    }


}
