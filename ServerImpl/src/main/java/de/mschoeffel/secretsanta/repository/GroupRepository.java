package de.mschoeffel.secretsanta.repository;

import de.mschoeffel.secretsanta.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
