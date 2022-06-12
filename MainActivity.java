package mad.practicals.mapservice;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.location.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.adnroid.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

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
