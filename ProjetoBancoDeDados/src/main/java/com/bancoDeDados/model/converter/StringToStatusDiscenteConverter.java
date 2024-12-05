package com.bancoDeDados.model.converter;

import com.bancoDeDados.model.Discente.StatusDiscente;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStatusDiscenteConverter implements Converter<String, StatusDiscente> {

    @Override
    public StatusDiscente convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return StatusDiscente.fromString(source);
    }
}
