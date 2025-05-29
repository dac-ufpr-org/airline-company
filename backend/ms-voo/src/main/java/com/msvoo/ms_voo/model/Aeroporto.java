package com.msvoo.ms_voo.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="airport")
public class Aeroporto implements Serializable {
    @Id
    @Column(name = "id", length = 3)
    private String id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="state", nullable = false)
    private String state;
}