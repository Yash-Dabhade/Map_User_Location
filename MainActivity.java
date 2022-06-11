package mad.practicals.mapservice;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker myLocatMarker;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        buildGoogleApiClient();
        mapFragment.getMapAsync(googleMap -> {
            map = googleMap;
            mGoogleApiClient.connect();
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        System.out.println("ABC buildGoogleApiClient map was invoked: ");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(Bundle arg0) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double lng = mLastLocation.getLongitude();
            double lat = mLastLocation.getLatitude();

            if (myLocatMarker != null) {
                myLocatMarker.remove();
            }
            LatLng ll = new LatLng(lat, lng);
            MarkerOptions markerOpt = new MarkerOptions().title("my location")
                    .position(ll);
            System.out.println("ABC onConnected map: " + lat + " ; " + lng);
            myLocatMarker = map.addMarker(markerOpt);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}