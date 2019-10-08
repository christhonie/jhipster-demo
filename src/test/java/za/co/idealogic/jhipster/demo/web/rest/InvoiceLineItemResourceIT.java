package za.co.idealogic.jhipster.demo.web.rest;

import za.co.idealogic.jhipster.demo.JavatrainingApp;
import za.co.idealogic.jhipster.demo.domain.InvoiceLineItem;
import za.co.idealogic.jhipster.demo.domain.Invoice;
import za.co.idealogic.jhipster.demo.domain.Item;
import za.co.idealogic.jhipster.demo.repository.InvoiceLineItemRepository;
import za.co.idealogic.jhipster.demo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static za.co.idealogic.jhipster.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InvoiceLineItemResource} REST controller.
 */
@SpringBootTest(classes = JavatrainingApp.class)
public class InvoiceLineItemResourceIT {

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private InvoiceLineItemRepository invoiceLineItemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restInvoiceLineItemMockMvc;

    private InvoiceLineItem invoiceLineItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvoiceLineItemResource invoiceLineItemResource = new InvoiceLineItemResource(invoiceLineItemRepository);
        this.restInvoiceLineItemMockMvc = MockMvcBuilders.standaloneSetup(invoiceLineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLineItem createEntity(EntityManager em) {
        InvoiceLineItem invoiceLineItem = new InvoiceLineItem()
            .quantity(DEFAULT_QUANTITY)
            .description(DEFAULT_DESCRIPTION)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        Invoice invoice;
        if (TestUtil.findAll(em, Invoice.class).isEmpty()) {
            invoice = InvoiceResourceIT.createEntity(em);
            em.persist(invoice);
            em.flush();
        } else {
            invoice = TestUtil.findAll(em, Invoice.class).get(0);
        }
        invoiceLineItem.setInvoice(invoice);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        invoiceLineItem.setItem(item);
        return invoiceLineItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceLineItem createUpdatedEntity(EntityManager em) {
        InvoiceLineItem invoiceLineItem = new InvoiceLineItem()
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT);
        // Add required entity
        Invoice invoice;
        if (TestUtil.findAll(em, Invoice.class).isEmpty()) {
            invoice = InvoiceResourceIT.createUpdatedEntity(em);
            em.persist(invoice);
            em.flush();
        } else {
            invoice = TestUtil.findAll(em, Invoice.class).get(0);
        }
        invoiceLineItem.setInvoice(invoice);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createUpdatedEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        invoiceLineItem.setItem(item);
        return invoiceLineItem;
    }

    @BeforeEach
    public void initTest() {
        invoiceLineItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceLineItem() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineItemRepository.findAll().size();

        // Create the InvoiceLineItem
        restInvoiceLineItemMockMvc.perform(post("/api/invoice-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
            .andExpect(status().isCreated());

        // Validate the InvoiceLineItem in the database
        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceLineItem testInvoiceLineItem = invoiceLineItemList.get(invoiceLineItemList.size() - 1);
        assertThat(testInvoiceLineItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceLineItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInvoiceLineItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createInvoiceLineItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceLineItemRepository.findAll().size();

        // Create the InvoiceLineItem with an existing ID
        invoiceLineItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceLineItemMockMvc.perform(post("/api/invoice-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLineItem in the database
        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLineItemRepository.findAll().size();
        // set the field null
        invoiceLineItem.setQuantity(null);

        // Create the InvoiceLineItem, which fails.

        restInvoiceLineItemMockMvc.perform(post("/api/invoice-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
            .andExpect(status().isBadRequest());

        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoiceLineItemRepository.findAll().size();
        // set the field null
        invoiceLineItem.setAmount(null);

        // Create the InvoiceLineItem, which fails.

        restInvoiceLineItemMockMvc.perform(post("/api/invoice-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
            .andExpect(status().isBadRequest());

        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoiceLineItems() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

        // Get all the invoiceLineItemList
        restInvoiceLineItemMockMvc.perform(get("/api/invoice-line-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceLineItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getInvoiceLineItem() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

        // Get the invoiceLineItem
        restInvoiceLineItemMockMvc.perform(get("/api/invoice-line-items/{id}", invoiceLineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceLineItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceLineItem() throws Exception {
        // Get the invoiceLineItem
        restInvoiceLineItemMockMvc.perform(get("/api/invoice-line-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceLineItem() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

        int databaseSizeBeforeUpdate = invoiceLineItemRepository.findAll().size();

        // Update the invoiceLineItem
        InvoiceLineItem updatedInvoiceLineItem = invoiceLineItemRepository.findById(invoiceLineItem.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceLineItem are not directly saved in db
        em.detach(updatedInvoiceLineItem);
        updatedInvoiceLineItem
            .quantity(UPDATED_QUANTITY)
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT);

        restInvoiceLineItemMockMvc.perform(put("/api/invoice-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceLineItem)))
            .andExpect(status().isOk());

        // Validate the InvoiceLineItem in the database
        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeUpdate);
        InvoiceLineItem testInvoiceLineItem = invoiceLineItemList.get(invoiceLineItemList.size() - 1);
        assertThat(testInvoiceLineItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceLineItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInvoiceLineItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceLineItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceLineItemRepository.findAll().size();

        // Create the InvoiceLineItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceLineItemMockMvc.perform(put("/api/invoice-line-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invoiceLineItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceLineItem in the database
        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceLineItem() throws Exception {
        // Initialize the database
        invoiceLineItemRepository.saveAndFlush(invoiceLineItem);

        int databaseSizeBeforeDelete = invoiceLineItemRepository.findAll().size();

        // Delete the invoiceLineItem
        restInvoiceLineItemMockMvc.perform(delete("/api/invoice-line-items/{id}", invoiceLineItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InvoiceLineItem> invoiceLineItemList = invoiceLineItemRepository.findAll();
        assertThat(invoiceLineItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceLineItem.class);
        InvoiceLineItem invoiceLineItem1 = new InvoiceLineItem();
        invoiceLineItem1.setId(1L);
        InvoiceLineItem invoiceLineItem2 = new InvoiceLineItem();
        invoiceLineItem2.setId(invoiceLineItem1.getId());
        assertThat(invoiceLineItem1).isEqualTo(invoiceLineItem2);
        invoiceLineItem2.setId(2L);
        assertThat(invoiceLineItem1).isNotEqualTo(invoiceLineItem2);
        invoiceLineItem1.setId(null);
        assertThat(invoiceLineItem1).isNotEqualTo(invoiceLineItem2);
    }
}
