package uz.pdp.appmeganews.entity.template;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.pdp.appmeganews.config.IntAuditConfig;

import java.io.Serializable;

@Getter
@MappedSuperclass
@EntityListeners(IntAuditConfig.class)
public abstract class AbsIntAuditingEntity extends AbsIntEntity  implements Serializable {
    @CreatedBy
    private Integer createBy;

    @LastModifiedBy
    private Integer updateBy;
}
