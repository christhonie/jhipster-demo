package za.co.idealogic.jhipster.demo.service.impl;

import za.co.idealogic.jhipster.demo.service.PaymentTermsService;
import za.co.idealogic.jhipster.demo.domain.PaymentTerms;
import za.co.idealogic.jhipster.demo.repository.PaymentTermsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaymentTerms}.
 */
@Service
@Transactional
public class PaymentTermsServiceImpl implements PaymentTermsService {

    private final Logger log = LoggerFactory.getLogger(PaymentTermsServiceImpl.class);

    private final PaymentTermsRepository paymentTermsRepository;

    public PaymentTermsServiceImpl(PaymentTermsRepository paymentTermsRepository) {
        this.paymentTermsRepository = paymentTermsRepository;
    }

    /**
     * Save a paymentTerms.
     *
     * @param paymentTerms the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentTerms save(PaymentTerms paymentTerms) {
        log.debug("Request to save PaymentTerms : {}", paymentTerms);
        return paymentTermsRepository.save(paymentTerms);
    }

    /**
     * Get all the paymentTerms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentTerms> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTerms");
        return paymentTermsRepository.findAll(pageable);
    }


    /**
     * Get one paymentTerms by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTerms> findOne(Long id) {
        log.debug("Request to get PaymentTerms : {}", id);
        return paymentTermsRepository.findById(id);
    }

    /**
     * Delete the paymentTerms by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentTerms : {}", id);
        paymentTermsRepository.deleteById(id);
    }
}
