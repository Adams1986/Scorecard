package com.github.xb10.scorecard;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.github.xb10.scorecard.model.Course;
import com.github.xb10.scorecard.model.CourseMetaData;
import com.github.xb10.scorecard.model.Scorecard;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;


public class DistanceMeasurerActivity extends Activity{

    private LatLng[] startPosHole;
    private LatLng[] perspectivePosHole;
    private LatLng[] endPosHole;

    private GoogleMap googleMap;
    private TextView meterText;
    private float[] distanceToHole;
    private float[] distanceFromHole;
    private Marker markerStart;
    private Marker markerDuring;
    private Marker markerEnd;
    private Polyline line;
    private Polyline lineToCurrPosition;
    private int holeNumber;
    private Scorecard scorecard;
    private float[] bearingOfMap;
    private float[] zoomOnMap;
    private CameraPosition startingCamPos;
    private TextView holeText;
    private TextView meterToPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_measurer);

        try {

            if (googleMap == null) {

                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            googleMap.setMyLocationEnabled(true);

            googleMap.setTrafficEnabled(false);


            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.getUiSettings().setMapToolbarEnabled(false);

            setPositionOnMap();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setPositionOnMap() {

        getResourcesForMap();

        startingCamPos = CameraPosition.fromLatLngZoom(perspectivePosHole[holeNumber], zoomOnMap[holeNumber]);

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder(startingCamPos).bearing(bearingOfMap[holeNumber]).build()));


        distanceToHole = new float[1];
        distanceToHole[0] = 0;

        distanceFromHole = new float[1];
        distanceFromHole[0] = 0;

        BitmapDescriptor flag = BitmapDescriptorFactory.fromResource(R.drawable.golf_flag);

        markerStart = googleMap.addMarker(new MarkerOptions().position(startPosHole[holeNumber])
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        markerEnd = googleMap.addMarker(new MarkerOptions().position(endPosHole[holeNumber]).icon(flag));

                /*Måde at få reelle længde mellem start og slutpunkterne.
                Location.distanceBetween(
                        startPosHole[i].latitude, startPosHole[i].longitude,
                        endPosHole[i].latitude, endPosHole[i].longitude, distanceToHole);

                meterText.setText(String.format("%.0f", distanceToHole[0]) + getString(R.string.meter_text_string));*/

        meterText.setText(String.format("%d", scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getLength()) + getString(R.string.meters_text_string));
        meterToPos.setText("");
        holeText.setText(getString(R.string.text_scorecard_hole)+" "+ scorecard.getClub().getCourses().get(0).getHoles().get(holeNumber).getNumber());

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                addMarkerAndLine(latLng);


                meterText.setText(String.format("%.0f", distanceToHole[0]) + getString(R.string.meters_text_string));
                meterToPos.setText(String.format("%.0f", distanceFromHole[0]) + getString(R.string.meters_text_string));

            }

        });

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                if (googleMap.getMyLocation() != null) {
                    LatLng myPos = new LatLng(
                            googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());

                    Location.distanceBetween(
                            myPos.latitude, myPos.longitude,
                            endPosHole[holeNumber].latitude, endPosHole[holeNumber].longitude, distanceToHole);

                    addMarkerAndLine(myPos);

                    meterText.setText(String.format("%.0f ", distanceToHole[0]) + getString(R.string.meters_text_string));
                    meterToPos.setText(String.format("%.0f ", distanceFromHole[0]) + getString(R.string.meters_text_string));

                } else
                    Toast.makeText(DistanceMeasurerActivity.this, "Lokation ikke fundet, prøv igen", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    public void onResetMap(View view) {

        moveCameraView(holeNumber);

    }

    private void moveCameraView(int hole) {

        startingCamPos = CameraPosition.fromLatLngZoom(perspectivePosHole[hole], zoomOnMap[hole]);

        markerStart.setPosition(startPosHole[hole]);
        markerEnd.setPosition(endPosHole[hole]);

        Location.distanceBetween(
                startPosHole[hole].latitude, startPosHole[hole].longitude,
                endPosHole[hole].latitude, endPosHole[hole].longitude, distanceToHole);

        Location.distanceBetween(
                startPosHole[hole].latitude, startPosHole[hole].longitude,
                endPosHole[hole].latitude, endPosHole[hole].longitude, distanceFromHole);


        meterText.setText(String.format("%d", scorecard.getClub().getCourses().get(0).getHoles().get(hole).getLength()) + getString(R.string.meters_text_string));
        meterToPos.setText("");
        holeText.setText(getString(R.string.text_scorecard_hole)+" "+ scorecard.getClub().getCourses().get(0).getHoles().get(hole).getNumber());
        //Reelle længde mellem start og slutpunkterne
        //meterText.setText(String.format("%.0f", distanceToHole[0]) + getString(R.string.meter_text_string));
        removeMarkerAndLine();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder(startingCamPos).bearing(bearingOfMap[hole]).build()));
    }


    private void addMarkerAndLine(LatLng latLng){
        removeMarkerAndLine();
        markerDuring = googleMap.addMarker(
                new MarkerOptions().position(latLng)
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ball)).anchor(0.45f, 0.6f));

        Location.distanceBetween(
                latLng.latitude, latLng.longitude, endPosHole[holeNumber].latitude, endPosHole[holeNumber].longitude, distanceToHole);

        Location.distanceBetween(
                latLng.latitude, latLng.longitude, startPosHole[holeNumber].latitude, startPosHole[holeNumber].longitude, distanceFromHole);


        line = googleMap.addPolyline(new PolylineOptions().add(latLng).add(endPosHole[holeNumber]).color(Color.WHITE));
        lineToCurrPosition = googleMap.addPolyline(new PolylineOptions().add(latLng).add(startPosHole[holeNumber]).color(Color.BLUE));
    }

    private void removeMarkerAndLine() {

        if (markerDuring != null && line != null){
            markerDuring.remove();
            line.remove();
            lineToCurrPosition.remove();
        }
    }

    private void getResourcesForMap(){

        holeNumber = getIntent().getIntExtra(ScorecardRecyclerAdapter.HOLE_SELECTED_KEY, 0);
        scorecard = (Scorecard) getIntent().getSerializableExtra(ScorecardRecyclerAdapter.SCORECARD_KEY);

        initMetaData(scorecard.getClub().getCourses().get(0));

        startPosHole = scorecard.getClub().getCourses().get(0).getMetaData().getStartLatLng();
        endPosHole = scorecard.getClub().getCourses().get(0).getMetaData().getEndLatLng();
        perspectivePosHole = scorecard.getClub().getCourses().get(0).getMetaData().getPerspectiveLatitudeAndLongitude();
        bearingOfMap = scorecard.getClub().getCourses().get(0).getMetaData().getBearingOfMap();
        zoomOnMap = scorecard.getClub().getCourses().get(0).getMetaData().getZoomOnMap();
        meterText = (TextView) findViewById(R.id.meters_to_pin_text_view);
        meterToPos = (TextView) findViewById(R.id.tee_to_position__text_view);
        holeText = (TextView) findViewById(R.id.hole_text_map_view);
    }

    public void onBackMap(View view) {

        if(holeNumber == 0){
            holeNumber = 18;
        }
        holeNumber--;
        moveCameraView(holeNumber);

    }

    public void onForwardMap(View view) {

        if(holeNumber == 17) {
            holeNumber = -1;
        }
        holeNumber++;
        moveCameraView(holeNumber);
    }


    private void initMetaData(Course course){

        //TODO: Perspective latlong!!!!!!!!
        CourseMetaData hk18Metadata = new CourseMetaData(new float[]{
                0f, -90f, 100f, 0f, 180f, -10f, -100f, 190f, 190f,
                180f, 0f, 180f, 0f, 200f, 200f, 30f, 90f, 120f},
                new float[]{
                        18f, 16.5f, 16.7f, 18f, 17f, 17.1f, 16.6f, 18.2f, 17f,
                        17f, 16.9f, 16.8f, 16.4f, 16.7f, 18.3f, 16.3f, 18.5f, 17.1f
                },
                new LatLng[]{
                        new LatLng(55.489252, 12.104942), new LatLng(55.490379, 12.104108), new LatLng(55.490778, 12.097419),
                        new LatLng(55.490882, 12.104269), new LatLng(55.492428, 12.105054), new LatLng(55.489797, 12.106511),
                        new LatLng(55.492435, 12.103754), new LatLng(55.491313, 12.097111), new LatLng(55.488979, 12.096060),
                        new LatLng(55.486118, 12.095676), new LatLng(55.483126, 12.093985), new LatLng(55.486615, 12.094512),
                        new LatLng(55.484519, 12.092482), new LatLng(55.488833, 12.094660), new LatLng(55.485873, 12.091394),
                        new LatLng(55.485519, 12.090043), new LatLng(55.489238, 12.093748), new LatLng(55.489681, 12.098958),
                },
                new LatLng[]{
                        new LatLng(55.490553, 12.104757), new LatLng(55.490444, 12.097302), new LatLng(55.490728, 12.103651),
                        new LatLng(55.492255, 12.104291), new LatLng(55.489439, 12.105393), new LatLng(55.492370, 12.105671),
                        new LatLng(55.491104, 12.097506), new LatLng(55.490097, 12.096691), new LatLng(55.486355, 12.095303),
                        new LatLng(55.483139, 12.094606), new LatLng(55.485895, 12.094973), new LatLng(55.483497, 12.093314),
                        new LatLng(55.488623, 12.095139), new LatLng(55.485694, 12.092076), new LatLng(55.484979, 12.090102),
                        new LatLng(55.489033, 12.094142), new LatLng(55.489511, 12.095471), new LatLng(55.489089, 12.104031),
                });

        course.setMetaData(hk18Metadata);
    }
}
