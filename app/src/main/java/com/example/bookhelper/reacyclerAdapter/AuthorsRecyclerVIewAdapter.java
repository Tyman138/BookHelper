package com.example.bookhelper.reacyclerAdapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.bookhelper.BooksActivity;
import com.example.bookhelper.R;
import com.example.bookhelper.entity.Author;

import java.util.List;
import java.util.Random;

public class AuthorsRecyclerVIewAdapter extends RecyclerView.Adapter<AuthorsRecyclerVIewAdapter.ViewHolder> {
    private Context ctx;
    private List<Author> authors;


    public AuthorsRecyclerVIewAdapter(Context ctx, List<Author> authors) {
        this.ctx = ctx;
        this.authors = authors;
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorName;
        TextView countryOfLiving;
        TextView yearsLiving;
        TextView colorsTextView;
        View v;

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            authorName = itemView.findViewById(R.id.authorNameTextView);
            countryOfLiving = itemView.findViewById(R.id.countryTextView);
            yearsLiving = itemView.findViewById(R.id.yearsTextView);
            colorsTextView = itemView.findViewById(R.id.colorTextView);
            colorsTextView.setBackgroundColor(getRandomColor());
            itemView.setClipToOutline(true);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public AuthorsRecyclerVIewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View authorView = LayoutInflater.from(ctx).inflate(R.layout.author_layout, viewGroup, false);
        return new ViewHolder(authorView);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorsRecyclerVIewAdapter.ViewHolder viewHolder, final int position) {
        final Author author = authors.get(position);
        viewHolder.authorName.setText(author.getName());
        viewHolder.countryOfLiving.setText(author.getCountry());
        viewHolder.yearsLiving.setText(author.getYearsOfLiving());

        viewHolder.v.setOnClickListener(v -> {
            Snackbar.make(v, "OnClick " + position, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Intent intent = new Intent(ctx, BooksActivity.class);
            ctx.startActivity(intent);
        });

        viewHolder.v.setOnLongClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        authors.remove(position);
                        notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View tempView = inflater.inflate(R.layout.new_author, null);
                        alertDialog.setView(tempView);

                        EditText name = tempView.findViewById(R.id.addAuthorName);
                        EditText country = tempView.findViewById(R.id.addAuthorCountry);
                        EditText years = tempView.findViewById(R.id.addAuthorYearsLive);
                        name.setText(authors.get(position).getName());
                        country.setText(authors.get(position).getCountry());
                        years.setText(authors.get(position).getYearsOfLiving());

                        alertDialog.setPositiveButton("Изменить",
                                (sDialog, sWhich) -> {
                                    if (name.getText().toString().isEmpty() ||
                                            country.getText().toString().isEmpty() ||
                                            years.getText().toString().isEmpty()) {
                                        Snackbar.make(v, "Автор не изменен, не все поля заполненны", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        authors.get(position).setName(name.getText().toString());
                                        authors.get(position).setCountry(country.getText().toString());
                                        authors.get(position).setYearsOfLiving(years.getText().toString());
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
        return authors.size();
    }


}
