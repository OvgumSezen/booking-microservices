package org.cse.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ApartmentResponse {
    private Integer id;
    private String name;
    private String address;
    private Integer noiseLevel;
    private Integer floor;
}
