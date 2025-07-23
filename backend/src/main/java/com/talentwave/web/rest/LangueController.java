package com.talentwave.web.rest;

import com.talentwave.service.dto.langue.LangueDTO;
import com.talentwave.service.langue.LangueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author akdim
 */

@RestController
@RequestMapping("/api/langue")
public class LangueController {
    @Autowired
    LangueService langueService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<LangueDTO> addLangue(@RequestBody LangueDTO langueDTO) {
        LangueDTO langueDTO1 = langueService.addLangue(langueDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(langueDTO1);
    }

    @GetMapping
    ResponseEntity<List<LangueDTO>> getAllLangues() {
        List<LangueDTO> langues = langueService.getAllLangues();
        return ResponseEntity.status(HttpStatus.OK).body(langues);
    }
}
