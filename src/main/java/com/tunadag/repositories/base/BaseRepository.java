package com.tunadag.repositories.base;

import com.tunadag.exceptions.custom.ResourceNotFoundException;
import com.tunadag.repositories.entity.base.BaseEntity;
import com.tunadag.repositories.entity.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, Oid> extends JpaRepository<T, Oid> {

    /*
        Find Methods For Active Entities
     */


    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'ACTIVE' AND t.oid = ?1")
    Optional<T> findActiveById(@NonNull Oid oid);


    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'ACTIVE' ORDER BY t.updatedAt DESC")
    List<T> findAllActive();

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'ACTIVE' ORDER BY t.updatedAt DESC")
    Page<T> findAllActive(@NonNull Pageable pageable);

    /*
        Find Methods For Deleted Entities
     */

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'DELETED' AND t.oid = ?1")
    Optional<T> findDeletedById(Oid oid);

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'DELETED' ORDER BY t.updatedAt DESC")
    List<T> findAllDeleted();

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'DELETED' ORDER BY t.updatedAt DESC")
    Page<T> findAllDeleted(Pageable pageable);

    /*
        Modified Delete Methods
     */


    default boolean softDeleteById(Oid oid){
        T entity = findActiveById(oid).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        entity.setState(State.DELETED);
        save(entity);
        return true;
    };

}
