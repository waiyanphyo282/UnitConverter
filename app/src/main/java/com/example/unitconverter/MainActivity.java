package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner fromUnit, toUnit;
    private TextView resultText;

    private final String[] units = {"Meters", "Kilometers", "Centimeters", "Millimeters", "Miles", "Yards", "Feet", "Inches"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        Button convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Setup spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromUnit.setAdapter(adapter);
        toUnit.setAdapter(adapter);

        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void convertUnits() {
        if (inputValue.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(inputValue.getText().toString());
        String from = fromUnit.getSelectedItem().toString();
        String to = toUnit.getSelectedItem().toString();

        // Convert to meters first (base unit)
        double metersValue = convertToMeters(value, from);
        // Then convert from meters to target unit
        double result = convertFromMeters(metersValue, to);

        // Format result to 4 decimal places
        String formattedResult = String.format(Locale.getDefault(), "%.4f %s", result, to);
        resultText.setText(formattedResult);
    }

    private double convertToMeters(double value, String unit) {
        switch (unit) {
            case "Kilometers": return value * 1000;
            case "Centimeters": return value / 100;
            case "Millimeters": return value / 1000;
            case "Miles": return value * 1609.344;
            case "Yards": return value * 0.9144;
            case "Feet": return value * 0.3048;
            case "Inches": return value * 0.0254;
            default: return value;
        }
    }

    private double convertFromMeters(double meters, String unit) {
        switch (unit) {
            case "Kilometers": return meters / 1000;
            case "Centimeters": return meters * 100;
            case "Millimeters": return meters * 1000;
            case "Miles": return meters / 1609.344;
            case "Yards": return meters / 0.9144;
            case "Feet": return meters / 0.3048;
            case "Inches": return meters / 0.0254;
            default: return meters;
        }
    }
}