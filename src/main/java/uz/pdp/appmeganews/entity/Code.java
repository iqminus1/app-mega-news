package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.appmeganews.entity.template.AbsIntEntity;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Code extends AbsIntEntity implements Serializable {
    private String email;
    private String code;

    private Integer attempt;

}
