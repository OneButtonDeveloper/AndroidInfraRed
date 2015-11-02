package com.obd.infrared.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.obd.infrared.InfraRed;
import com.obd.infrared.log.LogToEditText;
import com.obd.infrared.patterns.PatternAdapter;
import com.obd.infrared.patterns.PatternConverter;
import com.obd.infrared.patterns.PatternConverterUtils;
import com.obd.infrared.patterns.PatternType;
import com.obd.infrared.transmit.TransmitInfo;
import com.obd.infrared.transmit.TransmitterType;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private LogToEditText log;
    private InfraRed infraRed;
    private TransmitInfo[] patterns;

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

        // Log messages print with Log.d(), Log.w(), Log.e()
        // LogToConsole log = new LogToConsole(TAG);

        // Turn off log
        // LogToAir log = new LogToAir(TAG);

        infraRed = new InfraRed(this, log);
        // detect transmitter type
        TransmitterType transmitterType = infraRed.detect();

        // initialize transmitter by type
        infraRed.createTransmitter(transmitterType);

        // initialize raw patterns
        List<PatternConverter> rawPatterns = new ArrayList<>();
        // Canon
        // rawPatterns.add(new PatternConverter(PatternType.Intervals, 33000, 500, 7300, 500, 200));
        // Nikon v.1
        rawPatterns.add(new PatternConverter(PatternType.Cycles, 38400, 1, 105, 5, 1, 75, 1095, 20, 60, 20, 140, 15, 2500, 80, 1));
        // Nikon v.2
        rawPatterns.add(new PatternConverter(PatternType.Cycles, 38400, 77, 1069, 16, 61, 16, 137, 16, 2427, 77, 1069, 16, 61, 16, 137, 16));
        // Nikon v.3
        rawPatterns.add(new PatternConverter(PatternType.Intervals, 38000, 2000, 27800, 400, 1600, 400, 3600, 400, 200));
        // Nikon v.3 fromString
        rawPatterns.add(PatternConverterUtils.fromString(PatternType.Intervals, 38000, "2000, 27800, 400, 1600, 400, 3600, 400, 200"));
        // Nikon v.3 fromHexString
        rawPatterns.add(PatternConverterUtils.fromHexString(PatternType.Intervals, 38000, "0x7d0 0x6c98 0x190 0x640 0x190 0xe10 0x190 0xc8"));
        // Nikon v.3 fromHexString without 0x
        rawPatterns.add(PatternConverterUtils.fromHexString(PatternType.Intervals, 38000, "7d0 6c98 190 640 190 e10 190 c8"));


        // adapt the patterns for the device that is used to transmit the patterns
        PatternAdapter patternAdapter = new PatternAdapter(log);

        TransmitInfo[] transmitInfoArray = new TransmitInfo[rawPatterns.size()];
        for (int i = 0; i < transmitInfoArray.length; i++) {
            transmitInfoArray[i] = patternAdapter.createTransmitInfo(rawPatterns.get(i));
        }
        this.patterns = transmitInfoArray;

        for (TransmitInfo transmitInfo : this.patterns) {
            log.log(transmitInfo.toString());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        infraRed.start();
    }


    private int currentPattern = 0;

    @Override
    public void onClick(View v) {
        TransmitInfo transmitInfo = patterns[currentPattern++];
        if (currentPattern >= patterns.length) currentPattern = 0;
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
