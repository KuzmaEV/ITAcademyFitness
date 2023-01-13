package by.mk_jd2_92_22.foodCounter.controllers.utils.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class StringToLocalDteTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(@NonNull String source) {

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(source)), ZoneOffset.UTC);
    }
}
