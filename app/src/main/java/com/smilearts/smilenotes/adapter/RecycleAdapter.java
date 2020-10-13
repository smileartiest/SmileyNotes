package com.smilearts.smilenotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.smilearts.smilenotes.R;
import com.smilearts.smilenotes.controller.RoomDB;
import com.smilearts.smilenotes.controller.TimeDate;
import com.smilearts.smilenotes.model.ColorUtil;
import com.smilearts.smilenotes.model.NotesModel;
import com.smilearts.smilenotes.model.RecycleModel;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    List<RecycleModel> list;
    List<RecycleModel> listAll;
    Context mContext;
    RoomDB roomDB;
    int position;

    public RecycleAdapter(List<RecycleModel> list, Context mContext) {
        this.list = list;
        this.listAll = list;
        this.mContext = mContext;
        roomDB = RoomDB.getInstance(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecycleAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_notes , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final RecycleModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.message.setText(model.getMessage());
        holder.date.setText(model.getDate());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu menu = new PopupMenu(mContext , v);
                menu.inflate(R.menu.recycle_menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.recycle_menu_delete:
                                roomDB.notesDao().DeleteRecycle(model.getId());
                                list.remove(position);
                                notifyDataSetChanged();
                                Snackbar.make(v , "Permanently Deleted This Notes" , Snackbar.LENGTH_SHORT).show();
                                return true;
                            case R.id.recycle_menu_restore:
                                NotesModel model1 = new NotesModel();
                                model1.setTitle(model.getTitle());
                                model1.setMessage(model.getMessage());
                                model1.setDate(new TimeDate().getDateYMD());
                                model1.setTime(new TimeDate().getTime());
                                model1.setBg(ColorUtil.DEFAULT);
                                model1.setPriority(0);
                                roomDB.notesDao().Insert(model1);
                                roomDB.notesDao().DeleteRecycle(model.getId());
                                list.remove(position);
                                notifyDataSetChanged();
                                Snackbar.make(v , "Restore your Notes Successful" , Snackbar.LENGTH_SHORT).show();
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
            priority.setVisibility(View.INVISIBLE);
        }
    }

}
