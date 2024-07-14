package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Employee extends AbsIntAuditingEntity {
    @ManyToOne
    private User user;

    private String position;
}
