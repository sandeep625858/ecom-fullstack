package com.sandeepc.ecom.service;

import com.sandeepc.ecom.dto.ProfileRequestDto;
import com.sandeepc.ecom.dto.ProfileResponseDto;

public interface IProfileService {

    ProfileResponseDto getProfile();

    ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto);

}
