package com.sandeepc.ecom.controller;

import com.sandeepc.ecom.dto.ProfileRequestDto;
import com.sandeepc.ecom.dto.ProfileResponseDto;
import com.sandeepc.ecom.service.IProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService iProfileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(){
        ProfileResponseDto profile = iProfileService.getProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(
            @Valid @RequestBody ProfileRequestDto profileRequestDto){
        ProfileResponseDto profileResponseDto = iProfileService.updateProfile(profileRequestDto);
        return ResponseEntity.ok(profileResponseDto);
    }

}
