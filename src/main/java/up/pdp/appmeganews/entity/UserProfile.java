package up.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import up.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@SQLRestriction("deleted = false")
@SQLDelete(sql = ("update user_profile set deleted = true where id = ?"))
public class UserProfile extends AbsIntAuditingEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Attachment banner;

    @ManyToMany
    @ToString.Exclude
    private List<Attachment> imageProfile;
}
