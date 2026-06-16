package br.edu.ifpb.ads.foodjava.util;

public class ValidadorCPF {

    public static boolean isCPF(String cpf){
        if (cpf == null) return false;

        String CPF = "";
        int som10digito = 0;
        int som11digito = 0;

        for(int i = 0; i < cpf.length(); i++){
            if(Character.isDigit(cpf.charAt(i))){
                CPF += cpf.charAt(i);
            }
        }

        if( CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ){
            return false;
        }

        if(CPF.length() != 11){
            return false;
        }

        try {
            // Vaidando o primeiro dígito ->
            for(int i = 0, peso = 10; i <= 8; i++, peso--){
                som10digito += (Character.getNumericValue(CPF.charAt(i)) * peso);
            }

            int restoPrimeiroDigito = som10digito % 11;
            int dig10Esperado = (restoPrimeiroDigito < 2) ? 0 : (11 - restoPrimeiroDigito);

            if (dig10Esperado != Character.getNumericValue(CPF.charAt(9))) {
                return false;
            }

            // Validando o segundo dígito ->
            for(int i = 0, peso = 11; i <= 9; i++, peso--){
                som11digito += (Character.getNumericValue(CPF.charAt(i)) * peso);
            }

            int restoSegundoDigito = som11digito % 11;
            int dig11Esperado = (restoSegundoDigito < 2) ? 0 : (11 - restoSegundoDigito);

            if (dig11Esperado != Character.getNumericValue(CPF.charAt(10))) {
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}