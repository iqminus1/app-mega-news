package up.pdp.appmeganews.entity.template;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import up.pdp.appmeganews.config.IntAuditConfig;

@Getter
@MappedSuperclass
@EntityListeners(IntAuditConfig.class)
public abstract class AbsIntAuditingEntity extends AbsIntEntity {
    @CreatedBy
    private Integer createBy;

    @LastModifiedBy
    private Integer updateBy;
}
