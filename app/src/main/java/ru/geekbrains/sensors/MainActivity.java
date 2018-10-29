package ru.geekbrains.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textConsole;
    private TextView textLight;
    private TextView textTemperature;
    private TextView textHumidity;
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor sensorLight;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textConsole = findViewById(R.id.textConsole);
        textLight = findViewById(R.id.textLight);
        textTemperature = findViewById(R.id.textTemperature);
        textHumidity = findViewById(R.id.textHumidity);

        // Менеджер датчиков
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Получить все датчики
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // Датчик освещенности, он есть на многих моделях
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Регистрируем слушателя датчика освещенности
        sensorManager.registerListener(listenerLight, sensorLight,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);

        // Показать все сенсоры
//        showSensors();
    }

    // Если приложение свернуто, то не будем тратить энергию на получение информации по датчикам
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
        sensorManager.unregisterListener(listenerTemperature, sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
    }

    // вывод всех сенсоров
    private void showSensors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Sensor sensor : sensors) { stringBuilder
                    .append("name = ").append(sensor.getName())
                    .append(", type = ").append(sensor.getType())
                    .append("\n")
                    .append("vendor = ").append(sensor.getVendor())
                    .append(" ,version = ").append(sensor.getVersion())
                    .append("\n")
                    .append("max = ").append(sensor.getMaximumRange())
                    .append(", resolution = ").append(sensor.getResolution())
                    .append("\n").append("--------------------------------------").append("\n");
        }
        textConsole.setText(stringBuilder);
    }

    // Вывод датчика освещенности
    private void showLightSensors(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Light Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textLight.setText(stringBuilder);
    }

    private void showTemperatureSensors(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textTemperature.setText(stringBuilder);
    }

    private void showHumiditySensors(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Humidity Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textHumidity.setText(stringBuilder);
    }

    // Слушатель датчика освещенности
    private final SensorEventListener listenerLight = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showLightSensors(event);
        }
    };

    private final SensorEventListener listenerTemperature = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTemperatureSensors(event);
        }
    };

    private final SensorEventListener listenerHumidity = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumiditySensors(event);
        }
    };
}
