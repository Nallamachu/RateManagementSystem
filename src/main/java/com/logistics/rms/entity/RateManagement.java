package com.logistics.rms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    @NonNull
    private LocalDateTime effectiveDate;
    @NonNull
    private LocalDateTime expirationDate;
    @NonNull
    private Integer amount;
}
