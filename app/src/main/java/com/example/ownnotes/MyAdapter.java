package com.example.ownnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    RealmResults<Note> noteslist;

    public MyAdapter(Context context, RealmResults<Note> noteslist) {
        this.context = context;
        this.noteslist = noteslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note note = noteslist.get(position);
        holder.titleoutput.setText(note.getTitle());
        holder.descriptionoutput.setText(note.getDescription());

        String formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.createdTimeoutput.setText(formatedTime);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE"))
                        {
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                        }


                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });
        

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleoutput;
        TextView descriptionoutput;
        TextView createdTimeoutput;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            titleoutput = itemView.findViewById(R.id.titleoutput);
            descriptionoutput = itemView.findViewById(R.id.descriptionoutput);
            createdTimeoutput = itemView.findViewById(R.id.createdtimeoutput);

        }
    }
}
