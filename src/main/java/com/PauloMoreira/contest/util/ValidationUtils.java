package com.PauloMoreira.contest.util;

public class ValidationUtils {

    public static boolean isValidCpf(String cpf) {
        if (cpf == null) {
            return false;
        }
        cpf = cpf.replaceAll("[^\\d]", "");

        return cpf.length() == 11;
    }

    public static boolean isValidNumeroProcesso(String numero) {
        return numero != null && !numero.trim().isEmpty();
    }
}
