package com.talentwave.web.rest.profilCV;

import com.talentwave.service.dto.profilCV.ProfileCVDTO;
import com.talentwave.service.profilCV.ProfileCVService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author akdim
 */

@RestController
@RequestMapping("/api/profile-cv")
@RequiredArgsConstructor
public class ProfileCVController {

    private final ProfileCVService profileCVService;


    @PostMapping
    public ResponseEntity<ProfileCVDTO> createProfile(@RequestBody ProfileCVDTO dto) {
        ProfileCVDTO created = profileCVService.addProfileCV(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProfileCVDTO> updateProfile(@PathVariable Long id, @RequestBody ProfileCVDTO dto) {
        dto.setId(id);
        ProfileCVDTO updated = profileCVService.updateProfileCV(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileCVService.deleteProfileCV(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProfileCVDTO>> getAllProfileCV() {
        return ResponseEntity.status(HttpStatus.OK).body(profileCVService.getAllProfileCV());
    }
}
