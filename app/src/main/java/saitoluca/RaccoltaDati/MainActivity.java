package saitoluca.RaccoltaDati;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    protected static final String[] perms = {android.Manifest.permission.PACKAGE_USAGE_STATS,android.Manifest.permission.ACTIVITY_RECOGNITION};
    private static final String TAG = "MainActivity";
    protected static boolean arePermsSetted=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        arePermsSetted = true;
        if (getGrantStatus()){
            arePermsSetted = false;
        }else {
            for (int i = 1; i < perms.length; i++) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), perms[i]) != PackageManager.PERMISSION_GRANTED) {
                    arePermsSetted = false;
                    break;
                }
            }
        }
        if(!arePermsSetted) {
            Intent intent = new Intent(this, PermissionsActivity.class);
            this.startActivity(intent);
        }
    }

    //method to check APP_USAGE permission
    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.unsafeCheckOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getApplicationContext().getPackageName());
        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) != PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode != MODE_ALLOWED);
        }
    }
}