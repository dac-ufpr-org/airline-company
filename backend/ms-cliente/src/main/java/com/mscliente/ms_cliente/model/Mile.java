package com.mscliente.ms_cliente.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mile")
public class Mile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @CreationTimestamp
    @Column(name = "transaction_date", insertable = false, updatable = false, nullable = false)
    private OffsetDateTime transactionDate;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "description", nullable = false)
    private String description = "COMPRA DE MILHAS";

    @Column(name = "reservation_code")
    private String reservationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_transaction", updatable = false, nullable = false)
    private Transaction transaction;
}
