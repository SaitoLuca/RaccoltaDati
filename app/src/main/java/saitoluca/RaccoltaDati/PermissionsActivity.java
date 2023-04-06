package saitoluca.RaccoltaDati;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import static saitoluca.RaccoltaDati.MainActivity.arePermsSetted;
import static saitoluca.RaccoltaDati.MainActivity.perms;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionsActivity extends AppCompatActivity {
    private static final String TAG = "PermissionsActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateView();
        Button enableBtn = findViewById(R.id.btnEnable);
        enableBtn.setOnClickListener(view -> permissions());
    }

    private void updateView() {
        arePermsSetted=true;
        TextView[] views = {findViewById(R.id.txtPerm1),findViewById(R.id.txtPerm2),findViewById(R.id.txtPerm3)};
        if (getGrantStatus()){
            views[0].setTextColor(getColor(R.color.redTxt));
            arePermsSetted=false;
        }else{
            views[0].setTextColor(getColor(R.color.greenTxt));
        }
        for (int i=1;i<perms.length;i++) {
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), perms[i]) != PackageManager.PERMISSION_GRANTED){
                views[i].setTextColor(getColor(R.color.redTxt));
                arePermsSetted=false;
            }else{
                views[i].setTextColor(getColor(R.color.greenTxt));
            }
        }
    }

    private void permissions() {
        if(arePermsSetted){
            this.finish();
        }
        if (getGrantStatus()){
            this.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        for (int i=1; i<perms.length;i++){
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), perms[i]) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, perms,1313);
            }
        }
        updateView();
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
