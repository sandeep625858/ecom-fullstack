package com.sandeepc.ecom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDto {

    @NotBlank(message = "Name can't be empty!")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @NotBlank(message = "Email can't be empty!")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Mobile Number can't be empty!")
    @Pattern(regexp = "^\\d{10}$",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Message can't be empty!")
    @Size(min = 5, max = 500, message = "Name must be between 5 and 500 characters")
    private String message;


}
