package com.example.mysuperweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.telecom.Call;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysuperweather.ui.main.AsyncWeatherJSONData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    public JSONObject j;
    private String mParam2;
    private View v;
    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
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
        Bundle localisation = getArguments();
        v = inflater.inflate(R.layout.fragment_today, container, false);
        try {
            new AsyncWeatherJSONData(this::FragmentJSON, localisation.getString("latitude"),localisation.getString("longitude")).execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Destroyed","Fragment détruit");
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void FragmentJSON(JSONObject j)
    {

        FragmentManager fm = getFragmentManager();
        Bundle send = getArguments();
        Button button = (Button) v.findViewById(R.id.details_btn);
        button.setOnClickListener(new MyLovelyOnClickListener(j, fm, send));

;
        this.j = j;
        try {
            String cityName = j.getString("name") + "\n"+j.getJSONArray("weather").getJSONObject(0).getString("main");
            double temperature = j.getJSONObject("main").getDouble("temp");
            long date_int = (long)j.getInt("dt")*1000;
            Date date = new Date(date_int);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE-dd-MM");
            String formattedDate = dateFormat.format(date);
            String def = j.getJSONArray("weather").getJSONObject(0).getString("main");
            double wind = j.getJSONObject("wind").getDouble("speed");
            String icon = j.getJSONArray("weather").getJSONObject(0).getString("icon");

            switch (j.getJSONArray("weather").getJSONObject(0).getString("main")) {//pour le changement de fond d'ecran
                case "Clouds":
                    v.setBackground(getResources().getDrawable(R.drawable.clouds));
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
                case "Snow" :
                    v.setBackground(getResources().getDrawable(R.drawable.neige));
                    break;
                default:
                    v.setBackground(getResources().getDrawable(R.drawable.brume));
                    break;
            }
            ((TextView) v.findViewById(R.id.description)).setText(cityName);
            ((TextView) v.findViewById(R.id.TodayTemp)).setText(Double.toString(temperature) +"°C");
            ((TextView) v.findViewById(R.id.TodayWind)).setText(Double.toString(wind));
            ((TextView) v.findViewById(R.id.DefToday)).setText(formattedDate);
            new AsyncBitmapDownloader(icon, ((ImageView) v.findViewById(R.id.ImageWeatherToday))).execute();



        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }



    }
}