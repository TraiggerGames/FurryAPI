package es.recha.furry.model;

import jakarta.persistence.*;

@Entity
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

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
