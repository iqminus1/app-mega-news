package up.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import up.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@SQLRestriction("deleted = false")
@SQLDelete(sql = ("update attachment set deleted = true where id = ?"))
public class Attachment extends AbsIntAuditingEntity {
    private String name;
    private String path;
    private String contentType;
    private String originalName;
    private Long size;
}
