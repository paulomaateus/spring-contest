package com.PauloMoreira.contest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A classe principal da aplicação Spring Boot.
 *
 * Esta classe é responsável por iniciar a aplicação Spring Boot.
 *
 * A anotação {@code @SpringBootApplication} marca esta classe como uma classe
 * de configuração do Spring Boot e ativa o modo de auto-configuração do Spring,
 * a varredura de componentes e a configuração de propriedades.
 *
 * O método {@code main} é o ponto de entrada da aplicação. Ele utiliza
 * {@code SpringApplication.run} para iniciar a aplicação Spring Boot,
 * carregando o contexto da aplicação e inicializando todos os componentes
 * necessários.
 *
 * <p>
 * Exemplo de execução:</p>
 * <pre>
 * java -jar nome-da-aplicacao.jar
 * </pre>
 *
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
@SpringBootApplication
public class ContestApplication {

    /**
     * O ponto de entrada da aplicação Spring Boot.
     *
     * Este método é chamado quando a aplicação é iniciada e é responsável por
     * configurar e iniciar o contexto da aplicação Spring Boot.
     *
     * @param args Argumentos da linha de comando passados para a aplicação.
     */
    public static void main(String[] args) {
        SpringApplication.run(ContestApplication.class, args);
    }
}
