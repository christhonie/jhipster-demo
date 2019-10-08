package za.co.idealogic.jhipster.demo.web.rest;

import za.co.idealogic.jhipster.demo.domain.PaymentTerms;
import za.co.idealogic.jhipster.demo.service.PaymentTermsService;
import za.co.idealogic.jhipster.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link za.co.idealogic.jhipster.demo.domain.PaymentTerms}.
 */
@RestController
@RequestMapping("/api")
public class PaymentTermsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTermsResource.class);

    private static final String ENTITY_NAME = "paymentTerms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentTermsService paymentTermsService;

    public PaymentTermsResource(PaymentTermsService paymentTermsService) {
        this.paymentTermsService = paymentTermsService;
    }

    /**
     * {@code POST  /payment-terms} : Create a new paymentTerms.
     *
     * @param paymentTerms the paymentTerms to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentTerms, or with status {@code 400 (Bad Request)} if the paymentTerms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-terms")
    public ResponseEntity<PaymentTerms> createPaymentTerms(@Valid @RequestBody PaymentTerms paymentTerms) throws URISyntaxException {
        log.debug("REST request to save PaymentTerms : {}", paymentTerms);
        if (paymentTerms.getId() != null) {
            throw new BadRequestAlertException("A new paymentTerms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTerms result = paymentTermsService.save(paymentTerms);
        return ResponseEntity.created(new URI("/api/payment-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-terms} : Updates an existing paymentTerms.
     *
     * @param paymentTerms the paymentTerms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentTerms,
     * or with status {@code 400 (Bad Request)} if the paymentTerms is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentTerms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-terms")
    public ResponseEntity<PaymentTerms> updatePaymentTerms(@Valid @RequestBody PaymentTerms paymentTerms) throws URISyntaxException {
        log.debug("REST request to update PaymentTerms : {}", paymentTerms);
        if (paymentTerms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentTerms result = paymentTermsService.save(paymentTerms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentTerms.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-terms} : get all the paymentTerms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentTerms in body.
     */
    @GetMapping("/payment-terms")
    public ResponseEntity<List<PaymentTerms>> getAllPaymentTerms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of PaymentTerms");
        Page<PaymentTerms> page = paymentTermsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-terms/:id} : get the "id" paymentTerms.
     *
     * @param id the id of the paymentTerms to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentTerms, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-terms/{id}")
    public ResponseEntity<PaymentTerms> getPaymentTerms(@PathVariable Long id) {
        log.debug("REST request to get PaymentTerms : {}", id);
        Optional<PaymentTerms> paymentTerms = paymentTermsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentTerms);
    }

    /**
     * {@code DELETE  /payment-terms/:id} : delete the "id" paymentTerms.
     *
     * @param id the id of the paymentTerms to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-terms/{id}")
    public ResponseEntity<Void> deletePaymentTerms(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTerms : {}", id);
        paymentTermsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
