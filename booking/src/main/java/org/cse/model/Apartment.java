package org.cse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Apartment implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private Integer floor;
    private Integer noiseLevel;

    @Override
    public String toString() {
        return "Apartment{id='" + id + "', name='" + name + "', address='" + address + "', floor='" + floor + "', noiseLevel='" + noiseLevel + "'}";
    }
}
