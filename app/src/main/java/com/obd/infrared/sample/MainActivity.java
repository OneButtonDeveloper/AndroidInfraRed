package com.obd.infrared.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.obd.infrared.InfraRed;
import com.obd.infrared.log.LogToConsole;
import com.obd.infrared.log.LogToEditText;
import com.obd.infrared.patterns.PatternConverter;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.TransmitterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private LogToEditText log;
    private InfraRed infraRed;
    private TransmitInfo[] patterns;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String TAG = "IR";

        Button transmitButton = (Button) this.findViewById(R.id.transmit_button);
        transmitButton.setOnClickListener(this);

        // Log messages print to EditText
        EditText console = (EditText) this.findViewById(R.id.console);
        log = new LogToEditText(console, TAG);
        log.log("Hello, world!");

        // Log messages print with Log.d(), Log.w(), Log.e()
        //LogToConsole log = new LogToConsole(TAG);

        // Turn off log
        // LogToAir log = new LogToAir(TAG);

        infraRed = new InfraRed(this, log);
        // detect transmitter type (ConsumerIrManager, Samsung obsolete service or HTC IR SDK)
        TransmitterType transmitterType = infraRed.detect();

        // initialize transmitter by type
        infraRed.createTransmitter(transmitterType);

        // initialize converter for convert pulse patterns to time patterns for Android 5.0 (Lollipop) or to Samsung obsolete service format
        PatternConverter patternConverter = new PatternConverter(log);
        // initialize patterns
        List<TransmitInfo> patterns = new ArrayList<>();
        // Nikon D7100 v.1
        patterns.add(patternConverter.createTransmitInfo(38400, 1, 105, 5, 1, 75, 1095, 20, 60, 20, 140, 15, 2500, 80, 1));
        // Nikon D7100 v.2
        patterns.add(patternConverter.createTransmitInfo(38400, 77, 1069, 16, 61, 16, 137, 16, 2427, 77, 1069, 16, 61, 16, 137, 16));
        this.patterns = patterns.toArray(new TransmitInfo[patterns.size()]);

        for (TransmitInfo transmitInfo : this.patterns) {
            log.log(transmitInfo.toString());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        infraRed.start();
    }


    @Override
    public void onClick(View v) {
        TransmitInfo transmitInfo = patterns[random.nextInt(patterns.length)];
        infraRed.transmit(transmitInfo);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        infraRed.stop();

        if (log != null) {
            log.destroy();
        }
    }

}
