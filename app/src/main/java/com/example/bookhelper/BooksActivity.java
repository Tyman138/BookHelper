package com.example.bookhelper;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.example.bookhelper.RecyclerViewDercirator.SpacesItemDecoration;
import com.example.bookhelper.entity.Book;
import com.example.bookhelper.reacyclerAdapter.BooksRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    public List<Book> books;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Book book = new Book("Сказка о рыбаке и рыбке", "Сказка", "16", "Русский", "200", "АСТ");
        books = new ArrayList<>();
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);
        books.add(book);

        getUI();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(BooksActivity.this);
            LayoutInflater inflater = BooksActivity.this.getLayoutInflater();
            alertDialog.setView(inflater.inflate(R.layout.new_book, null));
            alertDialog.setPositiveButton("Добавить",
                    (dialog, which) -> {
                        Dialog f = (Dialog) dialog;
                        if (2 == 1)//FIXME
                        {
                            Snackbar.make(view, "Книга не добавлена, не все поля заполненны", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, "addIncoming", Snackbar.LENGTH_LONG).show();
                            fileList();
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
        fillList();
    }

    private void fillList() {
        Parcelable state = mLayoutManager.onSaveInstanceState();
        mAdapter = new BooksRecyclerViewAdapter(this, books);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager.onRestoreInstanceState(state);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable("State", mLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable("State"));
        super.onRestoreInstanceState(savedInstanceState);
    }

}
