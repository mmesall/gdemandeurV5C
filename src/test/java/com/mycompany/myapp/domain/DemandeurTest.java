package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demandeur.class);
        Demandeur demandeur1 = new Demandeur();
        demandeur1.setId(1L);
        Demandeur demandeur2 = new Demandeur();
        demandeur2.setId(demandeur1.getId());
        assertThat(demandeur1).isEqualTo(demandeur2);
        demandeur2.setId(2L);
        assertThat(demandeur1).isNotEqualTo(demandeur2);
        demandeur1.setId(null);
        assertThat(demandeur1).isNotEqualTo(demandeur2);
    }
}
