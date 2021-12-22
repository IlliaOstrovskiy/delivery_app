package com.ilya.trpz.dto;

import com.ilya.trpz.config.Regex;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SimpleUserDTO {
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
    private String username;
    private String password;
    private boolean enabled;
}
