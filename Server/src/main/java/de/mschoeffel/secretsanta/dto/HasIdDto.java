package de.mschoeffel.secretsanta.dto;

public abstract class HasIdDto {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
