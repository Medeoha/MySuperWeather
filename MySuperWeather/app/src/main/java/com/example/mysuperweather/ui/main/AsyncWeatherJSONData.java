package com.example.mysuperweather.ui.main;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

public class AsyncWeatherJSONData extends AsyncTask<String, Void, JSONObject> {

    Consumer<JSONObject> callback;
    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=48.764519&lon=2.3969249&appid=f7e10ee1642ff17719eee0cd67ab01c5&units=metric");
    public AsyncWeatherJSONData(Consumer<JSONObject> callback) throws MalformedURLException {

        this.callback = callback;
    }

    public AsyncWeatherJSONData(Consumer<JSONObject> callback, String url) throws MalformedURLException {
        this.url = new URL(url);
        Log.i("URL",this.url.toString());
        this.callback = callback;
    }

    public AsyncWeatherJSONData(Consumer<JSONObject> callback, String latitude, String longitude) throws MalformedURLException {
        this.url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=f7e10ee1642ff17719eee0cd67ab01c5&units=metric");

        this.callback = callback;
    }


    @Override
    protected JSONObject doInBackground(String... strings) { // On se connecte a l'url donné par l'un des constructeurs
        HttpURLConnection urlConnection = null;
        JSONObject j = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String s = readStream(in);
            System.out.println(s);
            j = new JSONObject(s);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



        return j;
    }
    private String readStream(InputStream in) throws IOException { // Transforme un inputstream en string pour que l'on puisse le parser en JSON par la suite
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    protected void onPostExecute(JSONObject jsonObject) {
        try {
            callback.accept(jsonObject); //On renvoie le JSON
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
