package com.example.hp.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
public class MainActivity extends AppCompatActivity {
    EditText txtLimitInput;
    TextView txtLimitOutput;
    Button btnStart, btnStop;
    Intent intent;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        intent = new Intent(this, CounterService.class);
        txtLimitInput = (EditText) findViewById(R.id.txt_limit_input);
        txtLimitOutput = (TextView) findViewById(R.id.txt_limit_output);
        btnStart = (Button) findViewById(R.id.btn_ServiceStart);
        btnStop = (Button) findViewById(R.id.btn_service_stop);
        btnStop.setEnabled(false);
        //start service
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtLimitInput.getText().toString().isEmpty()) {
                    txtLimitInput.setError("Limit Needed");
                } else {
                    int value = Integer.parseInt(txtLimitInput.getText().toString());
                    intent.putExtra("limit", value);
                    startService(intent);
                    btnStart.setEnabled(false);
                    btnStop.setEnabled(true);
                }
            }
        });
        //stop servive
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MyEvents event) {
        txtLimitOutput.setText(event.getValue() + " %");
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
