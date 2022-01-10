package faiz.example1.cricketapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Model> modelList;
    private Context context;

    public MyAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model model =modelList.get(position);
        holder.team1Tv.setText(model.getTeam1());
        holder.team2Tv.setText(model.getTeam2());
        holder.matchTypeTv.setText(model.getMatchType());
        holder.matchStatusTv.setText(model.getMatchStatus());
        holder.dateTv.setText(model.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String matchId= model.getId();
                final String date= model.getDate();

                String options[]={"Quick Detail","Players List","Match Summary"};
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Choose Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {
                            Intent intent=new Intent(context,MatchDetailActivity.class);
                            intent.putExtra("match_id",matchId);
                            intent.putExtra("date",date);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if(which==1)
                        {
                            Intent intent=new Intent(context,PlayersActivity.class);
                            intent.putExtra("match_id",matchId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if(which==2)
                        {
                            Intent intent=new Intent(context,MatchSummaryActivity.class);
                            intent.putExtra("match_id",matchId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });

                builder.create().show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView team1Tv,team2Tv,matchStatusTv,matchTypeTv,dateTv;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            team1Tv=itemView.findViewById(R.id.team1Tv);
            team2Tv=itemView.findViewById(R.id.team2Tv);
            matchStatusTv=itemView.findViewById(R.id.matchStatusTv);
            matchTypeTv=itemView.findViewById(R.id.matchTypeTv);
            cardView=itemView.findViewById(R.id.cardView);
            dateTv=itemView.findViewById(R.id.dateTv);
        }
    }
}
