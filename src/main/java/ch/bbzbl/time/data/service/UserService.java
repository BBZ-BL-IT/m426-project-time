package ch.bbzbl.time.data.service;

import ch.bbzbl.time.data.Repository.UserRepository;
import ch.bbzbl.time.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for performing CRUD operations and business logic on User entities.
 */
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a User entity by its ID.
     *
     * @param id the ID of the User entity
     * @return an Optional containing the User entity if found, or an empty Optional if not found
     */
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    /**
     * Updates or saves a User entity.
     *
     * @param entity the User entity to update or save
     * @return the updated or saved User entity
     */
    public User update(User entity) {
        return repository.save(entity);
    }

    /**
     * Deletes a User entity by its ID.
     *
     * @param id the ID of the User entity to delete
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Retrieves a page of User entities.
     *
     * @param pageable the pageable information
     * @return a Page containing the User entities
     */
    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Retrieves a page of filtered User entities.
     *
     * @param pageable the pageable information
     * @param filter   the specification for filtering the User entities
     * @return a Page containing the filtered User entities
     */
    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    /**
     * Retrieves the total count of User entities.
     *
     * @return the total count of User entities
     */
    public int count() {
        return (int) repository.count();
    }

}
