package com.example.android.tourguideapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView res = (TextView) findViewById(R.id.restaurant);
        res.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent resIntent = new Intent(MainActivity.this,RestaurantActivity.class);
                startActivity(resIntent);
            }
        });
        TextView mus = (TextView) findViewById(R.id.muse);
        mus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent musIntent = new Intent(MainActivity.this,MuseumActivity.class);
                startActivity(musIntent);
            }
        });
        TextView caf = (TextView) findViewById(R.id.cafe);
        caf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent cafIntent = new Intent(MainActivity.this,CafeActivity.class);
                startActivity(cafIntent);
            }
        });
        TextView par = (TextView) findViewById(R.id.par);
        par.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent parIntent = new Intent(MainActivity.this,ParkActivity.class);
                startActivity(parIntent);
            }
        });
    }
}
