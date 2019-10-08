package za.co.idealogic.jhipster.demo.repository;

import za.co.idealogic.jhipster.demo.domain.PaymentTerms;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentTerms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentTermsRepository extends JpaRepository<PaymentTerms, Long> {

}
