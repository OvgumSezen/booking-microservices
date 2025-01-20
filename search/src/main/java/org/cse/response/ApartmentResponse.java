package org.cse.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ApartmentResponse {
    private Integer id;
    private String name;
    private String address;
    private Integer noiseLevel;
    private Integer floor;
}
