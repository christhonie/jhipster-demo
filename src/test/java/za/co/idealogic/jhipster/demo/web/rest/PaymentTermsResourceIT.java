package za.co.idealogic.jhipster.demo.web.rest;

import za.co.idealogic.jhipster.demo.JavatrainingApp;
import za.co.idealogic.jhipster.demo.domain.PaymentTerms;
import za.co.idealogic.jhipster.demo.repository.PaymentTermsRepository;
import za.co.idealogic.jhipster.demo.service.PaymentTermsService;
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
import java.util.List;

import static za.co.idealogic.jhipster.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PaymentTermsResource} REST controller.
 */
@SpringBootTest(classes = JavatrainingApp.class)
public class PaymentTermsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAYS = 1;
    private static final Integer UPDATED_DAYS = 2;

    @Autowired
    private PaymentTermsRepository paymentTermsRepository;

    @Autowired
    private PaymentTermsService paymentTermsService;

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

    private MockMvc restPaymentTermsMockMvc;

    private PaymentTerms paymentTerms;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentTermsResource paymentTermsResource = new PaymentTermsResource(paymentTermsService);
        this.restPaymentTermsMockMvc = MockMvcBuilders.standaloneSetup(paymentTermsResource)
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
    public static PaymentTerms createEntity(EntityManager em) {
        PaymentTerms paymentTerms = new PaymentTerms()
            .name(DEFAULT_NAME)
            .days(DEFAULT_DAYS);
        return paymentTerms;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentTerms createUpdatedEntity(EntityManager em) {
        PaymentTerms paymentTerms = new PaymentTerms()
            .name(UPDATED_NAME)
            .days(UPDATED_DAYS);
        return paymentTerms;
    }

    @BeforeEach
    public void initTest() {
        paymentTerms = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentTerms() throws Exception {
        int databaseSizeBeforeCreate = paymentTermsRepository.findAll().size();

        // Create the PaymentTerms
        restPaymentTermsMockMvc.perform(post("/api/payment-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTerms)))
            .andExpect(status().isCreated());

        // Validate the PaymentTerms in the database
        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentTerms testPaymentTerms = paymentTermsList.get(paymentTermsList.size() - 1);
        assertThat(testPaymentTerms.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentTerms.getDays()).isEqualTo(DEFAULT_DAYS);
    }

    @Test
    @Transactional
    public void createPaymentTermsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTermsRepository.findAll().size();

        // Create the PaymentTerms with an existing ID
        paymentTerms.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTermsMockMvc.perform(post("/api/payment-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTerms)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerms in the database
        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTermsRepository.findAll().size();
        // set the field null
        paymentTerms.setName(null);

        // Create the PaymentTerms, which fails.

        restPaymentTermsMockMvc.perform(post("/api/payment-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTerms)))
            .andExpect(status().isBadRequest());

        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTermsRepository.findAll().size();
        // set the field null
        paymentTerms.setDays(null);

        // Create the PaymentTerms, which fails.

        restPaymentTermsMockMvc.perform(post("/api/payment-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTerms)))
            .andExpect(status().isBadRequest());

        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentTerms() throws Exception {
        // Initialize the database
        paymentTermsRepository.saveAndFlush(paymentTerms);

        // Get all the paymentTermsList
        restPaymentTermsMockMvc.perform(get("/api/payment-terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentTerms.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)));
    }
    
    @Test
    @Transactional
    public void getPaymentTerms() throws Exception {
        // Initialize the database
        paymentTermsRepository.saveAndFlush(paymentTerms);

        // Get the paymentTerms
        restPaymentTermsMockMvc.perform(get("/api/payment-terms/{id}", paymentTerms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentTerms.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentTerms() throws Exception {
        // Get the paymentTerms
        restPaymentTermsMockMvc.perform(get("/api/payment-terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentTerms() throws Exception {
        // Initialize the database
        paymentTermsService.save(paymentTerms);

        int databaseSizeBeforeUpdate = paymentTermsRepository.findAll().size();

        // Update the paymentTerms
        PaymentTerms updatedPaymentTerms = paymentTermsRepository.findById(paymentTerms.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentTerms are not directly saved in db
        em.detach(updatedPaymentTerms);
        updatedPaymentTerms
            .name(UPDATED_NAME)
            .days(UPDATED_DAYS);

        restPaymentTermsMockMvc.perform(put("/api/payment-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentTerms)))
            .andExpect(status().isOk());

        // Validate the PaymentTerms in the database
        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeUpdate);
        PaymentTerms testPaymentTerms = paymentTermsList.get(paymentTermsList.size() - 1);
        assertThat(testPaymentTerms.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentTerms.getDays()).isEqualTo(UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentTerms() throws Exception {
        int databaseSizeBeforeUpdate = paymentTermsRepository.findAll().size();

        // Create the PaymentTerms

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentTermsMockMvc.perform(put("/api/payment-terms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTerms)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentTerms in the database
        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentTerms() throws Exception {
        // Initialize the database
        paymentTermsService.save(paymentTerms);

        int databaseSizeBeforeDelete = paymentTermsRepository.findAll().size();

        // Delete the paymentTerms
        restPaymentTermsMockMvc.perform(delete("/api/payment-terms/{id}", paymentTerms.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PaymentTerms> paymentTermsList = paymentTermsRepository.findAll();
        assertThat(paymentTermsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTerms.class);
        PaymentTerms paymentTerms1 = new PaymentTerms();
        paymentTerms1.setId(1L);
        PaymentTerms paymentTerms2 = new PaymentTerms();
        paymentTerms2.setId(paymentTerms1.getId());
        assertThat(paymentTerms1).isEqualTo(paymentTerms2);
        paymentTerms2.setId(2L);
        assertThat(paymentTerms1).isNotEqualTo(paymentTerms2);
        paymentTerms1.setId(null);
        assertThat(paymentTerms1).isNotEqualTo(paymentTerms2);
    }
}
