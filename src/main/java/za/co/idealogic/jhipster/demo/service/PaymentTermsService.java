package za.co.idealogic.jhipster.demo.service;

import za.co.idealogic.jhipster.demo.domain.PaymentTerms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PaymentTerms}.
 */
public interface PaymentTermsService {

    /**
     * Save a paymentTerms.
     *
     * @param paymentTerms the entity to save.
     * @return the persisted entity.
     */
    PaymentTerms save(PaymentTerms paymentTerms);

    /**
     * Get all the paymentTerms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentTerms> findAll(Pageable pageable);


    /**
     * Get the "id" paymentTerms.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentTerms> findOne(Long id);

    /**
     * Delete the "id" paymentTerms.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
