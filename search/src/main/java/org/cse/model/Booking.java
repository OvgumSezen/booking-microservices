package org.cse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKINGS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "APARTMENT_ID")
    private Integer apartmentId;

    @Setter
    @Column(name = "FROM_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime from;

    @Setter
    @Column(name = "TO_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime to;

    @Column(name = "WHO")
    private String who;

    @Override
    public String toString() {
        return "Booking{id='" + id + "', apartmentId='" + apartmentId + "', from='" + from + "', to='" + to + "', who='" + who + "'}";
    }
}
