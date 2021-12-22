package com.ilya.trpz.dto;


import com.ilya.trpz.config.Regex;
import com.ilya.trpz.model.Package;
import com.ilya.trpz.model.User;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserPackageDTO {
    private long id;
    private Package newPackage;
    private User sender;
    private User recipient;
    private User courier;

}
