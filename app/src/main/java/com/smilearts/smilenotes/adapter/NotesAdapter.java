package com.smilearts.smilenotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.model.ColorUtil;
import com.smilearts.smilenotes.model.NotesModel;
import com.smilearts.smilenotes.view.AddNotes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> implements Filterable {

    List<NotesModel> list;
    List<NotesModel> listAll;
    Context mContext;
    RoomDB roomDB;

    public NotesAdapter(List<NotesModel> list, Context mContext) {
        this.list = list;
        this.listAll = new ArrayList<>(list);
        this.mContext = mContext;
        roomDB = RoomDB.getInstance(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_notes , parent , false);
        return new NotesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final NotesModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.message.setText(model.getMessage());
        holder.date.setText(model.getDate());
        if(model.getPriority() == 0){
            holder.priority.setImageResource(R.drawable.non_priority_icon);
        }else {
            holder.priority.setImageResource(R.drawable.priority_icon);
        }
        if(model.getBg().equals(ColorUtil.DARKYELLOW)){
            holder.card.setBackgroundResource(R.color.DARKYELLOW);
        }else if(model.getBg().equals(ColorUtil.LIGHTBLUE)){
            holder.card.setBackgroundResource(R.color.LIGHTBLUE);
        }else if(model.getBg().equals(ColorUtil.LIGHTGREEN)){
            holder.card.setBackgroundResource(R.color.LIGHTGREEN);
        }else if(model.getBg().equals(ColorUtil.LIGHTYELLOW)){
            holder.card.setBackgroundResource(R.color.LIGHTYELLOW);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(
                        new Intent(mContext , AddNotes.class)
                        .putExtra("type","update")
                        .putExtra("id" , model.getId())
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        holder.priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getPriority() == 0){
                    holder.priority.setImageResource(R.drawable.priority_icon);
                    roomDB.notesDao().UpdatePriority(model.getId() ,1);
                }else {
                    holder.priority.setImageResource(R.drawable.non_priority_icon);
                    roomDB.notesDao().UpdatePriority(model.getId() ,0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NotesModel> filterList = new ArrayList<>();
            if(constraint.toString().isEmpty()) {
                filterList.addAll(listAll);
            } else {
                for (NotesModel note : listAll) {
                    if(note.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(note);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<NotesModel>)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title , message , date ;
        ImageView priority ;
        ConstraintLayout card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.row_notes_title);
            message = itemView.findViewById(R.id.row_notes_message);
            date = itemView.findViewById(R.id.row_notes_date);
            card = itemView.findViewById(R.id.row_notes_card);
            priority = itemView.findViewById(R.id.row_notes_priority);
        }
    }

}
