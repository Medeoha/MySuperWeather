package com.example.mysuperweather;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.BundleCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysuperweather.ui.main.AsyncWeatherJSONData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TomorrowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TomorrowFragment extends Fragment {
    private View v;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TomorrowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TomorrowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TomorrowFragment newInstance(String param1, String param2) {
        TomorrowFragment fragment = new TomorrowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        Bundle localisation = getArguments();

        try {
            new AsyncWeatherJSONData(this::FragmentJSON,"https://api.openweathermap.org/data/2.5/onecall?lat="+localisation.getString("latitude")+"&lon="+localisation.getString("longitude")+"&appid=f7e10ee1642ff17719eee0cd67ab01c5&units=metric").execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return v;
    }

    private void FragmentJSON(JSONObject j) {//Ici on recupere les data du json puis on met a jour le background
        try {

            double temperatureMax = j.getJSONArray("daily").getJSONObject(1).getJSONObject("temp").getDouble("max");
            double temperatureMin = j.getJSONArray("daily").getJSONObject(1).getJSONObject("temp").getDouble("min");

            String def = j.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main");
            double wind = j.getJSONArray("daily").getJSONObject(1).getDouble("wind_speed");
            String icon = j.getJSONArray("daily").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("icon");

            long date_int = (long) j.getJSONArray("daily").getJSONObject(1).getLong("dt") * 1000;
            Date date = new Date(date_int);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE-dd-MM");
            String formattedDate = dateFormat.format(date);


            ((TextView) v.findViewById(R.id.TomorrowTemp)).setText("Max: " + Double.toString(temperatureMax)+"°C");
            ((TextView) v.findViewById(R.id.TomorrowTempMin)).setText("Min: " + Double.toString(temperatureMin)+"°C");
            ((TextView) v.findViewById(R.id.TomorrowWind)).setText(Double.toString(wind));
            ((TextView) v.findViewById(R.id.description)).setText(def);
            ((TextView) v.findViewById(R.id.TomorrowDate)).setText(formattedDate);

            new AsyncBitmapDownloader(icon, ((ImageView) v.findViewById(R.id.ImageWeatherTomorrow))).execute();

            switch (def) {
                case "Clouds":
                    v.setBackground(getResources().getDrawable(R.drawable.background));
                    break;
                case "Rain":
                case "Drizzle"    :
                    v.setBackground(getResources().getDrawable(R.drawable.rainy));
                    break;
                case  "Thunderstorm"   :
                    v.setBackground(getResources().getDrawable(R.drawable.thunderstorm));
                    break;
                case  "Clear"  :
                    v.setBackground(getResources().getDrawable(R.drawable.clear));
                    break;
            }

        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}