package com.PauloMoreira.contest.exception;

/**
 * Exceção lançada quando há uma tentativa de criar ou atualizar um processo
 * judicial com um número que já está presente no sistema.
 *
 * Esta exceção é utilizada para indicar que o número do processo é duplicado,
 * ou seja, já existe um processo com o mesmo número registrado na base de
 * dados.
 */
public class DuplicateNumeroProcessoException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com uma mensagem específica.
     *
     * @param message A mensagem detalhando o motivo da exceção.
     */
    public DuplicateNumeroProcessoException(String message) {
        super(message);
    }
}
