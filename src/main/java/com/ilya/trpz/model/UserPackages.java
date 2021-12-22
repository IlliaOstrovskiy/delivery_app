package com.ilya.trpz.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UserPackages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Package newPackage;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;
    @ManyToOne
    private User courier;
}
