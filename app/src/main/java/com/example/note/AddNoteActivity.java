package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.note.model.Note;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddNoteActivity extends AppCompatActivity {
    Realm realm;
    EditText noteTitle, noteContentEt;



    boolean fromAdapter = false;
    int id;
    RealmResults<Note> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        realm = Realm.getDefaultInstance();

        noteContentEt = findViewById(R.id.et_noteContent);
        noteTitle = findViewById(R.id.et_noteTitle);



        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            fromAdapter = true;
            id = intent.getIntExtra("id", 0);
            results = realm.where(Note.class).equalTo("id", id).findAll();
            for (Note note : results) {

                noteTitle.setText(note.getNoteTitle());
                noteContentEt.setText(note.getNoteContent());


            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        MenuItem saveItem = menu.findItem(R.id.save);
        MenuItem deleteItem = menu.findItem(R.id.delete);

        if (fromAdapter)
            deleteItem.setEnabled(true);
        else
            deleteItem.setEnabled(false);

        saveItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (fromAdapter) {
                    for (Note note : results) {
                        realm.beginTransaction();
                        note.setNoteTitle(noteTitle.getText().toString());
                        note.setNoteContent(noteContentEt.getText().toString());
                        realm.commitTransaction();
                    }
                } else {
                    Note note = new Note();
                    note.setId(getNextKey());
                    note.setNoteTitle(noteTitle.getText().toString());
                    note.setNoteContent(noteContentEt.getText().toString());
                    realm.beginTransaction();
                    realm.copyToRealm(note);
                    realm.commitTransaction();
                }
                return true;
            }
        });
        deleteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                realm.beginTransaction();
                results.deleteAllFromRealm();
                realm.commitTransaction();

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public int getNextKey() {
        try {
            return realm.where(Note.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return 0;
        }
    }


}
