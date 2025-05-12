package com.mscliente.ms_cliente.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "miles_balance")
    private Double milesBalance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_location", nullable = false)
    private Address address;
}
