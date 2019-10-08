package za.co.idealogic.jhipster.demo.web.rest;

import za.co.idealogic.jhipster.demo.domain.InvoiceLineItem;
import za.co.idealogic.jhipster.demo.repository.InvoiceLineItemRepository;
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
 * REST controller for managing {@link za.co.idealogic.jhipster.demo.domain.InvoiceLineItem}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLineItemResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineItemResource.class);

    private static final String ENTITY_NAME = "invoiceLineItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceLineItemRepository invoiceLineItemRepository;

    public InvoiceLineItemResource(InvoiceLineItemRepository invoiceLineItemRepository) {
        this.invoiceLineItemRepository = invoiceLineItemRepository;
    }

    /**
     * {@code POST  /invoice-line-items} : Create a new invoiceLineItem.
     *
     * @param invoiceLineItem the invoiceLineItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceLineItem, or with status {@code 400 (Bad Request)} if the invoiceLineItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-line-items")
    public ResponseEntity<InvoiceLineItem> createInvoiceLineItem(@Valid @RequestBody InvoiceLineItem invoiceLineItem) throws URISyntaxException {
        log.debug("REST request to save InvoiceLineItem : {}", invoiceLineItem);
        if (invoiceLineItem.getId() != null) {
            throw new BadRequestAlertException("A new invoiceLineItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceLineItem result = invoiceLineItemRepository.save(invoiceLineItem);
        return ResponseEntity.created(new URI("/api/invoice-line-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-line-items} : Updates an existing invoiceLineItem.
     *
     * @param invoiceLineItem the invoiceLineItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceLineItem,
     * or with status {@code 400 (Bad Request)} if the invoiceLineItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceLineItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-line-items")
    public ResponseEntity<InvoiceLineItem> updateInvoiceLineItem(@Valid @RequestBody InvoiceLineItem invoiceLineItem) throws URISyntaxException {
        log.debug("REST request to update InvoiceLineItem : {}", invoiceLineItem);
        if (invoiceLineItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceLineItem result = invoiceLineItemRepository.save(invoiceLineItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceLineItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-line-items} : get all the invoiceLineItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceLineItems in body.
     */
    @GetMapping("/invoice-line-items")
    public ResponseEntity<List<InvoiceLineItem>> getAllInvoiceLineItems(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of InvoiceLineItems");
        Page<InvoiceLineItem> page = invoiceLineItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-line-items/:id} : get the "id" invoiceLineItem.
     *
     * @param id the id of the invoiceLineItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceLineItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-line-items/{id}")
    public ResponseEntity<InvoiceLineItem> getInvoiceLineItem(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLineItem : {}", id);
        Optional<InvoiceLineItem> invoiceLineItem = invoiceLineItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invoiceLineItem);
    }

    /**
     * {@code DELETE  /invoice-line-items/:id} : delete the "id" invoiceLineItem.
     *
     * @param id the id of the invoiceLineItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-line-items/{id}")
    public ResponseEntity<Void> deleteInvoiceLineItem(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLineItem : {}", id);
        invoiceLineItemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
