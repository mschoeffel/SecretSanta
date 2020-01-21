package de.mschoeffel.secretsanta.results;

import java.util.List;

public class GroupCreationResult {

    private String name;
    private List<GroupMemberCreationResult> members;

    public GroupCreationResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMemberCreationResult> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberCreationResult> members) {
        this.members = members;
    }
}
