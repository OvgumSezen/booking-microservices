package org.cse.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class BookingResponse {
    private Integer id;
    private Integer apartmentId;
    private LocalDateTime from;
    private LocalDateTime to;
    private String who;
}
