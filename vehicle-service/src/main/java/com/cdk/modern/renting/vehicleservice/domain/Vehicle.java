package com.cdk.modern.renting.vehicleservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class Vehicle {
    @Id
    protected UUID id;

    @Column
    private Integer modelId;

    @Transient
    private Model model;

    @Column
    private String imageUri;

    @Column
    private String rentPrice;

    @Column
    private String name;

    @Column
    private String color;

    @Column
    private Short productionYear;

    @Column
    private Short seatCount;

    @Column
    private String transmission;

    @Column
    private String fuelType;

    @Column
    private boolean active;

    @CreatedDate
    protected LocalDateTime createdAt;
}
