package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Category extends AbsIntAuditingEntity {
    private String name;


}
