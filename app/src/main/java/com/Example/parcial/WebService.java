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

public class WebService extends AsyncTask<String, Object, ArrayList> {
    private final String URL = "https://api.deezer.com/playlist/93489551/tracks";
    private URL DUrl = new URL(URL);
    private HttpsURLConnection conexion;
    private boolean Comprobador = false;
    private ArrayList Canciones;



    public WebService() throws MalformedURLException {
        Canciones = new ArrayList();
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
    protected ArrayList doInBackground(String... strings) {
        try {
            Canciones = Get();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return Canciones;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
    }

    public ArrayList Get() throws ProtocolException {
        Canciones = new ArrayList();
        conexion.setRequestMethod("GET");
        InputStream responseBody = null;
        try {
            responseBody = conexion.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            Canciones = parseSingJson(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Canciones;
    }

    public boolean Comprobador() {
        return Comprobador;
    }

    public ArrayList<Canciones> parseSingJson(JsonReader jsonReader) throws IOException {
        ArrayList<com.Example.parcial.Canciones> canciones = new ArrayList<>();
        com.Example.parcial.Canciones cancion;
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String data = jsonReader.nextName();
            if(data.equals("data")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    cancion = new Canciones();
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
                                    canciones.add(cancion);
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
        return canciones;
    }
}
