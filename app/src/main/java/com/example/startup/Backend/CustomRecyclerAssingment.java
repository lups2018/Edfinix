package com.example.startup.Backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.startup.R;

import java.util.List;

public class CustomRecyclerAssingment extends RecyclerView.Adapter<CustomRecyclerAssingment.ViewHolder> {

    private Context context;
    private List<StudentAssingmentSet> testnUtils;

    public CustomRecyclerAssingment(Context context, List<StudentAssingmentSet> testnUtils) {
        this.context = context;
        this.testnUtils = testnUtils;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView date,timing,sub;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.test_date);
            timing =itemView.findViewById(R.id.timing);
            sub = itemView.findViewById(R.id.test_sub);

        }
    }

    @NonNull
    @Override
    public CustomRecyclerAssingment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_assignment_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAssingment.ViewHolder holder, int position) {

        holder.itemView.setTag(testnUtils.get(position));

        StudentAssingmentSet pu = testnUtils.get(position);

        holder.date.setText(pu.getTestDate());
        holder.sub.setText(pu.getTestTitle());
        holder.timing.setText(pu.getTestDetails());

    }

    @Override
    public int getItemCount() {
        return testnUtils.size();
    }

}
