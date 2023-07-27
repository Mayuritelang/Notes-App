package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static  ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            notes.add("A Happy Day!");
        }
        else {
            notes = new ArrayList(set);
        }

        ListView listView = findViewById(R.id.listview);
        ImageView imageView = findViewById(R.id.imageView2);
        notes.add("Example notes");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), NotesEditActivity.class);
            intent.putExtra("notesId", i);
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {

            int itemDelete = i;
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure")
                    .setMessage("Do you want to delete")
                    .setPositiveButton("Yes", (dialogInterface, i1) -> {
                        notes.remove(itemDelete);
                        arrayAdapter.notifyDataSetChanged();

                        HashSet<String> sets = new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes", set).apply();

                    })
                    .setNegativeButton("No",null)
                    .show();
            return true;
        });
    }

    // MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         super.onOptionsItemSelected(item);

         if(item.getItemId() == R.id.add_note) {
             Intent intent = new Intent(getApplicationContext(), NotesEditActivity.class);
             startActivity(intent);
             return  true;
         }
         return false;
    }
}
