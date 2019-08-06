package com.example.note.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note.AddNoteActivity;
import com.example.note.R;
import com.example.note.model.Note;
import java.util.List;

/**
 * Created by devlomi on 3/31/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private Context context;
    private List<Note> notesList;

    public NoteAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row,parent,false);
        return new NoteHolder(row);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        final Note note = notesList.get(position);
        holder.noteTitleTv.setText(note.getNoteTitle());

        holder.noteTitleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("id",note.getId());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

     class NoteHolder extends RecyclerView.ViewHolder{
        TextView noteTitleTv;
        public NoteHolder(View itemView) {
            super(itemView);
            noteTitleTv = (TextView) itemView.findViewById(R.id.note_title_tv);

        }
    }
}
