package com.logistics.rms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateDTO {
    private Long id;
    private String description;
    @NonNull
    @JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss.fffZ")
    private String effectiveDate;
    @NonNull
    @JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss.fffZ")
    private String expirationDate;
    @NonNull
    private Integer amount;
}
