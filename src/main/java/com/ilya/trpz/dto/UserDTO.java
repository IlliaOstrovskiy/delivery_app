package com.ilya.trpz.dto;


import com.ilya.trpz.config.Regex;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    private Long id;
    @NotBlank
    @Pattern(regexp = Regex.regexFirstName)
    private String firstName;
    @NotBlank
    @Pattern(regexp = Regex.regexLastName)
    private String lastName;
    @NotBlank
    @Pattern(regexp = Regex.regexEmail)
    private String email;
    @NotBlank
    @Pattern(regexp = Regex.regexUsername)
    private String username;
    @NotBlank
    @Pattern(regexp = Regex.regexPassword)
    private String password;
    @NotBlank
    @Pattern(regexp = Regex.regexPassword)
    private String passwordConfirm;
    @NotBlank
    @Pattern(regexp = Regex.regexPhone)
    private String phoneNumber;
}

