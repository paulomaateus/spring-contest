package com.PauloMoreira.contest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de suporte para Web MVC e Spring Data.
 *
 * Esta classe configura o suporte ao Spring Data Web, incluindo a serialização
 * de páginas de resultados via DTOs (Data Transfer Objects). Implementa a
 * interface {@link WebMvcConfigurer}, permitindo ajustes adicionais na
 * configuração do MVC se necessário no futuro.
 *
 * A anotação {@link Configuration} indica que esta classe é uma classe de
 * configuração do Spring, que define beans e configurações para o contexto da
 * aplicação.
 *
 * A anotação {@link EnableSpringDataWebSupport} ativa o suporte ao Spring Data
 * para funcionalidades de paginação e sort em APIs REST. A opção
 * {@code pageSerializationMode} é configurada para
 * {@link EnableSpringDataWebSupport.PageSerializationMode#VIA_DTO}, que permite
 * que as páginas de resultados sejam serializadas usando DTOs, proporcionando
 * uma estrutura JSON estável para os clientes da API.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig implements WebMvcConfigurer {

}
