package ch.bbzbl.time.data.service;

import ch.bbzbl.time.data.Repository.SamplePersonRepository;
import ch.bbzbl.time.data.entity.SamplePerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for performing CRUD operations and business logic on SamplePerson entities.
 */
@Service
public class SamplePersonService {

    private final SamplePersonRepository repository;

    public SamplePersonService(SamplePersonRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a SamplePerson entity by its ID.
     *
     * @param id the ID of the SamplePerson entity
     * @return an Optional containing the SamplePerson entity if found, or an empty Optional if not found
     */
    public Optional<SamplePerson> get(Long id) {
        return repository.findById(id);
    }

    /**
     * Updates a SamplePerson entity.
     *
     * @param entity the SamplePerson entity to update
     * @return the updated SamplePerson entity
     */
    public SamplePerson update(SamplePerson entity) {
        return repository.save(entity);
    }

    /**
     * Deletes a SamplePerson entity by its ID.
     *
     * @param id the ID of the SamplePerson entity to delete
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Retrieves a paginated list of SamplePerson entities.
     *
     * @param pageable the Pageable object specifying the page number, page size, sorting, etc.
     * @return a Page containing the list of SamplePerson entities
     */
    public Page<SamplePerson> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Retrieves a paginated list of SamplePerson entities based on the provided filter.
     *
     * @param pageable the Pageable object specifying the page number, page size, sorting, etc.
     * @param filter   the Specification object representing the filter criteria
     * @return a Page containing the filtered list of SamplePerson entities
     */
    public Page<SamplePerson> list(Pageable pageable, Specification<SamplePerson> filter) {
        return repository.findAll(filter, pageable);
    }

    /**
     * Returns the total count of SamplePerson entities.
     *
     * @return the total count of SamplePerson entities
     */
    public int count() {
        return (int) repository.count();
    }
}
