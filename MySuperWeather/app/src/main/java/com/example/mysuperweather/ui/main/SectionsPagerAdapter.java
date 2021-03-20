package com.example.mysuperweather.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mysuperweather.ForecastWeather;
import com.example.mysuperweather.MainActivity;
import com.example.mysuperweather.R;
import com.example.mysuperweather.TodayFragment;
import com.example.mysuperweather.TomorrowFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};//nom des tables
    private  Context mContext;
    public FragmentManager pfm;
    Bundle locBundle;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        pfm = fm;
        locBundle = new Bundle();
        locBundle = null;
    }

    public SectionsPagerAdapter(Context context, FragmentManager fm, String longitude, String latitude) {//Constructeur de SectionsPagerAdapter qui va transferer la longitude et la latitude vers tous les fragments
        super(fm);
        mContext = context;
        pfm = fm;
        locBundle = new Bundle();
        locBundle.putString("longitude",longitude);
        locBundle.putString("latitude",latitude);
    }


    @Override
    public Fragment getItem(int position) { //En fonction dans laquel l'utilisateur se trouve le tableau va charger le fragment adéquate
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

      Fragment fragment = null;

      switch (position)
      {
          case 0:
              fragment = new TodayFragment();
              pfm.beginTransaction().detach(fragment).attach(fragment).commit();
              break;
          case 1:

              fragment = new TomorrowFragment();
              pfm.beginTransaction().detach(fragment).attach(fragment).commit();
              break;
          case 2:
              fragment = new ForecastWeather();
      }

          fragment.setArguments(locBundle);


      return fragment;//on retourne le fragment où l'utilisateur se trouve
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}