package com.Capx.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AssetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;
    private double burPrice;
    @ManyToOne
    private Coin coin;
    @ManyToOne
    private User user;

}
