package es.recha.furry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter


@Table(name = "content_status")
public class ContentStatus {
    @Id
    @Column(length = 20)
    private String code;

    @Column(length = 200)
    private String description;

    public ContentStatus() {}

    public ContentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }


}
