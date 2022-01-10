package faiz.example1.cricketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MatchSummaryActivity extends AppCompatActivity {

    private String url="https://cricapi.com/api/fantasySummary/?apikey=%22QYgnYshn71R9M8MoVWhVIVfCgDi1&&uniqueid=";

    TextView fieldT2DetailTv,fieldT2Tv,fieldT1Tv,fieldT1DetailTv,
            bowlT2DetailTv,bowlT2Tv,bowlT1Tv,bowlT1DetailTv,
            batT2DetailTv,batT2Tv,batT1Tv,batT1DetailTv,
            otherResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);

        Intent intent=getIntent();
        String uniqueId=intent.getStringExtra("match_id");
        url=url+uniqueId;

        fieldT1DetailTv=findViewById(R.id.fieldT1DetailTv);
        fieldT1Tv=findViewById(R.id.fieldT1Tv);
        fieldT2Tv=findViewById(R.id.fieldT2Tv);
        fieldT2DetailTv=findViewById(R.id.fieldT2DetailTv);

        bowlT1DetailTv=findViewById(R.id.bowlT1DetailTv);
        bowlT1Tv=findViewById(R.id.bowlT1Tv);
        bowlT2Tv=findViewById(R.id.bowlT2Tv);
        bowlT2DetailTv=findViewById(R.id.bowlT2DetailTv);

        batT1DetailTv=findViewById(R.id.batT1DetailTv);
        batT1Tv=findViewById(R.id.batT1Tv);
        batT2Tv=findViewById(R.id.batT2Tv);
        batT2DetailTv=findViewById(R.id.batT2DetailTv);

        otherResults=findViewById(R.id.otherResultsTv);

        loadData();
    }

    private void loadData() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject dataJObject=jsonObject.getJSONObject("data");

                    JSONArray fieldJArray=dataJObject.getJSONArray("fielding");
                    JSONArray bowlJArray=dataJObject.getJSONArray("bowling");
                    JSONArray batJArray=dataJObject.getJSONArray("batting");

                    JSONObject field0=fieldJArray.getJSONObject(0);
                    JSONObject field1=fieldJArray.getJSONObject(1);

                    String field1Title=field0.getString("title");
                    String field2Title=field1.getString("title");
                    JSONArray field1ScoresJArray=field0.getJSONArray("scores");
                    JSONArray field2ScoresJArray=field1.getJSONArray("scores");

                    fieldT1Tv.setText(field1Title);
                    for(int i=0;i<field1ScoresJArray.length();i++)
                    {
                        String name=field1ScoresJArray.getJSONObject(i).getString("name");
                        String bowled=field1ScoresJArray.getJSONObject(i).getString("bowled");
                        String catcch=field1ScoresJArray.getJSONObject(i).getString("catch");
                        String lbw=field1ScoresJArray.getJSONObject(i).getString("lbw");
                        String runout=field1ScoresJArray.getJSONObject(i).getString("runout");
                        String stumped=field1ScoresJArray.getJSONObject(i).getString("stumped");

                        fieldT1DetailTv.append("Name: "+name
                        +"\nBowled: "+bowled
                        +"\nCatch: "+catcch
                        +"\nLBW: "+lbw
                        +"\nRunout: "+runout
                        +"\nStumped: "+stumped+"\n\n");
                    }

                    fieldT2Tv.setText(field2Title);
                    for(int i=0;i<field2ScoresJArray.length();i++)
                    {
                        String name=field2ScoresJArray.getJSONObject(i).getString("name");
                        String bowled=field2ScoresJArray.getJSONObject(i).getString("bowled");
                        String catcch=field2ScoresJArray.getJSONObject(i).getString("catch");
                        String lbw=field2ScoresJArray.getJSONObject(i).getString("lbw");
                        String runout=field2ScoresJArray.getJSONObject(i).getString("runout");
                        String stumped=field2ScoresJArray.getJSONObject(i).getString("stumped");

                        fieldT2DetailTv.append("Name: "+name
                                +"\nBowled: "+bowled
                                +"\nCatch: "+catcch
                                +"\nLBW: "+lbw
                                +"\nRunout: "+runout
                                +"\nStumped: "+stumped+"\n\n");
                    }

                    JSONObject bowl0=bowlJArray.getJSONObject(0);
                    JSONObject bowl1=bowlJArray.getJSONObject(1);

                    String bowl1Title=bowl0.getString("title");
                    String bowl2Title=bowl1.getString("title");
                    JSONArray bowl1ScoresJArray=bowl0.getJSONArray("scores");
                    JSONArray bowl2ScoresJArray=bowl1.getJSONArray("scores");

                    bowlT1Tv.setText(bowl1Title);
                    for(int i=0;i<bowl1ScoresJArray.length();i++)
                    {
                        String bowlerName=bowl1ScoresJArray.getJSONObject(i).getString("bowler");
                        String overs=bowl1ScoresJArray.getJSONObject(i).getString("O");
                        String wickets=bowl1ScoresJArray.getJSONObject(i).getString("W");
                        String runs=bowl1ScoresJArray.getJSONObject(i).getString("R");
                        String zeroes=bowl1ScoresJArray.getJSONObject(i).getString("0s");
                        String fours=bowl1ScoresJArray.getJSONObject(i).getString("4s");
                        String sixes=bowl1ScoresJArray.getJSONObject(i).getString("6s");
                        String m=bowl1ScoresJArray.getJSONObject(i).getString("M");
                        String econ=bowl1ScoresJArray.getJSONObject(i).getString("Econ");

                        bowlT1DetailTv.append("Name: "+bowlerName
                        +"\nOvers: "+overs
                        +"\nWickets: "+wickets
                        +"\nRuns: "+runs
                        +"\n0s: "+zeroes
                        +"\n4s: "+fours
                        +"\n6s: "+sixes
                        +"\nM: "+m
                        +"\nEcon: "+econ
                        +"\n\n");
                    }

                    bowlT2Tv.setText(bowl2Title);
                    for(int i=0;i<bowl2ScoresJArray.length();i++)
                    {
                        String bowlerName=bowl2ScoresJArray.getJSONObject(i).getString("bowler");
                        String overs=bowl2ScoresJArray.getJSONObject(i).getString("O");
                        String wickets=bowl2ScoresJArray.getJSONObject(i).getString("W");
                        String runs=bowl2ScoresJArray.getJSONObject(i).getString("R");
                        String zeroes=bowl2ScoresJArray.getJSONObject(i).getString("0s");
                        String fours=bowl2ScoresJArray.getJSONObject(i).getString("4s");
                        String sixes=bowl2ScoresJArray.getJSONObject(i).getString("6s");
                        String m=bowl2ScoresJArray.getJSONObject(i).getString("M");
                        String econ=bowl2ScoresJArray.getJSONObject(i).getString("Econ");

                        bowlT2DetailTv.append("Name: "+bowlerName
                                +"\nOvers: "+overs
                                +"\nWickets: "+wickets
                                +"\nRuns: "+runs
                                +"\n0s: "+zeroes
                                +"\n4s: "+fours
                                +"\n6s: "+sixes
                                +"\nM: "+m
                                +"\nEcon: "+econ
                                +"\n\n");
                    }

                    JSONObject bat0=batJArray.getJSONObject(0);
                    JSONObject bat1=batJArray.getJSONObject(1);

                    String bat1Title=bat0.getString("title");
                    String bat2Title=bat1.getString("title");
                    JSONArray bat1ScoresJArray=bat0.getJSONArray("scores");
                    JSONArray bat2ScoresJArray=bat1.getJSONArray("scores");

                    batT1Tv.setText(bat1Title);
                    for(int i=0;i<bat1ScoresJArray.length();i++)
                    {
                        String batsman=bat1ScoresJArray.getJSONObject(i).getString("batsman");
                        String balls=bat1ScoresJArray.getJSONObject(i).getString("B");
                        String runs=bat1ScoresJArray.getJSONObject(i).getString("R");
                        String fours=bat1ScoresJArray.getJSONObject(i).getString("4s");
                        String sixes=bat1ScoresJArray.getJSONObject(i).getString("6s");
                        String strikeRate=bat1ScoresJArray.getJSONObject(i).getString("SR");
                        String dismissalInfo=bat1ScoresJArray.getJSONObject(i).getString("dismissal-info");
                        String dismissal="",dismissedBy="";
                        try{
                            dismissal=bat1ScoresJArray.getJSONObject(i).getString("dismissal");
                            dismissedBy=bat1ScoresJArray.getJSONObject(i).getJSONObject("dismissal-by").getString("name");
                        }
                        catch (Exception e)
                        {

                        }
                        batT1Tv.setText(bat1Title);
                        batT1DetailTv.append("Batsman: "+batsman
                        +"\nBalls: "+balls
                        +"\nRuns: "+runs
                        +"\n4s: "+fours
                        +"\n6s: "+sixes
                        +"\nSR: "+strikeRate
                        +"\nDismissal: "+dismissal
                        +"\nDismissal Info: "+dismissalInfo
                        +"\nDismissed By: "+dismissedBy
                        +"\n\n");
                    }

                    for(int i=0;i<bat2ScoresJArray.length();i++)
                    {
                        String batsman=bat2ScoresJArray.getJSONObject(i).getString("batsman");
                        String balls=bat2ScoresJArray.getJSONObject(i).getString("B");
                        String runs=bat2ScoresJArray.getJSONObject(i).getString("R");
                        String fours=bat2ScoresJArray.getJSONObject(i).getString("4s");
                        String sixes=bat2ScoresJArray.getJSONObject(i).getString("6s");
                        String strikeRate=bat2ScoresJArray.getJSONObject(i).getString("SR");
                        String dismissalInfo=bat2ScoresJArray.getJSONObject(i).getString("dismissal-info");
                        String dismissal="",dismissedBy="";
                        try{
                            dismissal=bat2ScoresJArray.getJSONObject(i).getString("dismissal");
                            dismissedBy=bat2ScoresJArray.getJSONObject(i).getJSONObject("dismissal-by").getString("name");
                        }
                        catch (Exception e)
                        {

                        }
                        batT2Tv.setText(bat2Title);
                        batT1DetailTv.append("Batsman: "+batsman
                                +"\nBalls: "+balls
                                +"\nRuns: "+runs
                                +"\n4s: "+fours
                                +"\n6s: "+sixes
                                +"\nSR: "+strikeRate
                                +"\nDismissal: "+dismissal
                                +"\nDismissal Info: "+dismissalInfo
                                +"\nDismissed By: "+dismissedBy
                                +"\n\n");
                    }

                    String manOfTheMatch="",tossWinner="",winnerTeam="";
                    try {
                        manOfTheMatch=dataJObject.getJSONObject("man-of-the-match").getString("name");
                    }
                    catch (Exception e)
                    {

                    }
                    try {
                        tossWinner=dataJObject.getString("toss_winner_team");
                    }
                    catch (Exception e)
                    {

                    }
                    try {
                        winnerTeam=dataJObject.getString("winner_team");
                    }
                    catch (Exception e)
                    {

                    }

                    otherResults.setText("Toss Winner: "+tossWinner
                    +"\nWinner: "+winnerTeam
                    +"\nMan of the Match: "+manOfTheMatch);

                }
                catch (Exception e)
                {
                    Toast.makeText(MatchSummaryActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchSummaryActivity.this, "Error:"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
