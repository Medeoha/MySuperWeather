package com.example.mysuperweather;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> //Comme pour Flickr, cette classe permet de récupere une image depuis une URL
                                                                    // de facon asynchrone afin de ne pas stopper les autres threads pendant son chargement
{
    URL url;
    ImageView img ;

    public AsyncBitmapDownloader(String s, ImageView im) throws MalformedURLException {

        this.url = new URL("http://openweathermap.org/img/wn/"+s+"@2x.png");
        img = im;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        Bitmap bm = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            bm = BitmapFactory.decodeStream(in);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {

        img.setImageBitmap(bitmap);
    }
}