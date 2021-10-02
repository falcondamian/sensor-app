package com.falcon.garden;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.ui.Legend;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.falcon.garden.client.SensorGatewayClient;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private final SensorGatewayClient client = new SensorGatewayClient();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);
        cartesian.padding(60d, 10d, 15d, 0d);

        List<SensorData> data;
        try {
            data = client.retrieveLatest();
        } catch (IOException e) {
            data = new LinkedList<>();
        }

        List<DataEntry> airHumidity = data.stream().map(this::mapCustomEntry).collect(Collectors.toList());
        Set set = Set.instantiate();
        set.data(airHumidity);

        Mapping airHumidityMapping = set.mapAs("{ x: 'x', value: 'value' }");
        Line humidityLine = cartesian.line(airHumidityMapping);
        humidityLine.name("Air Humidity");
        humidityLine.stroke("4 rgb(0, 230, 28)");

        Mapping airTemperatureMapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Line airTemperatureLine = cartesian.line(airTemperatureMapping);
        airTemperatureLine.name("Air Temperature");
        airTemperatureLine.stroke("4 rgb(255, 64, 25)");

        Mapping soilHumidityMapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Line soilHumidityLine = cartesian.line(soilHumidityMapping);
        soilHumidityLine.name("Soil Humidity");
        soilHumidityLine.stroke("4 rgb(102, 204, 255)");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private DataEntry mapCustomEntry(SensorData sd) {
        return new CustomDataEntry(formatter.format(sd.collectionDateTime.plusHours(2)), sd.airHumidity, sd.airTemperature, sd.soilHumidity);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

}