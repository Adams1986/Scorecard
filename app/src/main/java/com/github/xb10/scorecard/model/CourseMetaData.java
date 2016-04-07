package com.github.xb10.scorecard.model;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Class created as a convenience instead of putting it in the MySQL database. Should be broken down in a hole metadata table in later version.
 * The class contains metadata used for the Google Maps feature of the app to adjust the screen to contain the holes in a tweaked way.
 */
public class CourseMetaData implements Serializable {

    private float[] bearingOfMap;
    private float[] zoomOnMap;
    private LatLng[] startLatLng;
    private LatLng[] endLatLng;

    public CourseMetaData(float[] bearingOfMap, float[] zoomOnMap, LatLng[] startLatLng, LatLng[] endLatLng) {

        this.bearingOfMap = bearingOfMap;
        this.zoomOnMap = zoomOnMap;
        this.startLatLng = startLatLng;
        this.endLatLng = endLatLng;
    }

    public float[] getBearingOfMap() {
        return bearingOfMap;
    }

    public void setBearingOfMap(float[] bearingOfMap) {
        this.bearingOfMap = bearingOfMap;
    }

    public float[] getZoomOnMap() {
        return zoomOnMap;
    }

    public void setZoomOnMap(float[] zoomOnMap) {
        this.zoomOnMap = zoomOnMap;
    }

    public LatLng[] getStartLatLng() {
        return startLatLng;
    }

    public void setStartLatLng(LatLng[] startLatLng) {
        this.startLatLng = startLatLng;
    }

    public LatLng[] getEndLatLng() {
        return endLatLng;
    }

    public void setEndLatLng(LatLng[] endLatLng) {
        this.endLatLng = endLatLng;
    }

    public LatLng[] getPerspectiveLatitudeAndLongitude() {

        LatLng[] latLngs = new LatLng[18];

        for (int i = 0; i < latLngs.length; i++) {

            latLngs[i] = new LatLng(
                    (getStartLatLng()[i].latitude + getEndLatLng()[i].latitude) / 2,
                    (getStartLatLng()[i].longitude + getEndLatLng()[i].longitude) / 2);
        }
        return latLngs;
    }
}
