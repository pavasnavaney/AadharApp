package aadharapp.cloud.csc.aadharapp.Centers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aadharapp.cloud.csc.aadharapp.Datetime.DatePick;
import aadharapp.cloud.csc.aadharapp.R;


class ListAdapterForCenter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Centerpojo> centerlist = null;
    private ArrayList<Centerpojo> arraylist;
    String servicecsv;
    List<String> items;



    public ListAdapterForCenter(Context context, List<Centerpojo> centerlist) {
        this.context = context;
        this.centerlist = centerlist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Centerpojo>();
        this.arraylist.addAll(centerlist);
    }

    public class ViewHolder {
        TextView centername;
        TextView address;
        FlexboxLayout flexboxLayout;
        TextView book;
        ProgressBar progressBar;
    }


    @Override
    public int getCount() {
        return centerlist.size();
    }

    @Override
    public Object getItem(int position) {
        return centerlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        String font = "fonts/miso.otf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(),font);
        final ViewHolder holder;
        if(view==null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.center_listitem, null);
            holder.centername = (TextView) view.findViewById(R.id.centername);
            holder.address = (TextView) view.findViewById(R.id.address);
            holder.book = (TextView)view.findViewById(R.id.book);
            holder.flexboxLayout = (FlexboxLayout)view.findViewById(R.id.flexlayout);
            servicecsv = centerlist.get(position).getService_provided();
            items = Arrays.asList(servicecsv.split("\\s*,\\s*"));

            for (int i=0; i<items.size(); i++)
            {
                TextView tv = new TextView(context);
                tv.setText(items.get(i).toString());

                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(5, 5, 5, 0);
                tv.setLayoutParams(params);
                tv.setTextSize(16);
                tv.setTextColor(Color.WHITE);
                tv.setTypeface(tf,Typeface.BOLD);
                if(i%3==0)
                {
                    tv.setBackgroundResource(R.drawable.rounded_corner_green);
                }
                else if(i%3==1)
                {
                    tv.setBackgroundResource(R.drawable.rounded_corner_purple);
                }
                else if(i%3==2)
                {
                    tv.setBackgroundResource(R.drawable.rounded_corner_red);
                }
                holder.flexboxLayout.addView(tv);
            }
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

            holder.centername.setBackgroundColor(Color.parseColor("#795548"));
            holder.book.setBackgroundColor(Color.parseColor("#795548"));

        holder.centername.setTypeface(tf,Typeface.BOLD);
        String center = centerlist.get(position).getCenter_name().toUpperCase();
        holder.centername.setText(center);
        holder.address.setText(centerlist.get(position).getAddress());
        holder.address.setTypeface(tf,Typeface.BOLD);
        holder.book.setTypeface(tf,Typeface.BOLD);
        holder.book.setText("BOOK APPOINTMENT!");

      /*  view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Centers.class);
                i.putExtra("state_code", servicelist.get(position).getState_code());
                i.putExtra("district_code",servicelist.get(position).getDistrict_code());
                i.putExtra("service", servicelist.get(position).getName());
                context.startActivity(i);
                }
        });*/
      holder.book.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(context , DatePick.class);
              i.putExtra("state_code", centerlist.get(position).getState_code());
              i.putExtra("district_code", centerlist.get(position).getDistrict_code());
              i.putExtra("service_code", centerlist.get(position).getService_code());
              i.putExtra("id", centerlist.get(position).getId());
              i.putExtra("center_name", centerlist.get(position).getCenter_name());
              i.putExtra("user_name", centerlist.get(position).getUser_name());
              i.putExtra("state_code", centerlist.get(position).getState_code());
              i.putExtra("address", centerlist.get(position).getAddress());
              i.putExtra("email", centerlist.get(position).getEmail());
              i.putExtra("todate", centerlist.get(position).getTodate());
              i.putExtra("date", centerlist.get(position).getDate());
              context.startActivity(i);
          }
      });
        return view;
    }
}
