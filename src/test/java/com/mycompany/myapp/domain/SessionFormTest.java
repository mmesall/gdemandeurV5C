package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SessionFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionForm.class);
        SessionForm sessionForm1 = new SessionForm();
        sessionForm1.setId(1L);
        SessionForm sessionForm2 = new SessionForm();
        sessionForm2.setId(sessionForm1.getId());
        assertThat(sessionForm1).isEqualTo(sessionForm2);
        sessionForm2.setId(2L);
        assertThat(sessionForm1).isNotEqualTo(sessionForm2);
        sessionForm1.setId(null);
        assertThat(sessionForm1).isNotEqualTo(sessionForm2);
    }
}
