package com.example.mysuperweather;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mysuperweather.ui.main.AsyncWeatherJSONData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastWeather#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastWeather extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<ForecastWeatherObject> forecastArray;
    View v;
    GridView test;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastWeather() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastWeather.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastWeather newInstance(String param1, String param2) {
        ForecastWeather fragment = new ForecastWeather();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forecastArray = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle localisation = getArguments();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.grid_view_forecast, container, false);
        test = (GridView) v.findViewById(R.id.forecast_grid_view);
        Bundle localisation = getArguments();

        try {
            new AsyncWeatherJSONData(this::FragmentJSON,"https://api.openweathermap.org/data/2.5/onecall?lat="+localisation.getString("latitude")+"&lon="+localisation.getString("longitude")+"&appid=f7e10ee1642ff17719eee0cd67ab01c5&units=metric").execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //GridView weatherForecastView = (GridView) v.findViewById(R.id.forecast_grid_view);


        return v;
    }

    private void FragmentJSON(JSONObject j) {
        try {
             forecastArray.clear();
             for(int i = 2 ; i < 8 ; i++)//On recupere les infos sur les 6 prochains jours
             {
                 double temperatureMaxJ3 = j.getJSONArray("daily").getJSONObject(i).getJSONObject("temp").getDouble("max");
                 double temperatureMinJ3 =  j.getJSONArray("daily").getJSONObject(i).getJSONObject("temp").getDouble("min");
                 String temperature = "Max :"+temperatureMaxJ3+"°C"+"\nMin :" + temperatureMinJ3+"°C";

                 long date_int_j3= (long)j.getJSONArray("daily").getJSONObject(i).getLong("dt")*1000;
                 Date date = new Date(date_int_j3);
                 SimpleDateFormat dateFormat = new SimpleDateFormat("EEE-dd-MM");
                 String formattedDate = dateFormat.format(date_int_j3);

                 String iconID = j.getJSONArray("daily").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");

                 forecastArray.add(new ForecastWeatherObject(temperature,formattedDate,iconID));
             }


             test.setAdapter(new forecastAdapter(this.getActivity(),forecastArray));// On transmet ces infos a l'adapter qui va traiter les donner pour l'affichage
            switch (j.getJSONArray("weather").getJSONObject(2).getString("main")) {//pour le changement de fond d'ecran
                case "Clouds":
                    v.setBackground(getResources().getDrawable(R.drawable.clouds));
                    break;
                case "Rain":
                case "Drizzle"    :
                    v.setBackground(getResources().getDrawable(R.drawable.rain));
                    break;
                case  "Thunderstorm"   :
                    v.setBackground(getResources().getDrawable(R.drawable.thunder));
                    break;
                case  "Clear"  :
                    v.setBackground(getResources().getDrawable(R.drawable.clear));
                    break;
                case "Snow" :
                    v.setBackground(getResources().getDrawable(R.drawable.neige));
                    break;
                default:
                    v.setBackground(getResources().getDrawable(R.drawable.brume));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}