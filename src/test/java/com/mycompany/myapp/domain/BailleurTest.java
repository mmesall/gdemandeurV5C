package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BailleurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bailleur.class);
        Bailleur bailleur1 = new Bailleur();
        bailleur1.setId(1L);
        Bailleur bailleur2 = new Bailleur();
        bailleur2.setId(bailleur1.getId());
        assertThat(bailleur1).isEqualTo(bailleur2);
        bailleur2.setId(2L);
        assertThat(bailleur1).isNotEqualTo(bailleur2);
        bailleur1.setId(null);
        assertThat(bailleur1).isNotEqualTo(bailleur2);
    }
}
