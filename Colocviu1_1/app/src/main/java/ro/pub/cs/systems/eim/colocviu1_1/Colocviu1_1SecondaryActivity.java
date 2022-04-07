package ro.pub.cs.systems.eim.colocviu1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Colocviu1_1SecondaryActivity extends AppCompatActivity {

    private TextView numberOfClicksTextView;
    private Button resumeButton, cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.register_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_1_secondary);
        numberOfClicksTextView = (TextView)findViewById(R.id.number_of_clicks_text_view);

        Intent intent = getIntent();
        if (intent != null && ((Intent) intent).getExtras().containsKey("steptsUnthilNow")) {
            String numberOfClicks = intent.getStringExtra("steptsUnthilNow");
            numberOfClicksTextView.setText(String.valueOf(numberOfClicks));
        }

        resumeButton = (Button)findViewById(R.id.register_button);
        resumeButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);

    }
}