package com.example.androiduber;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiduber.Common.Common;
import com.example.androiduber.Remote.IGoogleAPI;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustommerCall extends AppCompatActivity {

    TextView tvTime;
    TextView tvAddress;
    TextView tvDistance;

    IGoogleAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommer_call);

        tvTime = findViewById(R.id.txt_time);
        tvAddress = findViewById(R.id.txt_address);
        tvDistance = findViewById(R.id.txt_distance);

        mService = Common.getGoogleAPI();

        if(getIntent() != null){
            double lat = getIntent().getDoubleExtra("lat", -1.0);
            double lng = getIntent().getDoubleExtra("lng", -1.0);

            getDirection(lat, lng);
        }
    }

    private void getDirection(double lat, double lng) {


        String requestApi = null;

        try{
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=" + Common.mLastLocation.getLatitude()+ "," + Common.mLastLocation.getLongitude() + "&"+
                    "destination=" + lat +"," + lng + "&"+
                    "key=" + getResources().getString(R.string.google_maps_key);

//            requestApi="https://maps.googleapis.com/maps/api/directions/json?mode=driving&" +
//                    "transit_routing_preference=less_driving&origin="+Common.mLastLocation.getLatitude()+","+Common.mLastLocation.getLongitude()+"&" +
//                    "destination="+lat+","+lng+"&key="+getResources().getString(R.string.google_direction_api);

            Log.d("gg api", requestApi);
            mService.getPath(requestApi).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray routes = jsonObject.getJSONArray("routes");

                        JSONObject object = routes.getJSONObject(0);

                        JSONArray legs = object.getJSONArray("legs");

                        JSONObject legsObject = legs.getJSONObject(0);

                        JSONObject distance = legsObject.getJSONObject("distance");
                        tvDistance.setText(distance.getString("text"));

                        //get time
                        JSONObject time = legsObject.getJSONObject("duration");
                        tvTime.setText(time.getString("text"));

                        //get address
                        String address = legsObject.getString("end_address");
                        tvAddress.setText(address);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(CustommerCall.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
