package com.ilya.trpz.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "t_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@Column(unique=true)
	private String email;
	@Column(unique=true)
	private String username;
	private String password;
	private String phoneNumber;
	@Column(name = "is_active")
	private boolean enabled;
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private RoleType role;

	public boolean getEnabled(){
		return  enabled;
	}

}
