package com.example.bautista_06tp1_simplemobilegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HashMap<Button, Boolean> buttonBooleanHashMap;
    private HashMap<Button, Boolean> isMatchedMap;
    private String[] animalPairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button10 = findViewById(R.id.button10);
        Button button11 = findViewById(R.id.button11);
        Button button12 = findViewById(R.id.button12);

        buttonBooleanHashMap = new HashMap<>();
        isMatchedMap = new HashMap<>();
        Button[] allButtons = {button1, button2, button3, button4, button5,
                button6, button7, button8, button9, button10, button11, button12};

        for (Button button : allButtons) {
            buttonBooleanHashMap.put(button, false);
            isMatchedMap.put(button, false);
        }

        animalPairs = new String[]{
                "Koala", "Koala",
                "Fox", "Fox",
                "Crab", "Crab",
                "Bear", "Bear",
                "Monkey", "Monkey",
                "Dog", "Dog"
        };

        replaceButtonText();

    }

    private void replaceButtonText() {
        List<Button> buttonList = new ArrayList<>(buttonBooleanHashMap.keySet());
        Collections.shuffle(buttonList);

        for (int i = 0; i < animalPairs.length; i++) {
            Button button = buttonList.get(i);
            button.setHint(animalPairs[i]);
            button.setText(" ");
            buttonBooleanHashMap.put(button, false);
        }
    }

    public void onButtonClicked(View view) {
        Button clickedButton = (Button) view;
        if (isMatchedMap.get(clickedButton) || buttonBooleanHashMap.get(clickedButton)) return;

        clickedButton.setText(clickedButton.getHint().toString());
        clickedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
        buttonBooleanHashMap.put(clickedButton, true);

        List<Button> pressedButtons = new ArrayList<>();
        for (Map.Entry<Button, Boolean> entry : buttonBooleanHashMap.entrySet()) {
            if (entry.getValue() && !isMatchedMap.get(entry.getKey())) {
                pressedButtons.add(entry.getKey());
            }
        }

        if (pressedButtons.size() == 2) {
            Button button1 = pressedButtons.get(0);
            Button button2 = pressedButtons.get(1);
            TextView textView = findViewById(R.id.textView);

            if (button1.getHint().toString().equals(button2.getHint().toString())) {
                isMatchedMap.put(button1, true);
                isMatchedMap.put(button2, true);
                textView.setText("MATCH!");

                if (checkVictory()){
                    new android.os.Handler().postDelayed(() -> {
                        textView.setText("YOU WIN!");
                    }, 500);
                }else{
                    new android.os.Handler().postDelayed(() -> {
                        textView.setText(" ");
                    }, 1000);
                }
            } else {
                textView.setText("NO MATCH!");
                new android.os.Handler().postDelayed(() -> {
                    textView.setText(" ");
                    button1.setText(" ");
                    button2.setText(" ");
                    button1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                    button2.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                    buttonBooleanHashMap.put(button1, false);
                    buttonBooleanHashMap.put(button2, false);
                }, 1000);
            }
        }
    }

    private boolean checkVictory() {
        for (Map.Entry<Button, Boolean> entry : isMatchedMap.entrySet()) {
            if (!entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void retryButtonClicked(View view) {
        TextView textView = findViewById(R.id.textView);
        for (Map.Entry<Button, Boolean> entry : buttonBooleanHashMap.entrySet()) {
            Button button = entry.getKey();
            boolean isPressed = entry.getValue();

            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
            buttonBooleanHashMap.put(button, false);
            isMatchedMap.put(button, false);
            textView.setText(" ");
            replaceButtonText();
        }
    }
}
