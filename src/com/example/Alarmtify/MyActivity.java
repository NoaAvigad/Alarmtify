package com.example.Alarmtify;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyActivity extends Activity
{

    private TimePicker timePicker;
    private Switch alarmSwitch;

    private ScheduledExecutorService scheduledExecutor;
    private Runnable runnable;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        addListeners();
        createRunnableForScheduled();
        startScheduledExecutor();

    }

    /**
     * sets the Switch and the TimePicker's listeners
     */
    private void addListeners()
    {
        this.alarmSwitch = (Switch) findViewById(R.id.alarm_switch);
        this.timePicker = (TimePicker) findViewById(R.id.timePicker);

        alarmSwitch.setChecked(true);
        alarmSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked)
            {
                if (isChecked)
                {
                    timePicker.setEnabled(true);
                    timePicker.setVisibility(View.VISIBLE);
                    startScheduledExecutor();

                } else
                {
                    timePicker.setVisibility(View.INVISIBLE);
                    if (scheduledExecutor != null && !scheduledExecutor.isShutdown())
                    {
                        scheduledExecutor.shutdown();
                        System.out.println("SHUT DOWN!");
                    }

                }
            }
        });
    }

    /**
     * initializes the runnable field
     */
    private void createRunnableForScheduled()
    {
        this.runnable = new Runnable()
        {
            @Override
            public void run()
            {
                checkTime();
            }
        };
    }

    /**
     * checks if the time specified in the TimePicker equals the current time
     */
    private void checkTime()
    {
        System.out.println("check time.....  :D  ");
    }

    /**
     * starts the ScheduledExecutor to check if alarm should go off
     */
    private void startScheduledExecutor()
    {
        this.scheduledExecutor = Executors.newScheduledThreadPool(1);
        this.scheduledExecutor.scheduleAtFixedRate(this.runnable, 0, 1, TimeUnit.SECONDS);
    }



}


/*
ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
 */