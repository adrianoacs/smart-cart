package br.com.smartcart.application.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ConvertUtil {

    public static BigDecimal toBigDecimal(String value){

        NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
        Number number;
        try {
            number = format.parse(value);
        } catch (ParseException e) {
            // todo: criar log e definir como proceder em caso de erro
            throw new RuntimeException(e);
        }

        return new BigDecimal(number.toString());
    }
}
