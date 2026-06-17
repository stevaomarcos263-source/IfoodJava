package br.edu.ifpb.ads.foodjava.util;

public class ValidadorCNPJ {

    public static boolean isCNPJ(String cnpj){

        String CNPJ = "";
        int somaValidacaoUm = 0;
        int somaValidacaoDois = 0;

        // proteção de borda ->
        if(cnpj==null || cnpj.isBlank() || cnpj.isEmpty()){
            return false;
        }
        // <- proteção de borda;

        try {
            // Limpando o cnpj e gerando um novo ->
            for (int i = 0; i < cnpj.length(); i++) {
                if (Character.isDigit(cnpj.charAt(i))) {
                    CNPJ += cnpj.charAt(i);
                }
            }
            if(CNPJ.length()!=14){
                return false;
            }
            // Validando primerio dígito ->
            for (int i = 0, regra = 5; i < 12; i++, regra--) {
                if (regra == 1) {
                    regra = 9;
                }
                somaValidacaoUm += Character.getNumericValue(CNPJ.charAt(i)) * regra;
            }
            int restoValidacaoUm = somaValidacaoUm % 11;
            int valorEsperadoValidacaoUm = (restoValidacaoUm < 2) ? 0 : 11 - restoValidacaoUm;
            if (valorEsperadoValidacaoUm != Character.getNumericValue(CNPJ.charAt(12))) {
                return false;
            }

            // Validando segundo dígito ->
            for (int i = 0, regra = 6; i < 13; i++, regra--) {
                if (regra == 1) {
                    regra = 9;
                }
                somaValidacaoDois += Character.getNumericValue(CNPJ.charAt(i)) * regra;
            }
            int restoValidacaoDois = somaValidacaoDois % 11;
            int valorEsperadoValidacaoDois = (restoValidacaoDois < 2) ? 0 : 11 - restoValidacaoDois;
            if (valorEsperadoValidacaoDois != Character.getNumericValue(CNPJ.charAt(13))) {
                return false;
            }
        }catch(Exception e){
            System.err.println("Erro no método isCNPJ -> "+e.getMessage());
        }
        return true;
    }
}
