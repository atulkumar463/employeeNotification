package com.beadcore.employeenotification.RecyclerAdapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.DetailedAttendenceResponse;
import com.beadcore.employeenotification.Retrofit.SimpplifiedAttendence;
import com.beadcore.employeenotification.Views.DetailedAttendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAttendanceAdapter extends RecyclerView.Adapter<DetailedAttendanceAdapter.ViewHolder> {

    private List<DetailedAttendenceResponse.DataBean> attendanceObjects;
    private Context mContext;
    int lastPosition = -1;

    public DetailedAttendanceAdapter(DetailedAttendance context, List<DetailedAttendenceResponse.DataBean> dataBeans) {
        this.mContext = context;
        this.attendanceObjects = dataBeans;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailed_attendence_layout, parent, false);

        return new DetailedAttendanceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom
                : R.anim.dwn_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition=position;



        holder.device.setText(attendanceObjects.get(position).getDevice_name());



        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
        final Date dateObj;
        try {
            dateObj = sdf.parse(attendanceObjects.get(position).getMark_time());
            holder.marktime.setText(new SimpleDateFormat("K:mm a").format(dateObj));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        holder.department.setText(attendanceObjects.get(position).getDepartment_name());
        holder.location.setText(getLocality(attendanceObjects.get(position).getLatitude(),attendanceObjects.get(position).getLongitude()));



    }

    @Override
    public int getItemCount() {
        return attendanceObjects.size();
    }

    public String getLocality(String lat,String lon){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mContext, Locale.getDefault());

        try{
            addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            return city;
        }catch (Exception e){
            Log.i("SDSD","Error : "+e.getMessage());
            e.printStackTrace();
        }
        return "N/A";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView device, marktime,department,location;
         ViewHolder(@NonNull View view) {
            super(view);

            device = (TextView) view.findViewById(R.id.tvdevice);
            marktime = (TextView) view.findViewById(R.id.tvmarkTime);
            department = (TextView) view.findViewById(R.id.tvDepartment);
            location = (TextView) view.findViewById(R.id.tvLocation);

        }
    }
}