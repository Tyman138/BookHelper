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

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class AuthorsRecyclerVIewAdapter extends RecyclerView.Adapter<AuthorsRecyclerVIewAdapter.ViewHolder> implements RealmChangeListener {
    private final RealmResults<Author> mAuthors;
    private Context ctx;
    private Realm realm;


    public AuthorsRecyclerVIewAdapter(Context ctx, RealmResults<Author> mAuthors, Realm realm) {
        this.ctx = ctx;
        this.mAuthors = mAuthors;
        this.realm = realm;
        mAuthors.addChangeListener(this);
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
    public AuthorsRecyclerVIewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View authorView = LayoutInflater.from(ctx).inflate(R.layout.author_layout, viewGroup, false);
        return new ViewHolder(authorView);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorsRecyclerVIewAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.authorName.setText(mAuthors.get(position).getName());
        viewHolder.countryOfLiving.setText(mAuthors.get(position).getCountry());
        viewHolder.yearsLiving.setText(mAuthors.get(position).getYearsOfLiving());

        viewHolder.v.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, BooksActivity.class);
            intent.putExtra("authorId", mAuthors.get(position).getId());
            ctx.startActivity(intent);
        });

        viewHolder.v.setOnLongClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        realm.beginTransaction();
                        mAuthors.get(position).deleteFromRealm();
                        realm.commitTransaction();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View tempView = inflater.inflate(R.layout.new_author, null);
                        alertDialog.setView(tempView);

                        EditText name = tempView.findViewById(R.id.addAuthorName);
                        EditText country = tempView.findViewById(R.id.addAuthorCountry);
                        EditText years = tempView.findViewById(R.id.addAuthorYearsLive);
                        name.setText(mAuthors.get(position).getName());
                        country.setText(mAuthors.get(position).getCountry());
                        years.setText(mAuthors.get(position).getYearsOfLiving());

                        alertDialog.setPositiveButton("Изменить",
                                (sDialog, sWhich) -> {
                                    if (name.getText().toString().isEmpty() ||
                                            country.getText().toString().isEmpty() ||
                                            years.getText().toString().isEmpty()) {
                                        Snackbar.make(v, "Автор не изменен, не все поля заполненны", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        realm.beginTransaction();
                                        mAuthors.get(position).setName(name.getText().toString());
                                        mAuthors.get(position).setCountry(country.getText().toString());
                                        mAuthors.get(position).setYearsOfLiving(years.getText().toString());
                                        realm.commitTransaction();
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
        return mAuthors.size();
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


}
