package de.mschoeffel.secretsanta.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Group")
@Table(name = "DrawGroup", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Group extends HasId {

    private String name;
    private Integer rerolls;
    private String token;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMember> groupMember;

    public Group() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRerolls() {
        return rerolls;
    }

    public void setRerolls(Integer rerolls) {
        this.rerolls = rerolls;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<GroupMember> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(List<GroupMember> groupMember) {
        this.groupMember = groupMember;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", rerolls=" + rerolls +
                ", groupMember=" + groupMember +
                ", id=" + id +
                '}';
    }
}
