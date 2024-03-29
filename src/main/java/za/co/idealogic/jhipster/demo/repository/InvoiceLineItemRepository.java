package za.co.idealogic.jhipster.demo.repository;

import za.co.idealogic.jhipster.demo.domain.InvoiceLineItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvoiceLineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceLineItemRepository extends JpaRepository<InvoiceLineItem, Long> {

}
