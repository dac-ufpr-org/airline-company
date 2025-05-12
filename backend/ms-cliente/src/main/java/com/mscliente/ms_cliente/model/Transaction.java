package com.mscliente.ms_cliente.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;
import com.mscliente.ms_cliente.enum.TransactionType;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", insertable = false, updatable = false, nullable = false, unique = true)
    private TransactionType transactionType = TransactionType.ENTRADA;
}
