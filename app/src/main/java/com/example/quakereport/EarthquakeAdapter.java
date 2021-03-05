package com.example.quakereport;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeTextview = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeTextview.setText(getFormattedMagnitude(currentEarthquake.getmMagnitude()));

        GradientDrawable magnitudeBackground = (GradientDrawable) magnitudeTextview.getBackground();
        int magnitudeBackgroundColor = getMagnitudeBackgroundColor(currentEarthquake.getmMagnitude());
        magnitudeBackground.setColor(magnitudeBackgroundColor);

        String[] address = splitPlace(currentEarthquake.getmPlace());
        TextView directionTextview = (TextView) listItemView.findViewById(R.id.direction);
        directionTextview.setText(address[0]);

        TextView locationTextview = (TextView) listItemView.findViewById(R.id.location);
        locationTextview.setText(address[1]);

        TextView dateTextview = (TextView) listItemView.findViewById(R.id.date);
        dateTextview.setText(getFormattedDate(currentEarthquake.getmDate()));

        TextView timeTextview = (TextView) listItemView.findViewById(R.id.time);
        timeTextview.setText(getFormattedTime(currentEarthquake.getmDate()));

        return listItemView;
    }

    private String getFormattedMagnitude(double mag) {
        DecimalFormat formater = new DecimalFormat("0.0");
        return formater.format(mag);
    }

    private String[] splitPlace(String place) {
        String[] address = new String[2];
        if(place.contains("of")) {
            address = place.split(" of ");
            address[0] += " of";
        } else {
            address[0] = "Near the";
            address[1] = place;
        }
        return address;
    }

    private String getFormattedDate(long timeInMS) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormater.format(new Date(timeInMS));
    }

    private String getFormattedTime(long timeInMS) {
        SimpleDateFormat timeFormater = new SimpleDateFormat("h:mm a");
        return timeFormater.format(new Date(timeInMS));
    }

    private  int getMagnitudeBackgroundColor(double magnitude) {
        int backgroundColorId;
        int mag = (int) Math.floor(magnitude);
        switch (mag) {
            case 0:
            case 1:
                backgroundColorId = R.color.magnitude1;
                break;
            case 2:
                backgroundColorId = R.color.magnitude2;
                break;
            case 3:
                backgroundColorId = R.color.magnitude3;
                break;
            case 4:
                backgroundColorId = R.color.magnitude4;
                break;
            case 5:
                backgroundColorId = R.color.magnitude5;
                break;
            case 6:
                backgroundColorId = R.color.magnitude6;
                break;
            case 7:
                backgroundColorId = R.color.magnitude7;
                break;
            case 8:
                backgroundColorId = R.color.magnitude8;
                break;
            case 9:
                backgroundColorId = R.color.magnitude9;
                break;
            default:
                backgroundColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), backgroundColorId);
    }
}
