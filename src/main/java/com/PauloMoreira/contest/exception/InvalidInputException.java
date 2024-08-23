package com.PauloMoreira.contest.exception;

/**
 * Exceção lançada quando a entrada fornecida é inválida.
 *
 * Esta classe estende {@link RuntimeException} e é usada para indicar que os
 * dados fornecidos para a aplicação não são válidos, como, por exemplo,
 * entradas com formato incorreto ou valores fora do esperado.
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Cria uma nova instância de {@link InvalidInputException} com uma mensagem
     * de erro específica.
     *
     * @param message A mensagem de erro que descreve o motivo da exceção.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
