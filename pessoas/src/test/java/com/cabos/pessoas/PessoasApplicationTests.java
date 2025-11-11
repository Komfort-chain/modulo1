package com.cabos.pessoas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PessoasApplicationTests {

    @Configuration
    @EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
    })
    static class NoDatabaseConfig {
        // Remove toda a configuração automática de banco de dados.
    }

    @Test
    void contextLoads() {
        // Teste básico — apenas garante que o contexto Spring carrega corretamente.
    }
}
