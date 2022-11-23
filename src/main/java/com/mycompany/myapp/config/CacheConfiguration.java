package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Localite.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Localite.class.getName() + ".etablissements");
            createCache(cm, com.mycompany.myapp.domain.Localite.class.getName() + ".eleves");
            createCache(cm, com.mycompany.myapp.domain.Localite.class.getName() + ".etudiants");
            createCache(cm, com.mycompany.myapp.domain.Localite.class.getName() + ".professionnels");
            createCache(cm, com.mycompany.myapp.domain.Localite.class.getName() + ".agents");
            createCache(cm, com.mycompany.myapp.domain.Demande.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Demande.class.getName() + ".pieceJointes");
            createCache(cm, com.mycompany.myapp.domain.Dossier.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Dossier.class.getName() + ".demandes");
            createCache(cm, com.mycompany.myapp.domain.PieceJointe.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Services.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Services.class.getName() + ".agents");
            createCache(cm, com.mycompany.myapp.domain.Commission.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Commission.class.getName() + ".membres");
            createCache(cm, com.mycompany.myapp.domain.Membre.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Agent.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Bailleur.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Bailleur.class.getName() + ".priseEnCharges");
            createCache(cm, com.mycompany.myapp.domain.Demandeur.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Eleve.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Etudiant.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Professionnel.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Concours.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PriseEnCharge.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SessionForm.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Formation.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Formation.class.getName() + ".priseEnCharges");
            createCache(cm, com.mycompany.myapp.domain.Formation.class.getName() + ".concours");
            createCache(cm, com.mycompany.myapp.domain.Formation.class.getName() + ".sessionForms");
            createCache(cm, com.mycompany.myapp.domain.Etablissement.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Etablissement.class.getName() + ".filieres");
            createCache(cm, com.mycompany.myapp.domain.Etablissement.class.getName() + ".series");
            createCache(cm, com.mycompany.myapp.domain.Etablissement.class.getName() + ".sessionForms");
            createCache(cm, com.mycompany.myapp.domain.Filiere.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Serie.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
