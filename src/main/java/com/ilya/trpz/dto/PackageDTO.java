package com.ilya.trpz.dto;

import com.ilya.trpz.config.Regex;
import com.ilya.trpz.model.Department;
import com.ilya.trpz.model.StatusPackage;
import com.ilya.trpz.model.TypePackage;
import com.ilya.trpz.model.User;
import lombok.*;

import javax.persistence.*;
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
public class PackageDTO {
    @NotBlank
    @Pattern(regexp = Regex.regexTitle)
    private String title;
    private LocalDateTime dateSend;
    private LocalDateTime dateReceive;
    @NotNull
    private Double weight;
    @NotNull
    private Double price;
}
