package de.mschoeffel.secretsanta.repository;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    public Optional<GroupMember> findByNameAndKeyAndGroup(String name, String key, Group group);

}
