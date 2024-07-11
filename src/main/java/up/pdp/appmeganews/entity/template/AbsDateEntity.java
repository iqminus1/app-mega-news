package up.pdp.appmeganews.entity.template;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;


@MappedSuperclass
@Getter
public abstract class AbsDateEntity {
    @CreatedDate
    private Timestamp createAt;

    @LastModifiedDate
    private Timestamp updateAt;

}
