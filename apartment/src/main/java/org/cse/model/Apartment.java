package org.cse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "APARTMENTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Apartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FLOOR")
    private Integer floor;

    @Column(name = "NOISE_LEVEL")
    private Integer noiseLevel;

    @Override
    public String toString() {
        return "Apartment{id='" + id + "', name='" + name + "', address='" + address + "', floor='" + floor + "', noiseLevel='" + noiseLevel + "'}";
    }
}
