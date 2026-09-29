package com.sandeepc.ecom.dto;

public record LoginResponseDto(String message,UserDto user, String jwtToken) {
}
