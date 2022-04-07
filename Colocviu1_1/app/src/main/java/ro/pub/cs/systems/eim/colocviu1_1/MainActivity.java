package ro.pub.cs.systems.eim.colocviu1_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView titleText;
    private Button nordBtn, southBtn, eastBtn, weastBtn;


    private String TittleValueText = "";
    private int numberOfPresses = 0;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String titleValue = titleText.getText().toString();

            switch(view.getId()) {
                case R.id.nord:
                    titleValue = titleValue + "Nord, ";
                    titleText.setText(String.valueOf(titleValue));
                    break;
                case R.id.south:
                    titleValue = titleValue + "South, ";
                    titleText.setText(String.valueOf(titleValue));
                    break;
                case R.id.east:
                    titleValue = titleValue + "East, ";
                    titleText.setText(String.valueOf(titleValue));
                    break;
                case R.id.west:
                    titleValue = titleValue + "West, ";
                    titleText.setText(String.valueOf(titleValue));
                    break;
            }
            numberOfPresses++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = (TextView)findViewById(R.id.title_label);

        nordBtn = (Button)findViewById(R.id.nord);
        southBtn = (Button)findViewById(R.id.south);
        eastBtn = (Button)findViewById(R.id.east);
        weastBtn = (Button)findViewById(R.id.west);


        nordBtn.setOnClickListener(buttonClickListener);
        southBtn.setOnClickListener(buttonClickListener);
        eastBtn.setOnClickListener(buttonClickListener);
        weastBtn.setOnClickListener(buttonClickListener);

        titleText.setText("- ");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("saveCounts", numberOfPresses);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("[onRestoreInstanceState]", "Enter in onRestoreInstanceState");

        if (savedInstanceState.containsKey("saveCounts")) {
            numberOfPresses = savedInstanceState.getInt("saveCounts");
        } else {
            numberOfPresses = 0;

        }

        Log.d("[onRestoreInstanceState-Value]", String.valueOf(numberOfPresses));
    }
}