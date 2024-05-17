package com.cdk.modern.renting.vehicleservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("vehicle_model")
public class Model implements Serializable {
    @Id
    protected Integer id;

    @Column
    private Integer typeId;

    @Transient
    private Type type;

    @Column
    private Integer brandId;

    @Transient
    private Brand brand;

    @Column
    private String imageUri;

    @Column
    private String name;

    @Column
    private boolean active;

    @CreatedDate
    protected LocalDateTime createdAt;
}
