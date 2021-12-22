package com.ilya.trpz.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "t_department", uniqueConstraints = { @UniqueConstraint( columnNames = { "town", "depart" } ) })
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String town;
    private Long depart;
}
