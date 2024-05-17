package com.cdk.modern.renting.vehicleservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("vehicle_brand")
public class Brand implements Serializable {
    @Id
    protected Integer id;

    @Column
    private String imageUri;

    @Column
    private String name;

    @Column
    private boolean active;

    @CreatedDate
    protected LocalDateTime createdAt;
}
