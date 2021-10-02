package com.falcon.garden.client;

import com.falcon.garden.SensorData;
import com.falcon.garden.util.LocalDateTimeJsonAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SensorGatewayClient {

    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder()
            .add(LocalDateTime.class, new LocalDateTimeJsonAdapter())
            .build();
    private final Type type = Types.newParameterizedType(List.class, SensorData.class);
    private final JsonAdapter<List<SensorData>> sensorDataJsonAdapter = moshi.adapter(type);

    public List<SensorData> retrieveLatest() throws IOException {

        Request request = new Request.Builder()
                .url("https://sensor-api-gateway.herokuapp.com/retrieveLatest")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return sensorDataJsonAdapter.fromJson(response.body().source());
        }
    }
}
