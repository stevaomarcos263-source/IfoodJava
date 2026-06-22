package br.edu.ifpb.ads.foodjava.util;

public class ValidadorTelefone{

    public static boolean isTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            return false;
        }
        telefone = telefone.replaceAll("\\D", "");

        return telefone.length() == 10 || telefone.length() == 11;
    }
}
