package com.slavikart.hw_11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView statusText;
    private ProgressBar progressBar;
    private Button startStopBtn;
    private Button cancelBtn;
    private MyAsyncTask task;
    private static final int ITERATIONS = 100;
    private static final int DELAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        progressBar = findViewById(R.id.progressBar);
        startStopBtn = findViewById(R.id.startStopBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        progressBar.setMax(ITERATIONS);

        startStopBtn.setOnClickListener(v -> {
            if (task == null || task.getStatus() == AsyncTask.Status.FINISHED) {
                startTask();
            } else {
                stopTask();
            }
        });

        cancelBtn.setOnClickListener(v -> {
            if (task != null) task.cancel(true);
        });
    }

    private void startTask() {
        task = new MyAsyncTask();
        task.execute();
        startStopBtn.setText("Stop");
        cancelBtn.setEnabled(true);
    }

    private void stopTask() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
        startStopBtn.setText("Start");
        cancelBtn.setEnabled(false);
        statusText.setText("Pending");
        progressBar.setProgress(0);
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            statusText.setText("Running");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < ITERATIONS && !isCancelled(); i++) {
                try {
                    Thread.sleep(DELAY);
                    publishProgress(i + 1);
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            statusText.setText("Finished");
            startStopBtn.setText("Start");
            cancelBtn.setEnabled(false);
        }

        @Override
        protected void onCancelled() {
            statusText.setText("Cancelled");
            startStopBtn.setText("Start");
            cancelBtn.setEnabled(false);
        }
    }
}