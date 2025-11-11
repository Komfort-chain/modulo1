package com.cabos.pessoas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = PessoasApplication.class)
@ActiveProfiles("test")
class PessoasApplicationTests {

    @Test
    void contextLoads() {
        // Teste de inicialização básica da aplicação (sem DB real)
    }
}
