package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.adapters.RestaurantListAdapter;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.services.YelpService;
import com.epicodus.myrestaurants.util.OnRestaurantSelectedListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RestaurantListActivity extends AppCompatActivity  implements OnRestaurantSelectedListener {

    private Integer mPosition;
    String mSource;
    ArrayList<Restaurant> mRestaurants;


    public static final String TAG = RestaurantListActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private String mRecentAdress;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

//        getRestaurants(location);

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mRestaurants = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_RESTAURANTS));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);
                if (mPosition != null && mRestaurants != null) {
                    Intent newIntent = new Intent(this, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(mRestaurants));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(newIntent);
                }
            }

        }

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mRecentAdress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
//        if(mRecentAdress !=null){
//            getRestaurants(mRecentAdress);
//        }
//        Log.d("Shared Pref Location", mRecentAdress);

    }


//    private void getRestaurants(String location) {
//        final YelpService yelpService = new YelpService();
//        yelpService.findRestaurants(location, new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                mRestaurants = yelpService.processResults(response);
//
//                RestaurantListActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager =
//                                new LinearLayoutManager(RestaurantListActivity.this);
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                    }
//                });
//            }
//        });
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mPosition != null && mRestaurants != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_RESTAURANTS,Parcels.wrap(mRestaurants));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }
    }
    @Override
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source) {
        mPosition = position;
        mRestaurants = restaurants;
        mSource = source;
    }
}
