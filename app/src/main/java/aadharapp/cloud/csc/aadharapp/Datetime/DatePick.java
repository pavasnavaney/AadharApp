package aadharapp.cloud.csc.aadharapp.Datetime;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;

import com.google.android.flexbox.FlexboxLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import aadharapp.cloud.csc.aadharapp.Main.MainActivity;
import aadharapp.cloud.csc.aadharapp.R;

import static aadharapp.cloud.csc.aadharapp.R.id.mon;
import static aadharapp.cloud.csc.aadharapp.R.id.textView;
import static android.os.Build.VERSION_CODES.M;

public class DatePick extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    String date , todate;
    TextView datetxt , montxt , select , morning , evening , afternoon;
    Calendar[] disabledDays;
    String day , month , year , yearcrop;
    SimpleDateFormat monthParse,monthDisplay;
    GridLayout mor ,aft , even;
   // RecyclerView rvmorning , rvafternoon , rvevening;
   // MorningRecyclerView adapter;
    int clicked=0;
    String[] slots = {"10:00 AM","10:10 AM", "10:20 AM" , "10:30 AM" , "10:40 AM" , "10:50 AM" , "11:00 AM"
    , "11:10 AM" , "11:20 AM" , "11:30 AM" , "11:40 AM" , "11:50 AM" , "12:00 PM" , "12:10 PM" , "12:20 PM" , "12:20 PM"
            , "12:30 PM" , "12:40 PM" , "12:50 PM" , "01:30 PM" , "01:40 PM" , "01:50 PM" , "02:00 PM" , "02:10 PM" , "02:20 PM"
            , "02:30 PM" ,"02:40 PM" , "02:50 PM" , "03:00 PM" , "03:10 PM" , "03:20 PM" , "03:30 PM" , "03:40 PM " , "03:50 PM"
            , "04:00 PM" , "04:10 PM" , "04:20 PM" , "04:30 PM" , "04:40 PM" , "04:50 PM" , "05:00 PM"};
   /* String[] afternoonslots = {"12:00 PM" , "12:10 PM" , "12:20 PM" , "12:20 PM"
            , "12:30 PM" , "12:40 PM" , "12:50 PM" , "01:30 PM" , "01:40 PM" , "01:50 PM" , "02:00 PM" , "02:10 PM" , "02:20 PM"
            , "02:30 PM" ,"02:40 PM" , "02:50 PM" , "03:00 PM" };
    String[] eveningslots = { "03:10 PM" , "03:20 PM" , "03:30 PM" , "03:40 PM " , "03:50 PM"
            , "04:00 PM" , "04:10 PM" , "04:20 PM" , "04:30 PM" , "04:40 PM" , "04:50 PM" , "05:00 PM"};*/
    int monthint , dayint , yearint;
    final TextView[] lister = new TextView[41];
    Calendar copiersun , copiersat ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);
        Calendar manual = Calendar.getInstance();
        Calendar saturday;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        String font = "fonts/miso.otf";
        Typeface tf = Typeface.createFromAsset(this.getAssets(),font);
        Calendar sunday;
        mor = (GridLayout)findViewById(R.id.morningslot);
        aft = (GridLayout)findViewById(R.id.afternoonslot);
        even = (GridLayout)findViewById(R.id.eveningslot);
        morning = (TextView)findViewById(R.id.morning);
        afternoon = (TextView)findViewById(R.id.afternoon);
        evening = (TextView)findViewById(R.id.evening);
        morning.setTypeface(tf,Typeface.BOLD);
        evening.setTypeface(tf,Typeface.BOLD);
        afternoon.setTypeface(tf,Typeface.BOLD);
        for (int i=0; i<lister.length; i++)
        {

            int sizer=150;
            GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
            layoutParams.setMargins(sizer*20/100,sizer*20/100,sizer*20/100,sizer*20/100);
            layoutParams.width=width/4;
            layoutParams.height=sizer;
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/4,100);
            lister[i] = new TextView(this);
            lister[i].setPadding(20,20,20,20);
            lister[i].setLayoutParams(layoutParams);
           // lister[i].setLayoutParams(params);
            lister[i].setGravity(Gravity.CENTER);
            lister[i].setTypeface(tf);
            lister[i].setTextColor(Color.parseColor("#3F51B5"));
            lister[i].setText(slots[i]);
            lister[i].setBackgroundResource(R.drawable.dividerdrawable);
        }
        for (int k=0; k<lister.length; k++)
        {
            if(k<12)
            {
                mor.addView(lister[k]);
            }
            else if(k>=12 && k<29)
            {
                aft.addView(lister[k]);
            }
            else {
                even.addView(lister[k]);
            }
        }
        for (int j=0; j<lister.length; j++)
        {
            lister[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DatePick.this,((TextView)v).getText().toString(),Toast.LENGTH_LONG).show();
                    for(int f=0; f<lister.length; f++)
                    {
                        lister[f].setBackgroundResource(R.drawable.dividerdrawable);
                        lister[f].setTextColor(Color.parseColor("#3F51B5"));
                    }
                    ((TextView)v).setBackgroundResource(R.drawable.dividerdrawableblue);
                    ((TextView)v).setTextColor(Color.WHITE);
                }
            });
        }
        /*rvmorning = (RecyclerView)findViewById(R.id.morningslot);
        rvafternoon = (RecyclerView)findViewById(R.id.afternoonslot);
        rvevening = (RecyclerView)findViewById(R.id.eveningslot);
        int noc = 3;
        rvmorning.setLayoutManager(new GridLayoutManager(this,noc));
        rvafternoon.setLayoutManager(new GridLayoutManager(this,noc));
        rvevening.setLayoutManager(new GridLayoutManager(this,noc));
        adapter = new MorningRecyclerView(this , morningslots);
        adapter.setClickListener(this);
        rvmorning.setAdapter(adapter);*/
        List<Calendar> weekends = new ArrayList<>();
        int weeks = 2;
        final Calendar cal1 = Calendar.getInstance();
        final Calendar copiersun = Calendar.getInstance();
        final Calendar copiersat = Calendar.getInstance();
        final Calendar copiersun1 = Calendar.getInstance();
        final Calendar copiersat1 = Calendar.getInstance();
        final Calendar satsun = Calendar.getInstance();
        final Calendar sunsat = Calendar.getInstance();
        final Calendar sunsat1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar calendar = Calendar.getInstance();
        Intent i = getIntent();
        datetxt = (TextView)findViewById(R.id.date);
        montxt = (TextView) findViewById(mon);
        select = (TextView)findViewById(R.id.selecttext);
        final Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
        final Calendar cal3 = Calendar.getInstance();
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MMM-yyyy");
        manual.set(Calendar.DAY_OF_WEEK , cal3.get(Calendar.DAY_OF_WEEK));
        date = i.getStringExtra("date");
        todate = i.getStringExtra("todate") ;
        day = date.substring(0,2);
        month = date.substring(3,6);
        yearcrop = date.substring(7,11);
        manual.set(Calendar.DAY_OF_YEAR,Integer.parseInt(day));
        year = date.substring(7,11);
      for (int in = 0; in < (weeks * 7) ; in = in + 7) {
            sunday = Calendar.getInstance();
            sunday.add(manual.DAY_OF_YEAR, (Calendar.SUNDAY - sunday.get(manual.DAY_OF_WEEK) + 7 + in));
            saturday = Calendar.getInstance();
            saturday.add(manual.DAY_OF_YEAR, (Calendar.SATURDAY - saturday.get(manual.DAY_OF_WEEK) + in));
            weekends.add(saturday);
            weekends.add(sunday);
        }
        try {
            cal1.setTime(sdf1.parse(date));// all done
            copiersun.setTime(sdf1.parse(date));
            copiersat.setTime(sdf1.parse(date));
            satsun.setTime(sdf1.parse(date));
            sunsat.setTime(sdf1.parse(date));
            sunsat1.setTime(sdf1.parse(date));
            copiersun1.setTime(sdf1.parse(date));
            copiersat1.setTime(sdf1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            cal2.setTime(sdf2.parse(todate));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            calendar.setTime(new SimpleDateFormat("MMM").parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Calendar[] restdays = {cal1,cal2};
        monthint = cal1.get(Calendar.MONTH)+1;
        copiersun.add(Calendar.DATE, -(copiersun.get(Calendar.DAY_OF_WEEK))+8);
        copiersat.add(Calendar.DATE, -(copiersat.get(Calendar.DAY_OF_WEEK))+7);
        copiersun1.add(Calendar.DATE, -(copiersun1.get(Calendar.DAY_OF_WEEK))+15);
        copiersat1.add(Calendar.DATE, -(copiersat1.get(Calendar.DAY_OF_WEEK))+14);
        if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY||cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY
                ||cal1.get(Calendar.DAY_OF_WEEK)+1==Calendar.SUNDAY)
        {
            weekends.add(cal1);
        }
        if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
        {
            sunsat.add(Calendar.DATE, -(copiersun.get(Calendar.DAY_OF_WEEK))+15);
            weekends.add(sunsat);
        }
        if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
        {
            satsun.add(Calendar.DATE, -(satsun.get(Calendar.DAY_OF_WEEK))+14);
            weekends.add(satsun);
        }
        if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
        {
            sunsat1.add(Calendar.DATE, -(sunsat1.get(Calendar.DAY_OF_WEEK))+15);
            weekends.add(sunsat1);
        }
        weekends.add(copiersun);
        weekends.add(copiersat);
        weekends.add(copiersun1);
        weekends.add(copiersat1);
        disabledDays = weekends.toArray(new Calendar[weekends.size()]);
        //String test = copier.get(Calendar.DATE)+"";
        //String test2 = copier.get(Calendar.DAY_OF_WEEK)+"";
        //Toast.makeText(DatePick.this,test,Toast.LENGTH_LONG).show();
        manual.set(Calendar.MONTH,monthint);
        manual.set(Calendar.YEAR,Integer.parseInt(yearcrop));
        dayint = Integer.parseInt(day);
        yearint = Integer.parseInt(year);
        select.setTypeface(tf,Typeface.BOLD);
        datetxt.setText(day);
        montxt.setText(month);

        montxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(DatePick.this , yearint , monthint , dayint);
                dpd.setMinDate(cal1);
                dpd.setMaxDate(cal2);
                dpd.setDisabledDays(disabledDays);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        for(int f=0; f<lister.length; f++)
        {
            lister[f].setBackgroundResource(R.drawable.dividerdrawable);
            lister[f].setTextColor(Color.parseColor("#3F51B5"));
        }
        String dater = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        monthint = monthOfYear+1;
        dayint=dayOfMonth;
        String dayset;
        yearint=year;
        if(dayint<10)
        {
            dayset = "0"+dayint;
        }
        else
        {
            dayset = dayint+"";
        }
        datetxt.setText(dayset);
        String monther = monthint+"";
        try {
            montxt.setText(formatMonth(monther).toUpperCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(DatePick.this,date,Toast.LENGTH_LONG).show();
    }
    public String formatMonth(String month) throws ParseException {
        monthParse = new SimpleDateFormat("MM");
        monthDisplay = new SimpleDateFormat("MMM");
        return monthDisplay.format(monthParse.parse(month));
    }


}
