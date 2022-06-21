package com.example.order.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderItemId;

    private Integer orderId;

    private Integer medicineId;

    private Integer batchId;

    private Integer quantity;
}
