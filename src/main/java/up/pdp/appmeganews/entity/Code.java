package up.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import lombok.*;
import up.pdp.appmeganews.entity.template.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Code extends AbsIntEntity {
    private String email;
    private String code;
}
