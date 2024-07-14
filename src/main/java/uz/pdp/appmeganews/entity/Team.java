package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Team extends AbsIntAuditingEntity {
    @ManyToOne
    private Employee leader;

    private String name;

    @ManyToMany
    @ToString.Exclude
    private List<Employee> teammates;

    private String email;

    private String phoneNumber;

    private String fax;

    private String locationDescription;

    private long longitude;

    private long latitude;
}
