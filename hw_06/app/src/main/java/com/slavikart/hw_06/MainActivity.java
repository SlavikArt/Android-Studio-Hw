package com.slavikart.hw_06;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private final Calendar calendar = Calendar.getInstance();
    private static final String CHANNEL_ID = "main_channel";
    private static final int PERMISSION_REQUEST_CODE = 1001;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted)
                    postNotification();
                else
                    Toast.makeText(this,
                            "У дозволі на сповіщення відмовлено", Toast.LENGTH_SHORT).show();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        setSupportActionBar(toolbar);

        editText = findViewById(R.id.editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            showEditDialog();
            return true;
        } else if (id == R.id.action_choose) {
            showChoiceDialog();
            return true;
        } else if (id == R.id.action_time) {
            showCurrentTime();
            return true;
        } else if (id == R.id.action_date) {
            showDatePicker();
            return true;
        } else if (id == R.id.action_notify) {
            createNotification();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редагування тексту");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String text = input.getText().toString();
            updateText(text);
        });
        builder.setNegativeButton("ВІДМІНА", null);
        builder.show();
    }

    private void showChoiceDialog() {
        String[] options = {"Привіт!", "Як справи?", "Просто текст"};

        new AlertDialog.Builder(this)
                .setTitle("Вибір тексту")
                .setItems(options, (dialog, which) -> {
                    String selected = options[which];
                    updateText(selected);
                })
                .setNegativeButton("Відміна", null)
                .show();
    }

    private void updateText(String text) {
        String time = DateUtils.formatDateTime(this,
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE);
        editText.setText(String.format("%s\n[%s]", text, time));
    }

    private void showCurrentTime() {
        String time = DateUtils.formatDateTime(this,
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME);

        new AlertDialog.Builder(this)
                .setTitle("Поточний час")
                .setMessage(time)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(this,
                this::onDateSet,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(year, month, day);
        String date = DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Дата змінена")
                    .setContentText("Нова дата: " + date)
                    .build();

            NotificationManager manager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, notification);
        } else {
            Toast.makeText(this, "Потрібен дозвіл на сповіщення", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNotification() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED)
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        else
            postNotification();
    }

    private void postNotification() {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Текст порожній", Toast.LENGTH_SHORT).show();
            return;
        }

        String time = DateUtils.formatDateTime(this,
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Сповіщення")
                .setContentText(text + "\nЧас: " + time)
                .build();

        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Main Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
