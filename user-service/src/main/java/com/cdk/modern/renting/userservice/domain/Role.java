package com.cdk.modern.renting.userservice.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Table(name="role")
@Entity
public class Role implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "role_id_seq_gen")
    @GenericGenerator(
        name = "role_id_seq_gen",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "role_id_seq"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
        }
    )
    protected Integer id;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "role_authority",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @Fetch(FetchMode.SUBSELECT)
    private Set<Authority> authorities = new HashSet<>();

    private String name;
}
