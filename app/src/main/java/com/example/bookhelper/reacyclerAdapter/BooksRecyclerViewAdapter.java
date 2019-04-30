package com.example.bookhelper.reacyclerAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookhelper.R;
import com.example.bookhelper.entity.Book;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder> implements RealmChangeListener {
    private Context ctx;
    private RealmList<Book> books;
    private Realm realm;

    public BooksRecyclerViewAdapter(Context ctx, RealmList<Book> books, Realm realm) {
        this.ctx = ctx;
        this.books = books;
        this.realm = realm;
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View bookView = LayoutInflater.from(ctx).inflate(R.layout.book_layout, viewGroup, false);
        return new ViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Book book = books.get(position);
        viewHolder.bookNameTextView.setText(book.getName());
        viewHolder.genreTextView.setText(book.getGenre());
        viewHolder.numbersOfPagesTextView.setText(book.getNumbersOfPages());
        viewHolder.langTextVIew.setText(book.getLanguage());
        viewHolder.editionTextView.setText(book.getEdition());
        viewHolder.publisherTextView.setText(book.getPublisher());
        viewHolder.v.setOnLongClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        realm.beginTransaction();
                        books.get(position).deleteFromRealm();
                        realm.commitTransaction();
                        notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View tempView = inflater.inflate(R.layout.new_book, null);
                        alertDialog.setView(tempView);

                        EditText addBookNameET = tempView.findViewById(R.id.addBookNameET);
                        EditText addGengreET = tempView.findViewById(R.id.addGenreET);
                        EditText addNumbersOfPagesET = tempView.findViewById(R.id.addNumbersOfPagesET);
                        EditText addLangET = tempView.findViewById(R.id.addLangET);
                        EditText addEditionET = tempView.findViewById(R.id.addEditionET);
                        EditText addPublisherET = tempView.findViewById(R.id.addPublisherET);

                        addBookNameET.setText(books.get(position).getName());
                        addGengreET.setText(books.get(position).getGenre());
                        addNumbersOfPagesET.setText(books.get(position).getNumbersOfPages());
                        addLangET.setText(books.get(position).getLanguage());
                        addEditionET.setText(books.get(position).getEdition());
                        addPublisherET.setText(books.get(position).getPublisher());


                        alertDialog.setPositiveButton("Изменить",
                                (sDialog, sWhich) -> {
                                    if (addBookNameET.getText().toString().isEmpty()
                                            || addGengreET.getText().toString().isEmpty()
                                            || addNumbersOfPagesET.getText().toString().isEmpty()
                                            || addLangET.getText().toString().isEmpty()
                                            || addEditionET.getText().toString().isEmpty()
                                            || addPublisherET.getText().toString().isEmpty()) {
                                        Snackbar.make(v, "Произведение не  было изменено, не все поля заполненны", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        realm.beginTransaction();
                                        books.get(position).setName(addBookNameET.getText().toString());
                                        books.get(position).setGenre(addGengreET.getText().toString());
                                        books.get(position).setNumbersOfPages(addNumbersOfPagesET.getText().toString());
                                        books.get(position).setLanguage(addLangET.getText().toString());
                                        books.get(position).setEdition(addEditionET.getText().toString());
                                        books.get(position).setPublisher(addPublisherET.getText().toString());
                                        realm.commitTransaction();
                                        notifyDataSetChanged();
                                    }
                                });
                        alertDialog.setNegativeButton("Отмена",
                                (sDialog, sWhich) -> sDialog.cancel());
                        alertDialog.show();
                        break;
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage("Что вы хотите сделать?").setPositiveButton("Удалить", dialogClickListener)
                    .setNegativeButton("Изменить", dialogClickListener).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View v;
        TextView bookNameTextView;
        TextView genreTextView;
        TextView numbersOfPagesTextView;
        TextView langTextVIew;
        TextView editionTextView;
        TextView publisherTextView;
        TextView colorView;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            colorView = itemView.findViewById(R.id.colorView);
            colorView.setBackgroundColor(getRandomColor());
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            numbersOfPagesTextView = itemView.findViewById(R.id.numbersOfPagesTextView);
            langTextVIew = itemView.findViewById(R.id.langTextVIew);
            editionTextView = itemView.findViewById(R.id.editionTextView);
            publisherTextView = itemView.findViewById(R.id.publisherTextView);
            itemView.setClipToOutline(true);
        }
    }


}
