package com.example.mark.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Book> mdataset;
    private Context context;

    public Adapter(ArrayList<Book> mdataset) {
        this.mdataset = mdataset;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_titolo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.nometitolo.setText(mdataset.get(position).getTitolo());
        /* holder.autore.setText(mdataset.get(position));*/

        int listAuthors = mdataset.get(position).getauthors().size();

        String authorlist = "";
        for (int i = 0; i < listAuthors; i++) {
            authorlist += mdataset.get(position).getauthors().get(i);
            if (i < listAuthors - 1)
                authorlist += ", ";

        }
        holder.autore.setText(authorlist);


        Glide.with(holder.nometitolo.getContext())
                .load(mdataset.get(position).getImageView())
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView nometitolo;
        public TextView autore;
        public ImageView imageView;

        //ArrayList<String> idlibro;

        public ViewHolder(final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cv);
            nometitolo = itemView.findViewById(R.id.nometitolo);
            imageView = itemView.findViewById(R.id.image);
            autore = itemView.findViewById(R.id.autore);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    String title = mdataset.get(getAdapterPosition()).getTitolo();
                    ArrayList<String> autori=mdataset.get(getAdapterPosition()).getauthors();
                    */
                    String id = mdataset.get(getAdapterPosition()).getId();
                    Intent nuovolibro = new Intent(v.getContext(), Detail_Activity.class);
                    nuovolibro.putExtra("id", id);

                    ((MainActivity) v.getContext()).startActivity(nuovolibro);

                }
            });
        }
    }

    public void setBooksList(ArrayList<Book> books) {
        mdataset.clear();
        mdataset.addAll(books);
        notifyDataSetChanged();
    }
}


