package za.co.idealogic.jhipster.demo.repository;

import za.co.idealogic.jhipster.demo.domain.Item;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Item entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
