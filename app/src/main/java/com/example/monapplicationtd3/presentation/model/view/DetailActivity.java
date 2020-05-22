package com.example.monapplicationtd3.presentation.model.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monapplicationtd3.R;
import com.example.monapplicationtd3.Singletons;
import com.example.monapplicationtd3.presentation.model.Natures;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtDetail = findViewById(R.id.detail_txt);
        Intent intent = getIntent();
        String naturesJson = intent.getStringExtra("naturesKey");
        Natures natures = Singletons.getGson().fromJson(naturesJson, Natures.class);
        showDetail(natures);

    }

    private void showDetail(Natures natures) {
        txtDetail.setText(natures.getName());
    }
}