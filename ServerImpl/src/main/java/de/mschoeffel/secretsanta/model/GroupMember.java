package de.mschoeffel.secretsanta.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "GroupMember")
@Table(name = "GroupMember")
public class GroupMember extends HasId{

    private String name;
    private String key;
    private Integer rerolls;
    private Boolean drawAccepted;
    private LocalDateTime lastDrawTime;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne
    @JoinColumn(name = "partner_id")
    private GroupMember partner;

    public GroupMember() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getRerolls() {
        return rerolls;
    }

    public void setRerolls(Integer rerolls) {
        this.rerolls = rerolls;
    }

    public Boolean getDrawAccepted() {
        return drawAccepted;
    }

    public void setDrawAccepted(Boolean drawAccepted) {
        this.drawAccepted = drawAccepted;
    }

    public LocalDateTime getLastDrawTime() {
        return lastDrawTime;
    }

    public void setLastDrawTime(LocalDateTime lastDrawTime) {
        this.lastDrawTime = lastDrawTime;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public GroupMember getPartner() {
        return partner;
    }

    public void setPartner(GroupMember partner) {
        this.partner = partner;
    }
}
