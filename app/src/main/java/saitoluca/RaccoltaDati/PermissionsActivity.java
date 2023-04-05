package saitoluca.RaccoltaDati;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button enableBtn = findViewById(R.id.btnEnable);
        enableBtn.setOnClickListener(view -> finish());
    }
}
