package es.recha.furry.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter

@Table(name = "visibility")
public class Visibility {
    @Id
    @Column(length = 20)
    private String code;

    @Column(length = 200)
    private String description;

    public Visibility() {}

    public Visibility(String code, String description) {
        this.code = code;
        this.description = description;
    }


}
