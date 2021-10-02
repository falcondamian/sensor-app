package com.falcon.garden.util;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeJsonAdapter extends JsonAdapter<LocalDateTime> {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public synchronized LocalDateTime fromJson(JsonReader reader) throws IOException {
        if (reader.peek() == JsonReader.Token.NULL) {
            return reader.nextNull();
        }
        String string = reader.nextString();
        return LocalDateTime.from(formatter.parse(string));
    }

    @Override
    public synchronized void toJson(JsonWriter writer, LocalDateTime value) throws IOException {
        if (value == null) {
            writer.nullValue();
        } else {
            String string = formatter.format(value);
            writer.value(string);
        }
    }
}
