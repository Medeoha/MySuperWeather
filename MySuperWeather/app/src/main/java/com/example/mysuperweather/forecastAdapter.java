package com.example.mysuperweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class forecastAdapter extends BaseAdapter {//meme principe que pour Flickr, l'adapter va gerer la liste d'objet meteo qui va etre affiché
    private Context context;
    private List<ForecastWeatherObject> weatherList;
    private LayoutInflater inflater;
    private List<Integer> tabImgID;

    public forecastAdapter(Context context, List<ForecastWeatherObject> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return weatherList.size();
        //return 2;
    }

    @Override
    public ForecastWeatherObject getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.adapter_item, null);
        ForecastWeatherObject currentWeatherForecast = getItem(position); // On recupere l'objet lié a la journée et on 
        TextView textemp = view.findViewById(R.id.temp);
        textemp.setText(currentWeatherForecast.getTemperature());

        TextView textdate = view.findViewById(R.id.date_forecast);
        textdate.setText(currentWeatherForecast.getDate());

        try {
            new AsyncBitmapDownloader(currentWeatherForecast.iconid,view.findViewById(R.id.weather_icon)).execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return view;
    }
}
