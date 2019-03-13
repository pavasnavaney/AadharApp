package aadharapp.cloud.csc.aadharapp.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import aadharapp.cloud.csc.aadharapp.R;
import aadharapp.cloud.csc.aadharapp.Utils.URLlist;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Services extends AppCompatActivity {

    String state_code,district_code;
    private ListView listview;
    TextView title , titledesc;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String photo = "https://government.000webhostapp.com/csc.png";
    ListAdapterForServices adapter;
    private List<ServiceList> servicelist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        Intent i = getIntent();
        state_code = i.getStringExtra("state_code");
        district_code = i.getStringExtra("district_code");
        title = (TextView)findViewById(R.id.servicetext);
        titledesc =(TextView)findViewById(R.id.servicedesc);
        String font = "fonts/miso.otf";
        Typeface tf = Typeface.createFromAsset(getAssets(),font);
        title.setTypeface(tf,Typeface.BOLD);
        titledesc.setTypeface(tf,Typeface.BOLD_ITALIC);
        listview = (ListView)findViewById(R.id.list_view);
        listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
        servicelist = new ArrayList<ServiceList>();
        Background_Service back = new Background_Service(Services.this);
        back.execute();
    }


    public class Background_Service extends AsyncTask<String,Void,String> {

        String json_string;
        Context ctx;
        String final_string;
        SweetAlertDialog sweetAlertDialog;

        public Background_Service(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            sweetAlertDialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#1B5E20"));
            sweetAlertDialog.setTitleText("Loading Services...");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            URLlist ur = new URLlist();
            String service_get_url = ur.service_list;
            try {
                URL url = new URL(service_get_url);
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
                jsonArray = jsonObject.getJSONArray("service");
                int count = 0;
                while (count < jsonArray.length())
                {
                    ServiceList map = new ServiceList();
                    JSONObject jo = jsonArray.getJSONObject(count);
                    map.setName((String) jo.get("name"));
                    map.setId((String) jo.get("id"));
                    photo = jo.getString("icon");
                    map.setIcon(photo);
                    map.setState_code(state_code);
                    map.setDistrict_code(district_code);
                    servicelist.add(map);
                    adapter = new ListAdapterForServices(Services.this,servicelist);
                    listview.setAdapter(adapter);
                    count++;
                }

            } catch (Exception e) {
                Log.e("error",e.toString());
                sweetAlertDialog = new SweetAlertDialog(ctx,SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Some Network Error Occurred! Please Check Your Internet Connection Or Try Again Later!");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();
            }
        }
    }

}
