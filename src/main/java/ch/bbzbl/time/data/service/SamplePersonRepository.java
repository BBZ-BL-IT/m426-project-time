package ch.bbzbl.time.data.service;

import ch.bbzbl.time.data.entity.SamplePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for performing CRUD operations on SamplePerson entities.
 * Inherits common repository interfaces from JpaRepository and JpaSpecificationExecutor.
 */
public interface SamplePersonRepository extends JpaRepository<SamplePerson, Long>, JpaSpecificationExecutor<SamplePerson> {

}
