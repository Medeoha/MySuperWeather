package com.example.mysuperweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View v;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private JSONObject j;
    private String mParam2;

    public DetailActivity( ) {
        // Required empty public constructor
    }
    public DetailActivity(JSONObject myJsonData) {
        // Required empty public constructor
        j = myJsonData;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailActivity newInstance(String param1, String param2) {
        DetailActivity fragment = new DetailActivity();
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
        v = inflater.inflate(R.layout.fragment_detail_activity, container, false);
        Bundle test = getArguments();
        ;

        ((TextView) v.findViewById(R.id.Detailtemp)).setText("Actual temp = "+test.getString("temperature")+"°C");
        ((TextView) v.findViewById(R.id.temp_min)).setText("Min temp = "+test.getString("temperatureMin")+"°C");

        ((TextView) v.findViewById(R.id.temp_max)).setText("Max temp = " + test.getString("temperatureMax")+"°C");

        ((TextView) v.findViewById(R.id.temp_fell_like)).setText(" Temperature felt = "+test.getString("temperatureFeel")+"°C");

        ((TextView) v.findViewById(R.id.sunrise)).setText(test.getString("sunrise"));
        ((TextView) v.findViewById(R.id.sunset)).setText(test.getString("sunset"));


        Button button = (Button) v.findViewById(R.id.retour_btn);
        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) // Permet  de retourner sur le fragment de la météo d'aujourd'hui en lui transmettant des coordonnées géographiques
            {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                TodayFragment retour = new TodayFragment();
                Bundle backToToday = new  Bundle();
                Bundle receiver = getArguments();
                backToToday.putString("latitude",receiver.getString("latitude"));
                backToToday.putString("longitude",receiver.getString("longitude"));
                v.findViewById(R.id.retour_btn).setVisibility(View.INVISIBLE);
                retour.setArguments(backToToday);
                ft.replace(R.id.todaydetail,retour);
                ft.show(retour);
                ft.commit();
            }
        });

        return v;
    }


}