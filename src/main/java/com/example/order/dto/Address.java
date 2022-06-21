package com.example.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    private String city;
    private String district;
    private String town;
    private String street;
    private String landmark;
    private Long pinCode;
    private Boolean isDefault = true;
}
