package com.smilearts.smilenotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.controller.TimeDate;
import com.smilearts.smilenotes.model.ColorUtil;
import com.smilearts.smilenotes.model.NotesModel;
import com.smilearts.smilenotes.model.RecycleModel;
import com.smilearts.smilenotes.view.AddNotes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> implements Filterable {

    List<NotesModel> list;
    List<NotesModel> listAll;
    Context mContext;
    RoomDB roomDB;
    int position;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return true;
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

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu menu = new PopupMenu(mContext ,v);
                menu.inflate(R.menu.context_menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.context_delete:
                                roomDB.notesDao().DeleteNote(model.getId());
                                list.remove(position);
                                notifyDataSetChanged();
                                menu.dismiss();
                                Snackbar.make(v , "Permanently Deleted" , Snackbar.LENGTH_SHORT).show();
                                return true;
                            case R.id.context_share:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "Title : " + model.getTitle() + "\nNotes : \n" + model.getMessage());
                                sendIntent.setType("text/plain");
                                Intent shareIntent = Intent.createChooser(sendIntent, null);
                                mContext.startActivity(shareIntent);
                                menu.dismiss();
                                return true;
                            case R.id.context_recycleBin:
                                RecycleModel model1 = new RecycleModel();
                                model1.setTitle(model.getTitle());
                                model1.setMessage(model.getMessage());
                                model1.setDate(new TimeDate().getDateYMD());
                                roomDB.notesDao().InsertRecycle(model1);
                                roomDB.notesDao().DeleteNote(model.getId());
                                list.remove(position);
                                notifyDataSetChanged();
                                Snackbar.make(v , "Move to Recycle Bin" , Snackbar.LENGTH_SHORT).show();
                                return true;
                            case R.id.context_priority:
                                roomDB.notesDao().UpdatePriority(model.getId() , 1);
                                Snackbar.make(v , "Set Priority" , Snackbar.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title , message , date ;
        ImageView priority , more ;
        ConstraintLayout card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.row_notes_title);
            message = itemView.findViewById(R.id.row_notes_message);
            date = itemView.findViewById(R.id.row_notes_date);
            card = itemView.findViewById(R.id.row_notes_card);
            priority = itemView.findViewById(R.id.row_notes_priority);
            more = itemView.findViewById(R.id.row_notes_moreicon);
        }
    }

}
