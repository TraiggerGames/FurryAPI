package es.recha.furry.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter

@Table(name = "link_origen")
public class LinkOrigen {
    @Id
    @Column(length = 30)
    private String code;

    @Column(length = 200)
    private String description;

    public LinkOrigen() {}

    public LinkOrigen(String code, String description) {
        this.code = code;
        this.description = description;
    }


}
