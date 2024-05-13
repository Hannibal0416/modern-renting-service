package com.cdk.modern.renting.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity(name = "User")
@Table(name = "app_user")
public class User implements Serializable {
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_role",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Fetch(FetchMode.JOIN)
	private Set<Role> roles = new HashSet<>();

	@Column(nullable = false, unique = true)
	private String username;

	private String password;

	private String email;

	private String phone;

	private String avatarUrl;

	private boolean active;

	@CreationTimestamp
	protected LocalDateTime createdAt;
}
