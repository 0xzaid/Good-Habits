package com.example.goodhabits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.ViewHolder> {

    List<User> data = new ArrayList<>();

    public LeaderboardRecyclerViewAdapter() {
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LeaderboardRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.leaderboardIndexCard.setText(Integer.toString(position + 1));
        holder.leaderboardNameCard.setText("Name: " + data.get(position).getName());
        holder.leaderboardPointsCard.setText("Points: " +Integer.toString(data.get(position).getPoints()));
        holder.leaderboardLevelCard.setText("Level: " +Integer.toString(data.get(position).getLevel()));
        holder.leaderboardImageCard.setImageResource(new ProfilePicture(data.get(position).getProfile()).pic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView leaderboardIndexCard;
        public TextView leaderboardNameCard;
        public TextView leaderboardPointsCard;
        public TextView leaderboardLevelCard;
        public ImageView leaderboardImageCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            leaderboardIndexCard = itemView.findViewById(R.id.leaderboardIndex);
            leaderboardNameCard = itemView.findViewById(R.id.leaderboardName);
            leaderboardPointsCard = itemView.findViewById(R.id.leaderboardPoints);
            leaderboardLevelCard = itemView.findViewById(R.id.leaderboardLevel);
            leaderboardImageCard = itemView.findViewById(R.id.leaderboardImage);


        }
    }
}
