package com.Example.parcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    public static ArrayList staticSings = new ArrayList<>();
    Adaptador adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.RecyclerView);
        rv.setHasFixedSize(true);

        try {
            WebService webService = new WebService();
            staticSings = webService.execute("").get();
            Log.d("size", String.valueOf(staticSings.size()));
            adaptador = new Adaptador(staticSings);
            rv.setAdapter(adaptador);
            rv.setLayoutManager(new LinearLayoutManager(this));
            if(webService.Comprobador()) {
                Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(this,  "Error", Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_sing:
                Intent intent = new Intent(this, FormActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptador = new Adaptador(staticSings);
        rv.setAdapter(adaptador);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
