package ch.bbzbl.time.data.service;

import ch.bbzbl.time.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for performing CRUD operations and querying on User entities.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Finds a User entity by its username.
     *
     * @param username the username of the User entity
     * @return the User entity if found, or null if not found
     */
    User findByUsername(String username);
}
