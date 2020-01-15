package de.mschoeffel.secretsanta.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime insdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime upddate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInsdate() {
        return insdate;
    }

    public void setInsdate(LocalDateTime insdate) {
        this.insdate = insdate;
    }

    public LocalDateTime getUpddate() {
        return upddate;
    }

    public void setUpddate(LocalDateTime upddate) {
        this.upddate = upddate;
    }

}
