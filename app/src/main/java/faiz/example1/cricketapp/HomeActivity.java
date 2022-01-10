package faiz.example1.cricketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button signout;

    private RecyclerView mRecyclerView;

    private String url="https://cricapi.com/api/matches?apikey=QYgnYshn71R9M8MoVWhVIVfCgDi1";

    private RecyclerView.Adapter mAdapter;
    private List<Model> modelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        auth = FirebaseAuth.getInstance();
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        signout=findViewById(R.id.signout);

        modelList=new ArrayList<>();

        loadUrlData();

    }

    private void loadUrlData() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest  stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                signout.setVisibility(View.VISIBLE);
                pd.dismiss();

                try {
                    JSONArray jsonArray=new JSONObject(response).getJSONArray("matches");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        try{
                            String uniqueId=jsonArray.getJSONObject(i).getString("unique_id");
                            String team1=jsonArray.getJSONObject(i).getString("team-1");
                            String team2=jsonArray.getJSONObject(i).getString("team-2");
                            String matchType=jsonArray.getJSONObject(i).getString("type");
                            String matchStatus=jsonArray.getJSONObject(i).getString("matchStarted");

                            if(matchStatus.equals("true"))
                            {
                                matchStatus="Match Started";
                            }
                            else
                            {
                                matchStatus="Match not started";
                            }

                            String dateTimeGMT=jsonArray.getJSONObject(i).getString("dateTimeGMT");
                            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            format1.setTimeZone(TimeZone.getTimeZone(dateTimeGMT));
                            Date date=format1.parse(dateTimeGMT);
                            SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String dateTime=format2.format(date);

                            Model model=new Model(uniqueId , team1 , team2 , matchType , matchStatus , dateTime);
                            modelList.add(model);

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    mAdapter =new MyAdapter(modelList,getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);

                }
                catch (Exception e)
                {
                    Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void signout(View view)
    {
        auth.signOut();
        Intent i=new Intent(HomeActivity.this,MainActivity.class);
        startActivity(i);
    }

}
