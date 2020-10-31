package br.com.zambotti.customer.utils;

import br.com.zambotti.customer.advice.ResponseError;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilMethods {
    public static Date toDate(String date){
        Date bind;
        try {
            bind = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new ResponseError(HttpStatus.BAD_REQUEST, "Um ou mais campos informados estão com formato inválido.");
        }
        return bind;
    }

    public static String toDateString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}
