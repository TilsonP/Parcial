package com.Example.parcial;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WebServiceBuscar extends AsyncTask<String, Object, Canciones> {
    private String URL = "https://api.deezer.com/search?q=";
    private URL DUrl;
    private HttpsURLConnection conexion;
    private boolean Comprobador = false;
    private Canciones cancion;


    public WebServiceBuscar(String c) throws MalformedURLException {
        URL = URL+c;
        DUrl = new URL(URL);
        cancion = new Canciones();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    conexion = (HttpsURLConnection) DUrl.openConnection();
                    conexion.setRequestProperty("User-Agent", "rest-app-v0.1");
                    if(conexion.getResponseCode() == 200)
                        Comprobador = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Canciones doInBackground(String... strings) {
        try {
            cancion = Get();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return cancion;
    }

    @Override
    protected void onPostExecute(Canciones cancion) {
        super.onPostExecute(cancion);
    }

    public Canciones Get() throws ProtocolException {
        cancion = new Canciones();
        conexion.setRequestMethod("GET");
        InputStream responseBody = null;
        try {
            responseBody = conexion.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            cancion = parseSingJson(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cancion;
    }

    public boolean Comprobador() {
        return Comprobador;
    }

    public Canciones parseSingJson(JsonReader jsonReader) throws IOException {
        com.Example.parcial.Canciones cancion = new Canciones();
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String data = jsonReader.nextName();
            if(data.equals("data")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()){
                        String name = jsonReader.nextName();
                        if(name.equalsIgnoreCase("title")){
                            cancion.setNombre(jsonReader.nextString());
                            Log.d("sing Title", cancion.getNombre());
                        }else if (name.equalsIgnoreCase("duration")){
                            cancion.setDuracion(Integer.parseInt(jsonReader.nextString()));
                        }else if(name.equalsIgnoreCase("artist")){
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                String artistName = jsonReader.nextName();
                                if(artistName.equalsIgnoreCase("name")){
                                    cancion.setArtista(jsonReader.nextString());
                                    return cancion;
                                }else{
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        }
                        else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                }
                jsonReader.endArray();
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return cancion;
    }
}
