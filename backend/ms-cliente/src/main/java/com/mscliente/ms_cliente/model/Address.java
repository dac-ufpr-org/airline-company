package com.mscliente.ms_cliente.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "complement")
    private String complement;
}
