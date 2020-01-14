package de.mschoeffel.secretsanta.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity(name = "Group")
@Table(name = "Group")
public class Group extends HasId {

    private String name;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMember;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMember> getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(List<GroupMember> groupMember) {
        this.groupMember = groupMember;
    }
}
