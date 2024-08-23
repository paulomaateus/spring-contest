package com.PauloMoreira.contest.exception;

/**
 * Exceção lançada quando um processo judicial não é encontrado.
 *
 * Esta classe estende {@link RuntimeException} e é usada para indicar que o
 * processo judicial solicitado não pôde ser encontrado no sistema. Pode ser
 * utilizada em casos onde o número do processo fornecido não existe na base de
 * dados.
 */
public class ProcessoNotFoundException extends RuntimeException {

    /**
     * Cria uma nova instância de {@link ProcessoNotFoundException} com uma
     * mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve o motivo da exceção,
     * indicando que o processo judicial não foi encontrado.
     */
    public ProcessoNotFoundException(String message) {
        super(message);
    }
}
