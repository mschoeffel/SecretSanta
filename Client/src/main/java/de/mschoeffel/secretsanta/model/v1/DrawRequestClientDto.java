package de.mschoeffel.secretsanta.model.v1;

public class DrawRequestClientDto {

    private String groupname;
    private String name;
    private String key;

    public DrawRequestClientDto() {
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    @Override
    public String toString() {
        return "DrawRequestClientDto{" +
                "groupname='" + groupname + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
