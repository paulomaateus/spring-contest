package com.PauloMoreira.contest.util;

/**
 * Classe utilitária para validação de dados.
 *
 * Esta classe contém métodos estáticos para realizar validações comuns, como
 * verificar a validade de um CPF e de um número de processo judicial. É uma
 * classe utilitária que fornece funções de validação usadas em várias partes da
 * aplicação.
 */
public class ValidationUtils {

    /**
     * Verifica se o CPF fornecido é válido.
     *
     * Um CPF é considerado válido se não for nulo e contiver exatamente 11
     * dígitos numéricos após a remoção de caracteres não numéricos. A função
     * remove caracteres não numéricos e verifica o comprimento do CPF.
     *
     * @param cpf O CPF a ser validado. Pode conter caracteres não numéricos
     * como pontos e traços.
     * @return {@code true} se o CPF for válido, ou {@code false} caso
     * contrário.
     */
    public static boolean isValidCpf(String cpf) {
        if (cpf == null) {
            return false;
        }
        cpf = cpf.replaceAll("[^\\d]", "");
        return cpf.length() == 11;
    }

    /**
     * Verifica se o número do processo judicial fornecido é válido.
     *
     * Um número de processo é considerado válido se não for nulo e não for uma
     * string vazia ou apenas espaços em branco. O método realiza uma
     * verificação básica para garantir que o número do processo tenha um valor
     * apropriado.
     *
     * @param numero O número do processo a ser validado. Deve ser uma string
     * não nula e não vazia.
     * @return {@code true} se o número do processo for válido, ou {@code false}
     * caso contrário.
     */
    public static boolean isValidNumeroProcesso(String numero) {
        return numero != null && !numero.trim().isEmpty();
    }
}
