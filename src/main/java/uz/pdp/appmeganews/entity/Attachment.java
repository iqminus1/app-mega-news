package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.*;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@SQLRestriction("deleted = false")
@SQLDelete(sql = ("update attachment set deleted = true where id = ?"))
public class Attachment extends AbsIntAuditingEntity implements Serializable {
    private String name;
    private String path;
    private String contentType;
    private String originalName;
    private Long size;
}
