package com.Example.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class FormActivity extends AppCompatActivity {

    TextView TextNombre, TextArtista, TextDuracion;
    EditText editCancion;
    Button btnAgregar;
    Canciones cancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        TextNombre = findViewById(R.id.textNombre);
        TextArtista = findViewById(R.id.textArtista);
        TextDuracion = findViewById(R.id.textDuracion);
        btnAgregar = findViewById(R.id.btnBuscar);
        editCancion = findViewById(R.id.editText);
    }


    public void BuscarCancion(View view) throws MalformedURLException, ExecutionException, InterruptedException {
        WebServiceBuscar buscar = new WebServiceBuscar(editCancion.getText().toString());
        cancion = buscar.execute("").get();
        TextNombre.setText(cancion.getNombre());
        TextArtista.setText(cancion.getArtista());
        TextDuracion.setText(cancion.getDuracion()+"");
    }

    public void AgregarCancion(View view){
        MainActivity.staticSings.add(cancion);
        finish();
    }
}
