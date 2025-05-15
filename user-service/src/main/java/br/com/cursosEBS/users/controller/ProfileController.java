package br.com.cursosEBS.users.controller;

import br.com.cursosEBS.users.dto.ProfileDTO;
import br.com.cursosEBS.users.entity.Profile;
import br.com.cursosEBS.users.entity.Role;
import br.com.cursosEBS.users.repository.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody @Valid ProfileDTO dto, UriComponentsBuilder uriBuilder){
        var profile = profileRepository.save(new Profile(dto.name()));
        URI uri = uriBuilder.path("/profile/{id}").buildAndExpand(profile.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProfileDTO(profile));

    }

    @GetMapping("/{id}")
    ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(new ProfileDTO(profileRepository.findById(id).get()));
    }

    @GetMapping()
    ResponseEntity<Page<ProfileDTO>> getAllProfiles(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(profileRepository.findAllByNameNot(pageable, Role.ADMIN.toString()).map(ProfileDTO::new));
    }
}
