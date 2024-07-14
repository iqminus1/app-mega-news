package uz.pdp.appmeganews.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.appmeganews.entity.template.AbsIntAuditingEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Post extends AbsIntAuditingEntity {
    private String title;
    private String tags;
    private String explanation;

    @ManyToOne
    private Attachment wallpaper;

    @ManyToMany
    @ToString.Exclude
    private List<Attachment> attachments;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Attachment background;
}
