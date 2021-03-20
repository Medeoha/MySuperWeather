package com.example.mysuperweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLovelyOnClickListener implements View.OnClickListener {//Bouton pour le détail du jour

    JSONObject j;
    FragmentManager frag;
    Bundle receive;
    public MyLovelyOnClickListener(JSONObject myLovelyVariable, FragmentManager f, Bundle send) {//on recupere les données (longitude latitude) via un Bundle
        this.j = myLovelyVariable;
        this.receive = send;
        this.frag = f;
    }
    @Override
    public void onClick(View v) {

        FragmentTransaction ft = frag.beginTransaction();
        DetailActivity detail = new DetailActivity();
        Bundle sendData = new Bundle();
        try {

            double temperature = j.getJSONObject("main").getDouble("temp");

            double temperatureMax = j.getJSONObject("main").getDouble("temp_max");
            double temperatureMin = j.getJSONObject("main").getDouble("temp_min");
            double temperature_feel = j.getJSONObject("main").getDouble("feels_like");

            long date_sunrise =  (long)j.getJSONObject("sys").getLong("sunrise")*1000;
            Date date_ss = new Date(date_sunrise);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String formattedDateSR = dateFormat.format(date_ss);

            long date_sunset = (long)j.getJSONObject("sys").getLong("sunset")*1000;
            Date date_sr = new Date(date_sunset);
            String formattedDateSS = dateFormat.format(date_sr);


            sendData.putString("temperature", Double.toString(temperature));
            sendData.putString("temperatureMin", Double.toString(temperatureMin));
            sendData.putString("temperatureMax", Double.toString(temperatureMax));
            sendData.putString("temperatureFeel", Double.toString(temperature_feel));
            sendData.putString("sunset",formattedDateSS);
            sendData.putString("sunrise",formattedDateSR);
            sendData.putString("latitude",receive.getString("latitude"));
            sendData.putString("longitude",receive.getString("longitude"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        detail.setArguments(sendData);
        v.findViewById(R.id.details_btn).setVisibility(View.INVISIBLE);
        ft.replace(R.id.TodayCoordinatorLayout,detail);
        ft.show(detail);
        ft.commit();
    }
}
