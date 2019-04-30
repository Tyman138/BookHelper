package com.example.bookhelper;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.bookhelper.RecyclerViewDercirator.SpacesItemDecoration;
import com.example.bookhelper.entity.Author;
import com.example.bookhelper.reacyclerAdapter.AuthorsRecyclerVIewAdapter;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getUI();
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            alertDialog.setView(inflater.inflate(R.layout.new_author, null));
            alertDialog.setPositiveButton("Добавить",
                    (dialog, which) -> {
                        Dialog f = (Dialog) dialog;
                        EditText name = f.findViewById(R.id.addAuthorName);
                        EditText country = f.findViewById(R.id.addAuthorCountry);
                        EditText years = f.findViewById(R.id.addAuthorYearsLive);
                        if (name.getText().toString().isEmpty() ||
                                country.getText().toString().isEmpty() ||
                                years.getText().toString().isEmpty()) {
                            Snackbar.make(view, "Автор не добавлен, не все поля заполненны", Snackbar.LENGTH_LONG).show();
                        } else {
                            realm.executeTransaction(realm -> {
                                Number currentId = realm.where(Author.class).max("id");
                                int nextId;
                                if (currentId == null) {
                                    nextId = 0;
                                } else {
                                    nextId = currentId.intValue() + 1;
                                }
                                Author author = realm.createObject(Author.class, nextId);
                                author.setName(name.getText().toString());
                                author.setCountry(country.getText().toString());
                                author.setYearsOfLiving(years.getText().toString());
                                realm.insert(author);
                            });
                        }
                    });
            alertDialog.setNegativeButton("Отмена",
                    (dialog, which) -> dialog.cancel());
            alertDialog.show();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0 && !fab.isShown()) {
                    fab.show();
                    fab.animate().scaleX(1f).start();
                    fab.animate().scaleY(1f).start();
                } else if (dy > 0 && fab.isShown()) {
                    fab.animate().scaleX(0f).start();
                    fab.animate().scaleY(0f).start();
                    fab.hide();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void getUI() {
        mRecyclerView = findViewById(R.id.rvAuthors);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(7));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AuthorsRecyclerVIewAdapter(this, realm.where(Author.class).findAll(), realm);
        mRecyclerView.setAdapter(mAdapter);
        Parcelable state = mLayoutManager.onSaveInstanceState();
        mLayoutManager.onRestoreInstanceState(state);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
         /*   realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
