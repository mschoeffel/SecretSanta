package de.mschoeffel.secretsanta.repository;

import de.mschoeffel.secretsanta.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
