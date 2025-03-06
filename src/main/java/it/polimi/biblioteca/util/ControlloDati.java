package it.polimi.biblioteca.util;

import it.polimi.biblioteca.exception.CustomException;

import java.util.List;

public class ControlloDati {

    public static void controlloStringhe(List<String> data) {
      if(data.stream().anyMatch(String::isBlank)) throw new CustomException("Stringa non valida");
        for (String s : data) {
            if (s == null || s.isBlank()) {
                throw new CustomException("Stringa non valida");
            }
        }
    }

    public static void controlloNumeri(List<Number> numeri) {
        for (Number n : numeri) {
            if (n instanceof Integer && (n.intValue() < 1 || n.intValue() > 5)) {
                throw new CustomException("Numero non valido");
            } else if (n instanceof Long && n.longValue() < 1) {
                throw new CustomException("Id non valido");
            }
        }
    }
}
