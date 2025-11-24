package com.minimax.settingslauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button launchSettingsBtn = findViewById(R.id.launch_settings);
        Button launchCameraBtn = findViewById(R.id.launch_camera);
        Button launchBrowserBtn = findViewById(R.id.launch_browser);
        Button launchCalculatorBtn = findViewById(R.id.launch_calculator);
        
        launchSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchApp("com.android.settings");
            }
        });
        
        launchCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchApp("com.android.camera");
            }
        });
        
        launchBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchApp("com.android.browser");
            }
        });
        
        launchCalculatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchApp("com.android.calculator2");
            }
        });
    }
    
    private void launchApp(String packageName) {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "Запуск " + packageName, Toast.LENGTH_SHORT).show();
            } else {
                // Если нет прямого запуска, используем am start
                launchWithAmStart(packageName);
            }
        } catch (Exception e) {
            launchWithAmStart(packageName);
        }
    }
    
    private void launchWithAmStart(String packageName) {
        try {
            Runtime.getRuntime().exec(new String[]{"su", "-c", "am start --user 0 " + packageName});
            Toast.makeText(this, "Команда выполнена (требует root)", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            try {
                // Попытка без root
                Runtime.getRuntime().exec(new String[]{"am", "start", "--user", "0", packageName});
                Toast.makeText(this, "Команда выполнена", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Ошибка запуска: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}