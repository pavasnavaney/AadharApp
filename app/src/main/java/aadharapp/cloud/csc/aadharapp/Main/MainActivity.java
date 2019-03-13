package aadharapp.cloud.csc.aadharapp.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import aadharapp.cloud.csc.aadharapp.R;
import aadharapp.cloud.csc.aadharapp.Utils.URLlist;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.media.CamcorderProfile.get;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView title , titledesc , statetext , disttext;
    LinearLayout mainsp , distly;
    private Spinner statelist;
    private Spinner districtlist;
    String code;
    String dcode;
    Button service;
    private ArrayList<State> stateArraylist;
    private ArrayList<District> districtArrayList;
    JSONObject jsonObject2;
    JSONArray jsonArray2;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Animation slide_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView)findViewById(R.id.scheduletext);
        titledesc =(TextView)findViewById(R.id.scheduledesc);
        statetext=(TextView)findViewById(R.id.statextext);
        mainsp=(LinearLayout)findViewById(R.id.mainspin);
        distly = (LinearLayout)findViewById(R.id.distspinner);
        disttext = (TextView)findViewById(R.id.disttext);
        service = (Button)findViewById(R.id.service);
        statelist = (Spinner)findViewById(R.id.statespin);
        statelist.setOnItemSelectedListener(this);
        districtlist=(Spinner)findViewById(R.id.distspin);
        districtlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dcode = districtArrayList.get(position).getCode().toString();
                if(dcode.equals("000"))
                {
                    service.setVisibility(View.INVISIBLE);
                }
                else
                {
                    service.setVisibility(View.VISIBLE);
                    service.startAnimation(slide_up);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        stateArraylist= new ArrayList<State>();
        districtArrayList = new ArrayList<District>();
        String font = "fonts/miso.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(),font);
        title.setTypeface(tf,Typeface.BOLD);
        titledesc.setTypeface(tf,Typeface.BOLD_ITALIC);
        disttext.setTypeface(tf,Typeface.BOLD);
        service.setTypeface(tf,Typeface.BOLD);
        service.setText("Select Service");
        statetext.setTypeface(tf,Typeface.BOLD);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slideup);
      //  final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
             //   R.anim.slidedown);

        mainsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   mainsp.startAnimation(slide_down);

            }
        });
        Background_State back = new Background_State(MainActivity.this);
        back.execute();

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , Services.class);
                i.putExtra("state_code",code);
                i.putExtra("district_code",dcode);
                startActivity(i);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        code = stateArraylist.get(position).getCode().toString();
        Toast.makeText(this,code,Toast.LENGTH_LONG).show();
        if(code.equals("00"))
        {
            distly.setVisibility(View.INVISIBLE);
            service.setVisibility(View.INVISIBLE);
        }
        else
        {
            Background_District back = new Background_District(MainActivity.this);
            back.execute();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class Background_State extends AsyncTask<String,Void,String> {

        String json_string;
        Context ctx;
        String final_string;
        SweetAlertDialog sweetAlertDialog;

        public Background_State(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            sweetAlertDialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
            sweetAlertDialog.setTitleText("Loading States...");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URLlist ur = new URLlist();
            String state_get_url = ur.state_list;
            try {
                URL url = new URL(state_get_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((json_string =bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException | ProtocolException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            sweetAlertDialog.dismissWithAnimation();
            try {
                final_string = result;
                jsonObject = new JSONObject(final_string);
                jsonArray = jsonObject.getJSONArray("state");
                int count = 0;
                State snull = new State("-- Select State --" , "00");
                stateArraylist.add(snull);
                while (count < jsonArray.length())
                {
                    JSONObject explore = jsonArray.getJSONObject(count);
                    State s = new State(explore.getString("state_name"),explore.getString("state_code"));
                    stateArraylist.add(s);
                    count++;
                }
                populate();
            } catch (Exception e) {
                Log.e("error",e.toString());
                sweetAlertDialog = new SweetAlertDialog(ctx,SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Some Network Error Occurred! Please Check Your Internet Connection Or Try Again Later!");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();
            }
        }
    }
    public void populate()
    {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < stateArraylist.size(); i++) {
            lables.add(stateArraylist.get(i).getState());
        }
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                R.layout.main_spinner,lables);
        adapter_state.setDropDownViewResource(R.layout.spinnerdropdown);
        statelist.setAdapter(adapter_state);
    }


    public class Background_District extends AsyncTask<String,Void,String> {

        String json_string2;
        Context ctx;
        String final_string2;
        SweetAlertDialog sweetAlertDialog;

        public Background_District(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            sweetAlertDialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
            sweetAlertDialog.setTitleText("Loading Districts...");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URLlist ur = new URLlist();
            String district_get_url = ur.district_list;
            try {
                URL url = new URL(district_get_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url
                        .openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("coder", "UTF-8") + "="
                        + URLEncoder.encode(code, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_string2 = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string2 + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException | ProtocolException | UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            sweetAlertDialog.dismissWithAnimation();
            try {
                final_string2 = result;
                jsonObject2 = new JSONObject(final_string2);
                jsonArray2 = jsonObject2.getJSONArray("district");
                int count = 0;
                districtArrayList.clear();
                District dnull = new District("-- Select District --" , "000");
                districtArrayList.add(dnull);
                while (count < jsonArray2.length())
                {
                    JSONObject explore = jsonArray2.getJSONObject(count);
                    District d = new District(explore.getString("district_name"),explore.getString("district_code"));
                    districtArrayList.add(d);
                    count++;
                }
                populate2();
            } catch (Exception e) {
                distly.setVisibility(View.INVISIBLE);
                service.setVisibility(View.INVISIBLE);
                Log.e("error",e.toString());
                sweetAlertDialog = new SweetAlertDialog(ctx,SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Some Network Error Occurred! Please Check Your Internet Connection Or Try Again Later!");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();
            }
        }
    }
    public void populate2()
    {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < districtArrayList.size(); i++) {
            lables.add(districtArrayList.get(i).getDistrict());
        }
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                R.layout.main_spinner,lables);
        adapter_state.setDropDownViewResource(R.layout.spinnerdropdown);
        districtlist.setAdapter(adapter_state);
        distly.setVisibility(View.VISIBLE);
        distly.startAnimation(slide_up);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


}
