package com.example.msi.zpi_interfejs;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Trasa extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private CustomLocationProvider provider;

    //Fragments
    private Strona mainFragment;
    private MapFragment mapFragment;
    private Postoje poisListFragment;
    private PlaceFinder finder;
    private Handler handler = new Handler();

    private int[] tabIcons = {
            R.mipmap.ic_local_shipping_white_24dp,
            R.mipmap.ic_schedule_white_24dp,
            R.mipmap.ic_local_parking_white_24dp,
            R.mipmap.ic_map_white_24dp
    };

    Runnable placesUpdate = new Runnable() {
        @Override
        public void run() {
            if(mapFragment != null && mapFragment.provider != null && mapFragment.provider.getLastLocation() != null) {
                finder.updatePlaces(mapFragment.provider.getLastLocation());
                //Referesh POIs every minute
                handler.postDelayed(placesUpdate, 60000);
            }
            else
                handler.postDelayed(placesUpdate, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasa);

        finder = new PlaceFinder();
        finder.addOnLoadListener(new OnPlacesLoadedListener() {
            @Override
            public void onLoad() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //Add new POI markers
                        mapFragment.updatePOIs(finder.currentPlaces);
                        //Update POIs lists
                        poisListFragment.updatePOIs(finder.currentPlaces);
                        mainFragment.updatePOIs(finder.currentPlaces.subList(0, 3));
                        Toast.makeText(getApplicationContext(), "Updated POIs markers", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Thread placesThread = new Thread(placesUpdate);
        placesThread.start();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        boolean isGPSEnabled = ((LocationManager)getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!isGPSEnabled) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Gps is turned off. Change your settings and try again.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 3)
                    mapFragment.zoomOncurrentLocation();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainFragment = new Strona();
        adapter.addFragment(mainFragment, "Main");
        adapter.addFragment(new Temp(), "Aktywności");
        poisListFragment = new Postoje();
        adapter.addFragment(poisListFragment, "Postój");
        mapFragment = new MapFragment();
        adapter.addFragment(mapFragment, "Mapa");
        viewPager.setAdapter(adapter);

        //Prevent from destroying/reloading fragments
        viewPager.setOffscreenPageLimit(4);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //return mFragmentTitleList.get(position);
            return null;
        }
    }
}
