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
import android.widget.EditText;

import com.example.bookhelper.RecyclerViewDercirator.SpacesItemDecoration;
import com.example.bookhelper.entity.Author;
import com.example.bookhelper.entity.Book;
import com.example.bookhelper.reacyclerAdapter.BooksRecyclerViewAdapter;

import io.realm.Realm;

public class BooksActivity extends AppCompatActivity {
    private Realm realm;
    private Author author;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        author = realm.where(Author.class).equalTo("id", getIntent().getExtras().getInt("authorId")).findFirst();

        getUI();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(BooksActivity.this);
            LayoutInflater inflater = BooksActivity.this.getLayoutInflater();
            alertDialog.setView(inflater.inflate(R.layout.new_book, null));
            alertDialog.setPositiveButton("Добавить",
                    (dialog, which) -> {
                        Dialog f = (Dialog) dialog;
                        EditText addBookNameET = f.findViewById(R.id.addBookNameET);
                        EditText addGengreET = f.findViewById(R.id.addGenreET);
                        EditText addNumbersOfPagesET = f.findViewById(R.id.addNumbersOfPagesET);
                        EditText addLangET = f.findViewById(R.id.addLangET);
                        EditText addEditionET = f.findViewById(R.id.addEditionET);
                        EditText addPublisherET = f.findViewById(R.id.addPublisherET);
                        if (addBookNameET.getText().toString().isEmpty()
                                || addGengreET.getText().toString().isEmpty()
                                || addNumbersOfPagesET.getText().toString().isEmpty()
                                || addLangET.getText().toString().isEmpty()
                                || addEditionET.getText().toString().isEmpty()
                                || addPublisherET.getText().toString().isEmpty()) {
                            Snackbar.make(view, "Произведение не добавлено, не все поля заполненны", Snackbar.LENGTH_LONG).show();
                        } else {
                            realm.beginTransaction();
                            author.getBooks().add(new Book(
                                    addBookNameET.getText().toString(),
                                    addGengreET.getText().toString(),
                                    addNumbersOfPagesET.getText().toString(),
                                    addLangET.getText().toString(),
                                    addEditionET.getText().toString(),
                                    addPublisherET.getText().toString()
                            ));
                            realm.commitTransaction();
                            mAdapter.notifyDataSetChanged();
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

    private void getUI() {
        mRecyclerView = findViewById(R.id.rvBooks);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(7));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BooksRecyclerViewAdapter(this, author.getBooks(), realm);
        mRecyclerView.setAdapter(mAdapter);

        Parcelable state = mLayoutManager.onSaveInstanceState();
        mLayoutManager.onRestoreInstanceState(state);//SAVE SATE OF RECYCLER

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
