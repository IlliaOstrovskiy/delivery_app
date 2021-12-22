package com.ilya.trpz.model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "t_package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private TypePackage type;
    @ManyToOne
    @JoinColumn
    private Department depart_send;
    @ManyToOne
    @JoinColumn
    private Department depart_receive;
    @Enumerated(EnumType.STRING)
    private StatusPackage status;
    private String title;
    @Column(name = "date_send")
    @DateTimeFormat(pattern = "yyyy-MM-dd, HH:mm")
    private LocalDateTime dateSend;
    @Column(name = "date_receive")
    @DateTimeFormat(pattern = "yyyy-MM-dd, HH:mm")
    private LocalDateTime dateReceive;
    private Double weight;
    private Double price;

}
