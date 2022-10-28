package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Localite;
import com.mycompany.myapp.domain.enumeration.DAKAR;
import com.mycompany.myapp.domain.enumeration.DIOURBEL;
import com.mycompany.myapp.domain.enumeration.FATICK;
import com.mycompany.myapp.domain.enumeration.KAFFRINE;
import com.mycompany.myapp.domain.enumeration.KAOLACK;
import com.mycompany.myapp.domain.enumeration.KEDOUGOU;
import com.mycompany.myapp.domain.enumeration.KOLDA;
import com.mycompany.myapp.domain.enumeration.LOUGA;
import com.mycompany.myapp.domain.enumeration.MATAM;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.SAINTLOUIS;
import com.mycompany.myapp.domain.enumeration.SEDHIOU;
import com.mycompany.myapp.domain.enumeration.TAMBACOUNDA;
import com.mycompany.myapp.domain.enumeration.THIES;
import com.mycompany.myapp.domain.enumeration.ZIGUINCHOR;
import com.mycompany.myapp.repository.LocaliteRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LocaliteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocaliteResourceIT {

    private static final NomRegion DEFAULT_REGION = NomRegion.DAKAR;
    private static final NomRegion UPDATED_REGION = NomRegion.DIOURBEL;

    private static final String DEFAULT_AUTRE_REGION = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_REGION = "BBBBBBBBBB";

    private static final DAKAR DEFAULT_DEPARTEMENT_DAKAR = DAKAR.DAKAR;
    private static final DAKAR UPDATED_DEPARTEMENT_DAKAR = DAKAR.GUEDIAWAYE;

    private static final DIOURBEL DEFAULT_DEPARTEMENT_DIOURBEL = DIOURBEL.BAMBAEY;
    private static final DIOURBEL UPDATED_DEPARTEMENT_DIOURBEL = DIOURBEL.DIOURBEL;

    private static final FATICK DEFAULT_DEPARTEMENT_FATICK = FATICK.FATICK;
    private static final FATICK UPDATED_DEPARTEMENT_FATICK = FATICK.FOUNDIOUGNE;

    private static final KAFFRINE DEFAULT_DEPARTEMENT_KAFFRINE = KAFFRINE.BIRKILANE;
    private static final KAFFRINE UPDATED_DEPARTEMENT_KAFFRINE = KAFFRINE.KAFFRINE;

    private static final KAOLACK DEFAULT_DEPARTEMENT_KAOLACK = KAOLACK.GUINGUINEO;
    private static final KAOLACK UPDATED_DEPARTEMENT_KAOLACK = KAOLACK.KAOLOACK;

    private static final KEDOUGOU DEFAULT_DEPARTEMENT_KEDOUGOU = KEDOUGOU.KEDOUGOU;
    private static final KEDOUGOU UPDATED_DEPARTEMENT_KEDOUGOU = KEDOUGOU.SALAMATA;

    private static final KOLDA DEFAULT_DEPARTEMENT_KOLDA = KOLDA.KOLDA;
    private static final KOLDA UPDATED_DEPARTEMENT_KOLDA = KOLDA.MEDINA_YORO_FOULAH;

    private static final LOUGA DEFAULT_DEPARTEMENT_LOUGA = LOUGA.KEBEMERE;
    private static final LOUGA UPDATED_DEPARTEMENT_LOUGA = LOUGA.LINGUERE;

    private static final MATAM DEFAULT_DEPARTEMENT_MATAM = MATAM.KANELKANEL;
    private static final MATAM UPDATED_DEPARTEMENT_MATAM = MATAM.MATAM;

    private static final SAINTLOUIS DEFAULT_DEPARTEMENT_SAINT = SAINTLOUIS.DAGANA;
    private static final SAINTLOUIS UPDATED_DEPARTEMENT_SAINT = SAINTLOUIS.PODOR;

    private static final SEDHIOU DEFAULT_DEPARTEMENT_SEDHIOU = SEDHIOU.BOUNKILING;
    private static final SEDHIOU UPDATED_DEPARTEMENT_SEDHIOU = SEDHIOU.GOUDOMP;

    private static final TAMBACOUNDA DEFAULT_DEPARTEMENT_TAMBACOUNDA = TAMBACOUNDA.BAKEL;
    private static final TAMBACOUNDA UPDATED_DEPARTEMENT_TAMBACOUNDA = TAMBACOUNDA.GOUDIRY;

    private static final THIES DEFAULT_DEPARTEMENT_THIS = THIES.MBOUR;
    private static final THIES UPDATED_DEPARTEMENT_THIS = THIES.THIES;

    private static final ZIGUINCHOR DEFAULT_DEPARTEMENT_ZIGUINCHOR = ZIGUINCHOR.BIGNONA;
    private static final ZIGUINCHOR UPDATED_DEPARTEMENT_ZIGUINCHOR = ZIGUINCHOR.OUSSOUYE;

    private static final String DEFAULT_AUTREDEPARTEMENT_DAKAR = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_DAKAR = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_DIOURBEL = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_DIOURBEL = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_FATICK = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_FATICK = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_KAFFRINE = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_KAFFRINE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_KAOLACK = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_KAOLACK = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_KEDOUGOU = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_KEDOUGOU = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_KOLDA = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_KOLDA = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_LOUGA = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_LOUGA = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_MATAM = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_MATAM = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_SAINT = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_SAINT = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_SEDHIOU = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_SEDHIOU = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_TAMBACOUNDA = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_THIS = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_THIS = "BBBBBBBBBB";

    private static final String DEFAULT_AUTREDEPARTEMENT_ZIGUINCHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTREDEPARTEMENT_ZIGUINCHOR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_QUARTIER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_QUARTIER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/localites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocaliteRepository localiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocaliteMockMvc;

    private Localite localite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localite createEntity(EntityManager em) {
        Localite localite = new Localite()
            .region(DEFAULT_REGION)
            .autreRegion(DEFAULT_AUTRE_REGION)
            .departementDakar(DEFAULT_DEPARTEMENT_DAKAR)
            .departementDiourbel(DEFAULT_DEPARTEMENT_DIOURBEL)
            .departementFatick(DEFAULT_DEPARTEMENT_FATICK)
            .departementKaffrine(DEFAULT_DEPARTEMENT_KAFFRINE)
            .departementKaolack(DEFAULT_DEPARTEMENT_KAOLACK)
            .departementKedougou(DEFAULT_DEPARTEMENT_KEDOUGOU)
            .departementKolda(DEFAULT_DEPARTEMENT_KOLDA)
            .departementLouga(DEFAULT_DEPARTEMENT_LOUGA)
            .departementMatam(DEFAULT_DEPARTEMENT_MATAM)
            .departementSaint(DEFAULT_DEPARTEMENT_SAINT)
            .departementSedhiou(DEFAULT_DEPARTEMENT_SEDHIOU)
            .departementTambacounda(DEFAULT_DEPARTEMENT_TAMBACOUNDA)
            .departementThis(DEFAULT_DEPARTEMENT_THIS)
            .departementZiguinchor(DEFAULT_DEPARTEMENT_ZIGUINCHOR)
            .autredepartementDakar(DEFAULT_AUTREDEPARTEMENT_DAKAR)
            .autredepartementDiourbel(DEFAULT_AUTREDEPARTEMENT_DIOURBEL)
            .autredepartementFatick(DEFAULT_AUTREDEPARTEMENT_FATICK)
            .autredepartementKaffrine(DEFAULT_AUTREDEPARTEMENT_KAFFRINE)
            .autredepartementKaolack(DEFAULT_AUTREDEPARTEMENT_KAOLACK)
            .autredepartementKedougou(DEFAULT_AUTREDEPARTEMENT_KEDOUGOU)
            .autredepartementKolda(DEFAULT_AUTREDEPARTEMENT_KOLDA)
            .autredepartementLouga(DEFAULT_AUTREDEPARTEMENT_LOUGA)
            .autredepartementMatam(DEFAULT_AUTREDEPARTEMENT_MATAM)
            .autredepartementSaint(DEFAULT_AUTREDEPARTEMENT_SAINT)
            .autredepartementSedhiou(DEFAULT_AUTREDEPARTEMENT_SEDHIOU)
            .autredepartementTambacounda(DEFAULT_AUTREDEPARTEMENT_TAMBACOUNDA)
            .autredepartementThis(DEFAULT_AUTREDEPARTEMENT_THIS)
            .autredepartementZiguinchor(DEFAULT_AUTREDEPARTEMENT_ZIGUINCHOR)
            .commune(DEFAULT_COMMUNE)
            .nomQuartier(DEFAULT_NOM_QUARTIER);
        return localite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localite createUpdatedEntity(EntityManager em) {
        Localite localite = new Localite()
            .region(UPDATED_REGION)
            .autreRegion(UPDATED_AUTRE_REGION)
            .departementDakar(UPDATED_DEPARTEMENT_DAKAR)
            .departementDiourbel(UPDATED_DEPARTEMENT_DIOURBEL)
            .departementFatick(UPDATED_DEPARTEMENT_FATICK)
            .departementKaffrine(UPDATED_DEPARTEMENT_KAFFRINE)
            .departementKaolack(UPDATED_DEPARTEMENT_KAOLACK)
            .departementKedougou(UPDATED_DEPARTEMENT_KEDOUGOU)
            .departementKolda(UPDATED_DEPARTEMENT_KOLDA)
            .departementLouga(UPDATED_DEPARTEMENT_LOUGA)
            .departementMatam(UPDATED_DEPARTEMENT_MATAM)
            .departementSaint(UPDATED_DEPARTEMENT_SAINT)
            .departementSedhiou(UPDATED_DEPARTEMENT_SEDHIOU)
            .departementTambacounda(UPDATED_DEPARTEMENT_TAMBACOUNDA)
            .departementThis(UPDATED_DEPARTEMENT_THIS)
            .departementZiguinchor(UPDATED_DEPARTEMENT_ZIGUINCHOR)
            .autredepartementDakar(UPDATED_AUTREDEPARTEMENT_DAKAR)
            .autredepartementDiourbel(UPDATED_AUTREDEPARTEMENT_DIOURBEL)
            .autredepartementFatick(UPDATED_AUTREDEPARTEMENT_FATICK)
            .autredepartementKaffrine(UPDATED_AUTREDEPARTEMENT_KAFFRINE)
            .autredepartementKaolack(UPDATED_AUTREDEPARTEMENT_KAOLACK)
            .autredepartementKedougou(UPDATED_AUTREDEPARTEMENT_KEDOUGOU)
            .autredepartementKolda(UPDATED_AUTREDEPARTEMENT_KOLDA)
            .autredepartementLouga(UPDATED_AUTREDEPARTEMENT_LOUGA)
            .autredepartementMatam(UPDATED_AUTREDEPARTEMENT_MATAM)
            .autredepartementSaint(UPDATED_AUTREDEPARTEMENT_SAINT)
            .autredepartementSedhiou(UPDATED_AUTREDEPARTEMENT_SEDHIOU)
            .autredepartementTambacounda(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA)
            .autredepartementThis(UPDATED_AUTREDEPARTEMENT_THIS)
            .autredepartementZiguinchor(UPDATED_AUTREDEPARTEMENT_ZIGUINCHOR)
            .commune(UPDATED_COMMUNE)
            .nomQuartier(UPDATED_NOM_QUARTIER);
        return localite;
    }

    @BeforeEach
    public void initTest() {
        localite = createEntity(em);
    }

    @Test
    @Transactional
    void createLocalite() throws Exception {
        int databaseSizeBeforeCreate = localiteRepository.findAll().size();
        // Create the Localite
        restLocaliteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isCreated());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate + 1);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testLocalite.getAutreRegion()).isEqualTo(DEFAULT_AUTRE_REGION);
        assertThat(testLocalite.getDepartementDakar()).isEqualTo(DEFAULT_DEPARTEMENT_DAKAR);
        assertThat(testLocalite.getDepartementDiourbel()).isEqualTo(DEFAULT_DEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getDepartementFatick()).isEqualTo(DEFAULT_DEPARTEMENT_FATICK);
        assertThat(testLocalite.getDepartementKaffrine()).isEqualTo(DEFAULT_DEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getDepartementKaolack()).isEqualTo(DEFAULT_DEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getDepartementKedougou()).isEqualTo(DEFAULT_DEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getDepartementKolda()).isEqualTo(DEFAULT_DEPARTEMENT_KOLDA);
        assertThat(testLocalite.getDepartementLouga()).isEqualTo(DEFAULT_DEPARTEMENT_LOUGA);
        assertThat(testLocalite.getDepartementMatam()).isEqualTo(DEFAULT_DEPARTEMENT_MATAM);
        assertThat(testLocalite.getDepartementSaint()).isEqualTo(DEFAULT_DEPARTEMENT_SAINT);
        assertThat(testLocalite.getDepartementSedhiou()).isEqualTo(DEFAULT_DEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getDepartementTambacounda()).isEqualTo(DEFAULT_DEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getDepartementThis()).isEqualTo(DEFAULT_DEPARTEMENT_THIS);
        assertThat(testLocalite.getDepartementZiguinchor()).isEqualTo(DEFAULT_DEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getAutredepartementDakar()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_DAKAR);
        assertThat(testLocalite.getAutredepartementDiourbel()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getAutredepartementFatick()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_FATICK);
        assertThat(testLocalite.getAutredepartementKaffrine()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getAutredepartementKaolack()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getAutredepartementKedougou()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getAutredepartementKolda()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_KOLDA);
        assertThat(testLocalite.getAutredepartementLouga()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_LOUGA);
        assertThat(testLocalite.getAutredepartementMatam()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_MATAM);
        assertThat(testLocalite.getAutredepartementSaint()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_SAINT);
        assertThat(testLocalite.getAutredepartementSedhiou()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getAutredepartementTambacounda()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getAutredepartementThis()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_THIS);
        assertThat(testLocalite.getAutredepartementZiguinchor()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testLocalite.getNomQuartier()).isEqualTo(DEFAULT_NOM_QUARTIER);
    }

    @Test
    @Transactional
    void createLocaliteWithExistingId() throws Exception {
        // Create the Localite with an existing ID
        localite.setId(1L);

        int databaseSizeBeforeCreate = localiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocaliteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = localiteRepository.findAll().size();
        // set the field null
        localite.setRegion(null);

        // Create the Localite, which fails.

        restLocaliteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = localiteRepository.findAll().size();
        // set the field null
        localite.setCommune(null);

        // Create the Localite, which fails.

        restLocaliteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocalites() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        // Get all the localiteList
        restLocaliteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localite.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].autreRegion").value(hasItem(DEFAULT_AUTRE_REGION)))
            .andExpect(jsonPath("$.[*].departementDakar").value(hasItem(DEFAULT_DEPARTEMENT_DAKAR.toString())))
            .andExpect(jsonPath("$.[*].departementDiourbel").value(hasItem(DEFAULT_DEPARTEMENT_DIOURBEL.toString())))
            .andExpect(jsonPath("$.[*].departementFatick").value(hasItem(DEFAULT_DEPARTEMENT_FATICK.toString())))
            .andExpect(jsonPath("$.[*].departementKaffrine").value(hasItem(DEFAULT_DEPARTEMENT_KAFFRINE.toString())))
            .andExpect(jsonPath("$.[*].departementKaolack").value(hasItem(DEFAULT_DEPARTEMENT_KAOLACK.toString())))
            .andExpect(jsonPath("$.[*].departementKedougou").value(hasItem(DEFAULT_DEPARTEMENT_KEDOUGOU.toString())))
            .andExpect(jsonPath("$.[*].departementKolda").value(hasItem(DEFAULT_DEPARTEMENT_KOLDA.toString())))
            .andExpect(jsonPath("$.[*].departementLouga").value(hasItem(DEFAULT_DEPARTEMENT_LOUGA.toString())))
            .andExpect(jsonPath("$.[*].departementMatam").value(hasItem(DEFAULT_DEPARTEMENT_MATAM.toString())))
            .andExpect(jsonPath("$.[*].departementSaint").value(hasItem(DEFAULT_DEPARTEMENT_SAINT.toString())))
            .andExpect(jsonPath("$.[*].departementSedhiou").value(hasItem(DEFAULT_DEPARTEMENT_SEDHIOU.toString())))
            .andExpect(jsonPath("$.[*].departementTambacounda").value(hasItem(DEFAULT_DEPARTEMENT_TAMBACOUNDA.toString())))
            .andExpect(jsonPath("$.[*].departementThis").value(hasItem(DEFAULT_DEPARTEMENT_THIS.toString())))
            .andExpect(jsonPath("$.[*].departementZiguinchor").value(hasItem(DEFAULT_DEPARTEMENT_ZIGUINCHOR.toString())))
            .andExpect(jsonPath("$.[*].autredepartementDakar").value(hasItem(DEFAULT_AUTREDEPARTEMENT_DAKAR)))
            .andExpect(jsonPath("$.[*].autredepartementDiourbel").value(hasItem(DEFAULT_AUTREDEPARTEMENT_DIOURBEL)))
            .andExpect(jsonPath("$.[*].autredepartementFatick").value(hasItem(DEFAULT_AUTREDEPARTEMENT_FATICK)))
            .andExpect(jsonPath("$.[*].autredepartementKaffrine").value(hasItem(DEFAULT_AUTREDEPARTEMENT_KAFFRINE)))
            .andExpect(jsonPath("$.[*].autredepartementKaolack").value(hasItem(DEFAULT_AUTREDEPARTEMENT_KAOLACK)))
            .andExpect(jsonPath("$.[*].autredepartementKedougou").value(hasItem(DEFAULT_AUTREDEPARTEMENT_KEDOUGOU)))
            .andExpect(jsonPath("$.[*].autredepartementKolda").value(hasItem(DEFAULT_AUTREDEPARTEMENT_KOLDA)))
            .andExpect(jsonPath("$.[*].autredepartementLouga").value(hasItem(DEFAULT_AUTREDEPARTEMENT_LOUGA)))
            .andExpect(jsonPath("$.[*].autredepartementMatam").value(hasItem(DEFAULT_AUTREDEPARTEMENT_MATAM)))
            .andExpect(jsonPath("$.[*].autredepartementSaint").value(hasItem(DEFAULT_AUTREDEPARTEMENT_SAINT)))
            .andExpect(jsonPath("$.[*].autredepartementSedhiou").value(hasItem(DEFAULT_AUTREDEPARTEMENT_SEDHIOU)))
            .andExpect(jsonPath("$.[*].autredepartementTambacounda").value(hasItem(DEFAULT_AUTREDEPARTEMENT_TAMBACOUNDA)))
            .andExpect(jsonPath("$.[*].autredepartementThis").value(hasItem(DEFAULT_AUTREDEPARTEMENT_THIS)))
            .andExpect(jsonPath("$.[*].autredepartementZiguinchor").value(hasItem(DEFAULT_AUTREDEPARTEMENT_ZIGUINCHOR)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].nomQuartier").value(hasItem(DEFAULT_NOM_QUARTIER)));
    }

    @Test
    @Transactional
    void getLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        // Get the localite
        restLocaliteMockMvc
            .perform(get(ENTITY_API_URL_ID, localite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localite.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.autreRegion").value(DEFAULT_AUTRE_REGION))
            .andExpect(jsonPath("$.departementDakar").value(DEFAULT_DEPARTEMENT_DAKAR.toString()))
            .andExpect(jsonPath("$.departementDiourbel").value(DEFAULT_DEPARTEMENT_DIOURBEL.toString()))
            .andExpect(jsonPath("$.departementFatick").value(DEFAULT_DEPARTEMENT_FATICK.toString()))
            .andExpect(jsonPath("$.departementKaffrine").value(DEFAULT_DEPARTEMENT_KAFFRINE.toString()))
            .andExpect(jsonPath("$.departementKaolack").value(DEFAULT_DEPARTEMENT_KAOLACK.toString()))
            .andExpect(jsonPath("$.departementKedougou").value(DEFAULT_DEPARTEMENT_KEDOUGOU.toString()))
            .andExpect(jsonPath("$.departementKolda").value(DEFAULT_DEPARTEMENT_KOLDA.toString()))
            .andExpect(jsonPath("$.departementLouga").value(DEFAULT_DEPARTEMENT_LOUGA.toString()))
            .andExpect(jsonPath("$.departementMatam").value(DEFAULT_DEPARTEMENT_MATAM.toString()))
            .andExpect(jsonPath("$.departementSaint").value(DEFAULT_DEPARTEMENT_SAINT.toString()))
            .andExpect(jsonPath("$.departementSedhiou").value(DEFAULT_DEPARTEMENT_SEDHIOU.toString()))
            .andExpect(jsonPath("$.departementTambacounda").value(DEFAULT_DEPARTEMENT_TAMBACOUNDA.toString()))
            .andExpect(jsonPath("$.departementThis").value(DEFAULT_DEPARTEMENT_THIS.toString()))
            .andExpect(jsonPath("$.departementZiguinchor").value(DEFAULT_DEPARTEMENT_ZIGUINCHOR.toString()))
            .andExpect(jsonPath("$.autredepartementDakar").value(DEFAULT_AUTREDEPARTEMENT_DAKAR))
            .andExpect(jsonPath("$.autredepartementDiourbel").value(DEFAULT_AUTREDEPARTEMENT_DIOURBEL))
            .andExpect(jsonPath("$.autredepartementFatick").value(DEFAULT_AUTREDEPARTEMENT_FATICK))
            .andExpect(jsonPath("$.autredepartementKaffrine").value(DEFAULT_AUTREDEPARTEMENT_KAFFRINE))
            .andExpect(jsonPath("$.autredepartementKaolack").value(DEFAULT_AUTREDEPARTEMENT_KAOLACK))
            .andExpect(jsonPath("$.autredepartementKedougou").value(DEFAULT_AUTREDEPARTEMENT_KEDOUGOU))
            .andExpect(jsonPath("$.autredepartementKolda").value(DEFAULT_AUTREDEPARTEMENT_KOLDA))
            .andExpect(jsonPath("$.autredepartementLouga").value(DEFAULT_AUTREDEPARTEMENT_LOUGA))
            .andExpect(jsonPath("$.autredepartementMatam").value(DEFAULT_AUTREDEPARTEMENT_MATAM))
            .andExpect(jsonPath("$.autredepartementSaint").value(DEFAULT_AUTREDEPARTEMENT_SAINT))
            .andExpect(jsonPath("$.autredepartementSedhiou").value(DEFAULT_AUTREDEPARTEMENT_SEDHIOU))
            .andExpect(jsonPath("$.autredepartementTambacounda").value(DEFAULT_AUTREDEPARTEMENT_TAMBACOUNDA))
            .andExpect(jsonPath("$.autredepartementThis").value(DEFAULT_AUTREDEPARTEMENT_THIS))
            .andExpect(jsonPath("$.autredepartementZiguinchor").value(DEFAULT_AUTREDEPARTEMENT_ZIGUINCHOR))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE))
            .andExpect(jsonPath("$.nomQuartier").value(DEFAULT_NOM_QUARTIER));
    }

    @Test
    @Transactional
    void getNonExistingLocalite() throws Exception {
        // Get the localite
        restLocaliteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Update the localite
        Localite updatedLocalite = localiteRepository.findById(localite.getId()).get();
        // Disconnect from session so that the updates on updatedLocalite are not directly saved in db
        em.detach(updatedLocalite);
        updatedLocalite
            .region(UPDATED_REGION)
            .autreRegion(UPDATED_AUTRE_REGION)
            .departementDakar(UPDATED_DEPARTEMENT_DAKAR)
            .departementDiourbel(UPDATED_DEPARTEMENT_DIOURBEL)
            .departementFatick(UPDATED_DEPARTEMENT_FATICK)
            .departementKaffrine(UPDATED_DEPARTEMENT_KAFFRINE)
            .departementKaolack(UPDATED_DEPARTEMENT_KAOLACK)
            .departementKedougou(UPDATED_DEPARTEMENT_KEDOUGOU)
            .departementKolda(UPDATED_DEPARTEMENT_KOLDA)
            .departementLouga(UPDATED_DEPARTEMENT_LOUGA)
            .departementMatam(UPDATED_DEPARTEMENT_MATAM)
            .departementSaint(UPDATED_DEPARTEMENT_SAINT)
            .departementSedhiou(UPDATED_DEPARTEMENT_SEDHIOU)
            .departementTambacounda(UPDATED_DEPARTEMENT_TAMBACOUNDA)
            .departementThis(UPDATED_DEPARTEMENT_THIS)
            .departementZiguinchor(UPDATED_DEPARTEMENT_ZIGUINCHOR)
            .autredepartementDakar(UPDATED_AUTREDEPARTEMENT_DAKAR)
            .autredepartementDiourbel(UPDATED_AUTREDEPARTEMENT_DIOURBEL)
            .autredepartementFatick(UPDATED_AUTREDEPARTEMENT_FATICK)
            .autredepartementKaffrine(UPDATED_AUTREDEPARTEMENT_KAFFRINE)
            .autredepartementKaolack(UPDATED_AUTREDEPARTEMENT_KAOLACK)
            .autredepartementKedougou(UPDATED_AUTREDEPARTEMENT_KEDOUGOU)
            .autredepartementKolda(UPDATED_AUTREDEPARTEMENT_KOLDA)
            .autredepartementLouga(UPDATED_AUTREDEPARTEMENT_LOUGA)
            .autredepartementMatam(UPDATED_AUTREDEPARTEMENT_MATAM)
            .autredepartementSaint(UPDATED_AUTREDEPARTEMENT_SAINT)
            .autredepartementSedhiou(UPDATED_AUTREDEPARTEMENT_SEDHIOU)
            .autredepartementTambacounda(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA)
            .autredepartementThis(UPDATED_AUTREDEPARTEMENT_THIS)
            .autredepartementZiguinchor(UPDATED_AUTREDEPARTEMENT_ZIGUINCHOR)
            .commune(UPDATED_COMMUNE)
            .nomQuartier(UPDATED_NOM_QUARTIER);

        restLocaliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocalite.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLocalite))
            )
            .andExpect(status().isOk());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testLocalite.getAutreRegion()).isEqualTo(UPDATED_AUTRE_REGION);
        assertThat(testLocalite.getDepartementDakar()).isEqualTo(UPDATED_DEPARTEMENT_DAKAR);
        assertThat(testLocalite.getDepartementDiourbel()).isEqualTo(UPDATED_DEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getDepartementFatick()).isEqualTo(UPDATED_DEPARTEMENT_FATICK);
        assertThat(testLocalite.getDepartementKaffrine()).isEqualTo(UPDATED_DEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getDepartementKaolack()).isEqualTo(UPDATED_DEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getDepartementKedougou()).isEqualTo(UPDATED_DEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getDepartementKolda()).isEqualTo(UPDATED_DEPARTEMENT_KOLDA);
        assertThat(testLocalite.getDepartementLouga()).isEqualTo(UPDATED_DEPARTEMENT_LOUGA);
        assertThat(testLocalite.getDepartementMatam()).isEqualTo(UPDATED_DEPARTEMENT_MATAM);
        assertThat(testLocalite.getDepartementSaint()).isEqualTo(UPDATED_DEPARTEMENT_SAINT);
        assertThat(testLocalite.getDepartementSedhiou()).isEqualTo(UPDATED_DEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getDepartementTambacounda()).isEqualTo(UPDATED_DEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getDepartementThis()).isEqualTo(UPDATED_DEPARTEMENT_THIS);
        assertThat(testLocalite.getDepartementZiguinchor()).isEqualTo(UPDATED_DEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getAutredepartementDakar()).isEqualTo(UPDATED_AUTREDEPARTEMENT_DAKAR);
        assertThat(testLocalite.getAutredepartementDiourbel()).isEqualTo(UPDATED_AUTREDEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getAutredepartementFatick()).isEqualTo(UPDATED_AUTREDEPARTEMENT_FATICK);
        assertThat(testLocalite.getAutredepartementKaffrine()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getAutredepartementKaolack()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getAutredepartementKedougou()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getAutredepartementKolda()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KOLDA);
        assertThat(testLocalite.getAutredepartementLouga()).isEqualTo(UPDATED_AUTREDEPARTEMENT_LOUGA);
        assertThat(testLocalite.getAutredepartementMatam()).isEqualTo(UPDATED_AUTREDEPARTEMENT_MATAM);
        assertThat(testLocalite.getAutredepartementSaint()).isEqualTo(UPDATED_AUTREDEPARTEMENT_SAINT);
        assertThat(testLocalite.getAutredepartementSedhiou()).isEqualTo(UPDATED_AUTREDEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getAutredepartementTambacounda()).isEqualTo(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getAutredepartementThis()).isEqualTo(UPDATED_AUTREDEPARTEMENT_THIS);
        assertThat(testLocalite.getAutredepartementZiguinchor()).isEqualTo(UPDATED_AUTREDEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testLocalite.getNomQuartier()).isEqualTo(UPDATED_NOM_QUARTIER);
    }

    @Test
    @Transactional
    void putNonExistingLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();
        localite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, localite.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();
        localite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();
        localite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaliteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocaliteWithPatch() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Update the localite using partial update
        Localite partialUpdatedLocalite = new Localite();
        partialUpdatedLocalite.setId(localite.getId());

        partialUpdatedLocalite
            .region(UPDATED_REGION)
            .autreRegion(UPDATED_AUTRE_REGION)
            .departementKaolack(UPDATED_DEPARTEMENT_KAOLACK)
            .departementLouga(UPDATED_DEPARTEMENT_LOUGA)
            .departementMatam(UPDATED_DEPARTEMENT_MATAM)
            .departementSedhiou(UPDATED_DEPARTEMENT_SEDHIOU)
            .departementZiguinchor(UPDATED_DEPARTEMENT_ZIGUINCHOR)
            .autredepartementFatick(UPDATED_AUTREDEPARTEMENT_FATICK)
            .autredepartementKaolack(UPDATED_AUTREDEPARTEMENT_KAOLACK)
            .autredepartementKolda(UPDATED_AUTREDEPARTEMENT_KOLDA)
            .autredepartementMatam(UPDATED_AUTREDEPARTEMENT_MATAM)
            .autredepartementSedhiou(UPDATED_AUTREDEPARTEMENT_SEDHIOU)
            .autredepartementTambacounda(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA);

        restLocaliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalite))
            )
            .andExpect(status().isOk());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testLocalite.getAutreRegion()).isEqualTo(UPDATED_AUTRE_REGION);
        assertThat(testLocalite.getDepartementDakar()).isEqualTo(DEFAULT_DEPARTEMENT_DAKAR);
        assertThat(testLocalite.getDepartementDiourbel()).isEqualTo(DEFAULT_DEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getDepartementFatick()).isEqualTo(DEFAULT_DEPARTEMENT_FATICK);
        assertThat(testLocalite.getDepartementKaffrine()).isEqualTo(DEFAULT_DEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getDepartementKaolack()).isEqualTo(UPDATED_DEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getDepartementKedougou()).isEqualTo(DEFAULT_DEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getDepartementKolda()).isEqualTo(DEFAULT_DEPARTEMENT_KOLDA);
        assertThat(testLocalite.getDepartementLouga()).isEqualTo(UPDATED_DEPARTEMENT_LOUGA);
        assertThat(testLocalite.getDepartementMatam()).isEqualTo(UPDATED_DEPARTEMENT_MATAM);
        assertThat(testLocalite.getDepartementSaint()).isEqualTo(DEFAULT_DEPARTEMENT_SAINT);
        assertThat(testLocalite.getDepartementSedhiou()).isEqualTo(UPDATED_DEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getDepartementTambacounda()).isEqualTo(DEFAULT_DEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getDepartementThis()).isEqualTo(DEFAULT_DEPARTEMENT_THIS);
        assertThat(testLocalite.getDepartementZiguinchor()).isEqualTo(UPDATED_DEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getAutredepartementDakar()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_DAKAR);
        assertThat(testLocalite.getAutredepartementDiourbel()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getAutredepartementFatick()).isEqualTo(UPDATED_AUTREDEPARTEMENT_FATICK);
        assertThat(testLocalite.getAutredepartementKaffrine()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getAutredepartementKaolack()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getAutredepartementKedougou()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getAutredepartementKolda()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KOLDA);
        assertThat(testLocalite.getAutredepartementLouga()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_LOUGA);
        assertThat(testLocalite.getAutredepartementMatam()).isEqualTo(UPDATED_AUTREDEPARTEMENT_MATAM);
        assertThat(testLocalite.getAutredepartementSaint()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_SAINT);
        assertThat(testLocalite.getAutredepartementSedhiou()).isEqualTo(UPDATED_AUTREDEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getAutredepartementTambacounda()).isEqualTo(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getAutredepartementThis()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_THIS);
        assertThat(testLocalite.getAutredepartementZiguinchor()).isEqualTo(DEFAULT_AUTREDEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testLocalite.getNomQuartier()).isEqualTo(DEFAULT_NOM_QUARTIER);
    }

    @Test
    @Transactional
    void fullUpdateLocaliteWithPatch() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();

        // Update the localite using partial update
        Localite partialUpdatedLocalite = new Localite();
        partialUpdatedLocalite.setId(localite.getId());

        partialUpdatedLocalite
            .region(UPDATED_REGION)
            .autreRegion(UPDATED_AUTRE_REGION)
            .departementDakar(UPDATED_DEPARTEMENT_DAKAR)
            .departementDiourbel(UPDATED_DEPARTEMENT_DIOURBEL)
            .departementFatick(UPDATED_DEPARTEMENT_FATICK)
            .departementKaffrine(UPDATED_DEPARTEMENT_KAFFRINE)
            .departementKaolack(UPDATED_DEPARTEMENT_KAOLACK)
            .departementKedougou(UPDATED_DEPARTEMENT_KEDOUGOU)
            .departementKolda(UPDATED_DEPARTEMENT_KOLDA)
            .departementLouga(UPDATED_DEPARTEMENT_LOUGA)
            .departementMatam(UPDATED_DEPARTEMENT_MATAM)
            .departementSaint(UPDATED_DEPARTEMENT_SAINT)
            .departementSedhiou(UPDATED_DEPARTEMENT_SEDHIOU)
            .departementTambacounda(UPDATED_DEPARTEMENT_TAMBACOUNDA)
            .departementThis(UPDATED_DEPARTEMENT_THIS)
            .departementZiguinchor(UPDATED_DEPARTEMENT_ZIGUINCHOR)
            .autredepartementDakar(UPDATED_AUTREDEPARTEMENT_DAKAR)
            .autredepartementDiourbel(UPDATED_AUTREDEPARTEMENT_DIOURBEL)
            .autredepartementFatick(UPDATED_AUTREDEPARTEMENT_FATICK)
            .autredepartementKaffrine(UPDATED_AUTREDEPARTEMENT_KAFFRINE)
            .autredepartementKaolack(UPDATED_AUTREDEPARTEMENT_KAOLACK)
            .autredepartementKedougou(UPDATED_AUTREDEPARTEMENT_KEDOUGOU)
            .autredepartementKolda(UPDATED_AUTREDEPARTEMENT_KOLDA)
            .autredepartementLouga(UPDATED_AUTREDEPARTEMENT_LOUGA)
            .autredepartementMatam(UPDATED_AUTREDEPARTEMENT_MATAM)
            .autredepartementSaint(UPDATED_AUTREDEPARTEMENT_SAINT)
            .autredepartementSedhiou(UPDATED_AUTREDEPARTEMENT_SEDHIOU)
            .autredepartementTambacounda(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA)
            .autredepartementThis(UPDATED_AUTREDEPARTEMENT_THIS)
            .autredepartementZiguinchor(UPDATED_AUTREDEPARTEMENT_ZIGUINCHOR)
            .commune(UPDATED_COMMUNE)
            .nomQuartier(UPDATED_NOM_QUARTIER);

        restLocaliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocalite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocalite))
            )
            .andExpect(status().isOk());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
        Localite testLocalite = localiteList.get(localiteList.size() - 1);
        assertThat(testLocalite.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testLocalite.getAutreRegion()).isEqualTo(UPDATED_AUTRE_REGION);
        assertThat(testLocalite.getDepartementDakar()).isEqualTo(UPDATED_DEPARTEMENT_DAKAR);
        assertThat(testLocalite.getDepartementDiourbel()).isEqualTo(UPDATED_DEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getDepartementFatick()).isEqualTo(UPDATED_DEPARTEMENT_FATICK);
        assertThat(testLocalite.getDepartementKaffrine()).isEqualTo(UPDATED_DEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getDepartementKaolack()).isEqualTo(UPDATED_DEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getDepartementKedougou()).isEqualTo(UPDATED_DEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getDepartementKolda()).isEqualTo(UPDATED_DEPARTEMENT_KOLDA);
        assertThat(testLocalite.getDepartementLouga()).isEqualTo(UPDATED_DEPARTEMENT_LOUGA);
        assertThat(testLocalite.getDepartementMatam()).isEqualTo(UPDATED_DEPARTEMENT_MATAM);
        assertThat(testLocalite.getDepartementSaint()).isEqualTo(UPDATED_DEPARTEMENT_SAINT);
        assertThat(testLocalite.getDepartementSedhiou()).isEqualTo(UPDATED_DEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getDepartementTambacounda()).isEqualTo(UPDATED_DEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getDepartementThis()).isEqualTo(UPDATED_DEPARTEMENT_THIS);
        assertThat(testLocalite.getDepartementZiguinchor()).isEqualTo(UPDATED_DEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getAutredepartementDakar()).isEqualTo(UPDATED_AUTREDEPARTEMENT_DAKAR);
        assertThat(testLocalite.getAutredepartementDiourbel()).isEqualTo(UPDATED_AUTREDEPARTEMENT_DIOURBEL);
        assertThat(testLocalite.getAutredepartementFatick()).isEqualTo(UPDATED_AUTREDEPARTEMENT_FATICK);
        assertThat(testLocalite.getAutredepartementKaffrine()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KAFFRINE);
        assertThat(testLocalite.getAutredepartementKaolack()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KAOLACK);
        assertThat(testLocalite.getAutredepartementKedougou()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KEDOUGOU);
        assertThat(testLocalite.getAutredepartementKolda()).isEqualTo(UPDATED_AUTREDEPARTEMENT_KOLDA);
        assertThat(testLocalite.getAutredepartementLouga()).isEqualTo(UPDATED_AUTREDEPARTEMENT_LOUGA);
        assertThat(testLocalite.getAutredepartementMatam()).isEqualTo(UPDATED_AUTREDEPARTEMENT_MATAM);
        assertThat(testLocalite.getAutredepartementSaint()).isEqualTo(UPDATED_AUTREDEPARTEMENT_SAINT);
        assertThat(testLocalite.getAutredepartementSedhiou()).isEqualTo(UPDATED_AUTREDEPARTEMENT_SEDHIOU);
        assertThat(testLocalite.getAutredepartementTambacounda()).isEqualTo(UPDATED_AUTREDEPARTEMENT_TAMBACOUNDA);
        assertThat(testLocalite.getAutredepartementThis()).isEqualTo(UPDATED_AUTREDEPARTEMENT_THIS);
        assertThat(testLocalite.getAutredepartementZiguinchor()).isEqualTo(UPDATED_AUTREDEPARTEMENT_ZIGUINCHOR);
        assertThat(testLocalite.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testLocalite.getNomQuartier()).isEqualTo(UPDATED_NOM_QUARTIER);
    }

    @Test
    @Transactional
    void patchNonExistingLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();
        localite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocaliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, localite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();
        localite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocalite() throws Exception {
        int databaseSizeBeforeUpdate = localiteRepository.findAll().size();
        localite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocaliteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(localite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Localite in the database
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocalite() throws Exception {
        // Initialize the database
        localiteRepository.saveAndFlush(localite);

        int databaseSizeBeforeDelete = localiteRepository.findAll().size();

        // Delete the localite
        restLocaliteMockMvc
            .perform(delete(ENTITY_API_URL_ID, localite.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localite> localiteList = localiteRepository.findAll();
        assertThat(localiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
