package saitoluca.RaccoltaDati;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    protected static final String[] perms = {android.Manifest.permission.PACKAGE_USAGE_STATS,android.Manifest.permission.ACTIVITY_RECOGNITION};
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        boolean arePermsSetted = true;
        for (String perm : perms) {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), perm) != PackageManager.PERMISSION_GRANTED){
                arePermsSetted = false;
                break;
            }
        }
        if(!arePermsSetted) {
            Intent intent = new Intent(this, PermissionsActivity.class);
            this.startActivity(intent);
        }
    }
}