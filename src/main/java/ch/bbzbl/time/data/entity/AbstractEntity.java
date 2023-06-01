package ch.bbzbl.time.data.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

/**
 * An abstract base class for entities in the application.
 * Provides common fields and methods for entity classes.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    @SequenceGenerator(name = "idGenerator", initialValue = 1000)
    private Long id;

    @Version
    private int version;

    /**
     * Retrieves the ID of the entity.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the entity.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the version of the entity.
     *
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false; // null or not an AbstractEntity class
        }
        AbstractEntity that = (AbstractEntity) obj;
        if (getId() != null) {
            return getId().equals(that.getId());
        }
        return super.equals(that);
    }
}
