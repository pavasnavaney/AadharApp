package aadharapp.cloud.csc.aadharapp.Centers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import aadharapp.cloud.csc.aadharapp.R;
import aadharapp.cloud.csc.aadharapp.Utils.URLlist;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static aadharapp.cloud.csc.aadharapp.R.color.department;
import static android.R.attr.offset;
import static android.R.id.list;

public class Centers extends AppCompatActivity {

    String state_code , district_code , name;
    String final_res;
    TextView centertxt , centerdesc;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private ListView listview;
    ListAdapterForCenter adapter;
    private List<Centerpojo> centerlist =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);
        Intent i =getIntent();
        state_code = i.getStringExtra("state_code");
        district_code = i.getStringExtra("district_code");
        name = i.getStringExtra("service");
        String font = "fonts/miso.otf";
        Typeface tf = Typeface.createFromAsset(this.getAssets(),font);
        centertxt = (TextView)findViewById(R.id.centertext);
        centerdesc = (TextView)findViewById(R.id.centerdesc);
        centertxt.setTypeface(tf,Typeface.BOLD);
        centerdesc.setTypeface(tf,Typeface.BOLD_ITALIC);
        listview = (ListView) findViewById(R.id.list_view);
        ViewCompat.setNestedScrollingEnabled(listview,true);
        initCollapsingToolbar();
        centerlist = new ArrayList<Centerpojo>();
        Background_Centers back = new Background_Centers(Centers.this);
        back.execute();
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.center));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    public class Background_Centers extends AsyncTask<String,Void,String> {

        String final_res;
        String json_string;
        Context ctx;

        SweetAlertDialog sweetAlertDialog;

        public Background_Centers(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            sweetAlertDialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#BF360C"));
            sweetAlertDialog.setTitleText("Loading Centers...");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        @Override
        protected String doInBackground(String...params) {
            URLlist ur = new URLlist();
            String course_get_url = ur.cennter_list;
            try {
                URL url = new URL(course_get_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("state", "UTF-8") + "="
                        + URLEncoder.encode(state_code, "UTF-8") + "&"
                        + URLEncoder.encode("district", "UTF-8") + "="
                        + URLEncoder.encode(district_code, "UTF-8") + "&"
                        + URLEncoder.encode("service", "UTF-8") + "="
                        + URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
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
            } catch (MalformedURLException e) {
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
                final_res = result;
                jsonObject = new JSONObject(final_res);
                jsonArray = jsonObject.getJSONArray("result");
                int count = 0;
                while (count < jsonArray.length()) {
                    Centerpojo map=new Centerpojo();
                    JSONObject jo = jsonArray.getJSONObject(count);
                    map.setCenter_name(jo.getString("center_name"));
                    map.setAddress(jo.getString("address"));
                    map.setId(jo.getString("id"));
                    map.setUser_name(jo.getString("user_name"));
                    map.setService_provided(jo.getString("service_provided"));
                    map.setEmail(jo.getString("email"));
                    map.setTodate(jo.getString("todate"));
                    String date = jo.getString("date");
                    map.setDate(date);
                    map.setState_code(state_code);
                    map.setDistrict_code(district_code);
                    map.setService_code(name);
                    centerlist.add(map);
                    adapter = new ListAdapterForCenter(Centers.this,centerlist);
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
