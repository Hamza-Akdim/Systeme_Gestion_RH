package com.talentwave.web.rest.profilCV;

import com.talentwave.service.dto.profilCV.LangueConsultantDTO;
import com.talentwave.service.profilCV.LangueConsultantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author akdim
 */

@RestController
@RequestMapping("/api/langue-consultant")
public class LangueConsultantController {
    @Autowired
    LangueConsultantService langueConsultantService;

    @PostMapping
    public ResponseEntity<LangueConsultantDTO> addLangueConsultant(@RequestBody LangueConsultantDTO langueConsultantDTO){
        LangueConsultantDTO langueConsultantDTO1 =  langueConsultantService.addLangueConsultant(langueConsultantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(langueConsultantDTO1);
    }

    @PostMapping("/update")
    public ResponseEntity<LangueConsultantDTO> updateLangueConsultant(@RequestBody LangueConsultantDTO langueConsultantDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(langueConsultantService.updateLangueConsultant(langueConsultantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLangueConsultant(@PathVariable Long id) {
        try {
            langueConsultantService.deleteLangueConsultant(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LangueConsultantDTO> getLangueConsultant(@PathVariable Long id) {
        try {
            LangueConsultantDTO dto = langueConsultantService.getLangueConsultant(id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
