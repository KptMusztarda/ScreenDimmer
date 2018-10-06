package me.kptmusztarda.screendimmer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class MainActivity extends Activity {

    private static final String permissions[] = {Manifest.permission.SYSTEM_ALERT_WINDOW};

    private void checkPermissions() {
        for(String p : permissions)
            if(ContextCompat.checkSelfPermission(this, p) != 0)
                ActivityCompat.requestPermissions(this, permissions, 2137);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //checkPermissions();

        if(!OverlapService.isRunning()) {
            startService(new Intent(this, OverlapService.class));
        } else stopService(new Intent(this, OverlapService.class));

        finish();
    }
}
