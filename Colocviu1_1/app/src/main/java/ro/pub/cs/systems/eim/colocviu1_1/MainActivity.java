package ro.pub.cs.systems.eim.colocviu1_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView titleText;
    private Button nordBtn, southBtn, eastBtn, weastBtn, navigateToSecondaryActivityButton;


    private String TittleValueText = "";
    private int numberOfPresses = 0;

    private IntentFilter intentFilter = new IntentFilter();

    private int serviceStatus = Constants.SERVICE_STOPPED;

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

                case R.id.navigation_btn:
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_1SecondaryActivity.class);
                    String steps = titleText.getText().toString();
                    intent.putExtra("steptsUnthilNow", steps);
                    startActivityForResult(intent, 1);
                    break;
            }
            numberOfPresses++;

            if (numberOfPresses >= 4) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_1Service.class);
                intent.putExtra("StepUntilNow",titleValue );
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_1Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

        titleText = (TextView)findViewById(R.id.title_label);

        nordBtn = (Button)findViewById(R.id.nord);
        southBtn = (Button)findViewById(R.id.south);
        eastBtn = (Button)findViewById(R.id.east);
        weastBtn = (Button)findViewById(R.id.west);


        nordBtn.setOnClickListener(buttonClickListener);
        southBtn.setOnClickListener(buttonClickListener);
        eastBtn.setOnClickListener(buttonClickListener);
        weastBtn.setOnClickListener(buttonClickListener);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigation_btn);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        titleText.setText("-");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            numberOfPresses = 0;
            titleText.setText("");
        }
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