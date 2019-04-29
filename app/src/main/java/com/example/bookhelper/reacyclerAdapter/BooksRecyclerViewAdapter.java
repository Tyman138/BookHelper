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
import android.widget.TextView;

import com.example.bookhelper.R;
import com.example.bookhelper.entity.Book;

import java.util.List;
import java.util.Random;

public class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder> {
    private Context ctx;
    private List<Book> books;

    public BooksRecyclerViewAdapter(Context ctx, List<Book> books) {
        this.ctx = ctx;
        this.books = books;
    }
    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View bookView = LayoutInflater.from(ctx).inflate(R.layout.book_layout,viewGroup,false);
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
        viewHolder.v.setOnLongClickListener(v ->{
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        books.remove(position);
                        notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Snackbar.make(v, "Change incoming...", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
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


}
