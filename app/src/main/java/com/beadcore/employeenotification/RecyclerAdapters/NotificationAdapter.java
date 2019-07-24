package com.beadcore.employeenotification.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.SimpplifiedAttendence;
import com.beadcore.employeenotification.Views.DetailedAttendance;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {


    private List<SimpplifiedAttendence.DataBean> attendanceObjects;
    private Context mContext;
    int lastPosition = -1;


    public NotificationAdapter(Context context, List<SimpplifiedAttendence.DataBean> attendanceObjects) {
        this.mContext = context;
        this.attendanceObjects = attendanceObjects;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simplified_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom
                : R.anim.dwn_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = formatter.parse(attendanceObjects.get(position).getDate());
            holder.date.setText(new SimpleDateFormat("dd-MMM-YYYY ").format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.date.setText(attendanceObjects.get(position).getDate());
        }

        try {

            holder.tvshiftTime.setText(new SimpleDateFormat("hh:mm aa")
                    .format(getTimeInformet(attendanceObjects.get(holder.getAdapterPosition()).getShift_in_time())) + " To " +
                    new SimpleDateFormat("hh:mm aa")
                            .format(getTimeInformet(attendanceObjects.get(holder.getAdapterPosition()).getShift_out_time())));
            holder.tvshiftTime.setText(new SimpleDateFormat("hh:mm aa")
                    .format(getTimeInformet(attendanceObjects.get(holder.getAdapterPosition()).getShift_out_time())));
            holder.tvActualTimeIn.setText(new SimpleDateFormat("hh:mm aa")
                    .format(getTimeInformet(attendanceObjects.get(holder.getAdapterPosition()).getIn_time())));
            holder.tvActualTimeOut.setText(new SimpleDateFormat("hh:mm aa")
                    .format(getTimeInformet(attendanceObjects.get(holder.getAdapterPosition()).getOut_time())));
        } catch (NullPointerException e) {
            Toast.makeText(mContext, "Somrthing wnet wrong", Toast.LENGTH_SHORT).show();
            holder.tvshiftTime.setText(attendanceObjects.get(holder.getAdapterPosition()).getShift_in_time() + " To " + attendanceObjects.get(holder.getAdapterPosition()).getShift_out_time());
            holder.tvActualTimeIn.setText(attendanceObjects.get(holder.getAdapterPosition()).getIn_time());
            holder.tvActualTimeOut.setText(attendanceObjects.get(holder.getAdapterPosition()).getOut_time());
        }

        holder.tvActualTime.setText(attendanceObjects.get(holder.getAdapterPosition()).getActual_work_time());
        holder.tvRemark.setText(attendanceObjects.get(holder.getAdapterPosition()).getRemark());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DetailedAttendance.class).
                        putExtra("date", attendanceObjects.get(holder.getAdapterPosition()).getDate()));
            }
        });

        try {
            Glide.with(mContext).load(ApiClient.BASE_URL +"/"+ attendanceObjects.get(holder.getAdapterPosition()).getIn_time_image())
                    .placeholder(R.drawable.profile_placeholder)
                    .error(R.drawable.profile_placeholder)
                    .into(holder.ivinImage);
            Glide.with(mContext).load(ApiClient.BASE_URL + "/"+attendanceObjects.get(holder.getAdapterPosition()).getOut_time_image())
                    .placeholder(R.drawable.profile_placeholder)
                    .error(R.drawable.profile_placeholder)
                    .into(holder.ivOutImage);
        } catch (NullPointerException e) {
            e.toString();
        }

    }

    public Date getTimeInformet(String time) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getItemCount() {
        return attendanceObjects.size();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvdate)
        TextView date;
        @BindView(R.id.tvshiftTime)
        TextView tvshiftTime;
        @BindView(R.id.tvActualTimeIn)
        TextView tvActualTimeIn;
        @BindView(R.id.ivinImage)
        ImageView ivinImage;
        @BindView(R.id.ivOutImage)
        ImageView ivOutImage;
        @BindView(R.id.tvActualTimeOut)
        TextView tvActualTimeOut;
        @BindView(R.id.tvRemark)
        TextView tvRemark;
        @BindView(R.id.tvActualTime)
        TextView tvActualTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}