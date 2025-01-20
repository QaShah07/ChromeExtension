package com.example.stockportfolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Data
public class Price {
    private BigDecimal adjClose;
    private BigDecimal adjHigh;
    private BigDecimal adjLow;
    private BigDecimal adjOpen;
    private long adjVolume;
    private BigDecimal close;
    // Change LocalDateTime -> OffsetDateTime
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX") // Handles timezone offset
    private OffsetDateTime date;
    private BigDecimal divCash;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal open;
    private BigDecimal splitFactor;
    private long volume;
    @Id
    private Long id;


}