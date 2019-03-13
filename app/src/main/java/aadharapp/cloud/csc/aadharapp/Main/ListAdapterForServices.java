package aadharapp.cloud.csc.aadharapp.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import aadharapp.cloud.csc.aadharapp.Centers.Centers;
import aadharapp.cloud.csc.aadharapp.R;

import static android.R.attr.resource;


class ListAdapterForServices extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<ServiceList> servicelist = null;
    private ArrayList<ServiceList> arraylist;
    int prevpos=-2 , newpos=-1 , click=0 ,caser=0;


    public ListAdapterForServices(Context context, List<ServiceList> servicelist) {
        this.context = context;
        this.servicelist = servicelist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ServiceList>();
        this.arraylist.addAll(servicelist);
    }

    public class ViewHolder {
        ImageView check;
        ImageView photo;
        TextView name;
        ProgressBar progressBar;
    }


    @Override
    public int getCount() {
        return servicelist.size();
    }

    @Override
    public Object getItem(int position) {
        return servicelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if(view==null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.service_layout, null);
            holder.photo = (ImageView)view.findViewById(R.id.photo);
            holder.check = (ImageView)view.findViewById(R.id.check);
            holder.name = (TextView)view.findViewById(R.id.service_name);
            holder.progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        int colorred = Color.parseColor("#D50000");
        int colorpurple = Color.parseColor("#9C27B0");
        int colorindigo = Color.parseColor("#1A237E");
        int colorgreen = Color.parseColor("#1B5E20");
        int colorbrown = Color.parseColor("#3E2723");
        holder.name.setTextColor(colorgreen);
        String font = "fonts/miso.otf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(),font);
        holder.name.setTypeface(tf,Typeface.BOLD);
        holder.name.setText(servicelist.get(position).getName());


        Glide.with(context).load(servicelist.get(position).getIcon()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
            {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.photo);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Centers.class);
                i.putExtra("state_code", servicelist.get(position).getState_code());
                i.putExtra("district_code",servicelist.get(position).getDistrict_code());
                i.putExtra("service", servicelist.get(position).getId());
                context.startActivity(i);
                }
        });
        return view;
    }
}
