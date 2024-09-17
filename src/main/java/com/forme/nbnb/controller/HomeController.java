package com.forme.nbnb.controller;

import com.forme.nbnb.dto.CreateHomeDto;
import com.forme.nbnb.dto.HomeDto;
import com.forme.nbnb.dto.UpdateHomeDto;
import com.forme.nbnb.entity.Home;
import com.forme.nbnb.mapper.MapperDTO;
import com.forme.nbnb.service.HomeServiceImpl;
import com.forme.nbnb.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeServiceImpl homeService;

    private final ImageUploadService imageUploadService;

    @PostMapping("/{homeId}/upload")
    @ResponseBody
    public ResponseEntity<?> uploadHome(@PathVariable final Long homeId,
                                        @RequestParam("images") final List<MultipartFile> images) {
        try {
            List<String> imageUrls = imageUploadService.uploadImages(images);

            homeService.addImages(homeId, imageUrls);

            return ResponseEntity.ok(imageUrls);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<HomeDto>> getHomes() {
        List<HomeDto> homeDtos =
                homeService.findAll()
                        .stream()
                        .map(MapperDTO::homeToHomeDto)
                        .toList();

        return ResponseEntity.ok(homeDtos);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<HomeDto> createHome(@RequestBody CreateHomeDto createHomeDto) {
        Home newHome = homeService.create(createHomeDto);
        if (newHome == null) {
            return ResponseEntity.badRequest().build();
        }
        HomeDto homeDto = MapperDTO.homeToHomeDto(newHome);
        return ResponseEntity.ok(homeDto);
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
    public ResponseEntity<HomeDto> getHome(@PathVariable Long homeId) {
        Home home = homeService.getDetails(homeId);
        return home != null ?
                ResponseEntity.ok(MapperDTO.homeToHomeDto(home)) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("/owner/{ownerId}")
    @ResponseBody
    public ResponseEntity<List<HomeDto>> getHomeByOwner(@PathVariable Long ownerId) {

        List<HomeDto> ownerHomesDto = homeService.findByOwnerId(ownerId)
                .stream().map(MapperDTO::homeToHomeDto)
                .toList();

        // TODO: need to change Home to dto
        return ownerHomesDto.isEmpty() ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(ownerHomesDto);
    }

    @GetMapping("/{city}")
    @ResponseBody
    public ResponseEntity<List<HomeDto>> getHomeByCity(@PathVariable String city) {
        List<HomeDto> homeDtos = homeService.findByCity(city)
                .stream().map(MapperDTO::homeToHomeDto)
                .toList();

        return homeDtos.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.ok(homeDtos);
    }

    @GetMapping("/{availability}")
    @ResponseBody
    public ResponseEntity<List<HomeDto>> getHomeByAvailability(@PathVariable boolean availability) {
        List<HomeDto> homeDtos = homeService.findByAvailability(availability)
                .stream().map(MapperDTO::homeToHomeDto)
                .toList();

        return homeDtos.isEmpty() ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(homeDtos);
    }


}
