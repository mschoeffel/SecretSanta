package de.mschoeffel.secretsanta.repository;

import de.mschoeffel.secretsanta.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);
    Optional<Group> findByNameAndToken(String name, String token);
    boolean existsByName(String name);
    void deleteByNameAndToken(String name, String token);

}
