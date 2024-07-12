package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;
import uz.pdp.appmeganews.enums.PermissionEnum;
import uz.pdp.appmeganews.enums.RoleTypeEnum;

import java.io.Serializable;
import java.sql.Types;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@SQLRestriction("deleted=false")
@SQLDelete(sql = ("update role set deleted = true where id = ?"))
public class Role extends AbsIntAuditingEntity implements Serializable {
    private String name;
    private RoleTypeEnum type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.ARRAY)
    private List<PermissionEnum> permissions;
}
