package tn.esprit.spring.wecare.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @Column(name = "event_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String name;
    private String description;
    private String type;
    private String mail;
    @Max(5)
    @Min(0)
    private float rating = 0;
    private Date date;

}
