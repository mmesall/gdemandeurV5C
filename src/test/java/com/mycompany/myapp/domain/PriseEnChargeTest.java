package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriseEnChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriseEnCharge.class);
        PriseEnCharge priseEnCharge1 = new PriseEnCharge();
        priseEnCharge1.setId(1L);
        PriseEnCharge priseEnCharge2 = new PriseEnCharge();
        priseEnCharge2.setId(priseEnCharge1.getId());
        assertThat(priseEnCharge1).isEqualTo(priseEnCharge2);
        priseEnCharge2.setId(2L);
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
        priseEnCharge1.setId(null);
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
    }
}
