package up.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import up.pdp.appmeganews.entity.template.AbsIntAuditingEntity;
import up.pdp.appmeganews.enums.PermissionEnum;
import up.pdp.appmeganews.enums.RoleTypeEnum;

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
public class Role extends AbsIntAuditingEntity {
    private String name;
    private RoleTypeEnum type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.ARRAY)
    private List<PermissionEnum> permissions;
}
