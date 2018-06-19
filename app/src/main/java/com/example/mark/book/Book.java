package com.example.mark.book;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {

    String titolo;
    String autore;
    String id;
    ArrayList<String>authors=new ArrayList<>();



    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public Book(String titolo,String imageView) {

        this.titolo = titolo;

        this.imageView = imageView;
    }

    String imageView;

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Book(String titolo) {

        this.titolo = titolo;
    }

    public static ArrayList<Book> getBooksList(JSONArray books){

        ArrayList<Book> list = new ArrayList<>();

        for (int i = 0; i < books.length(); i++){

            try {
                JSONObject jsonNote = books.getJSONObject(i);
                
                list.add(new Book(jsonNote));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book(JSONObject jsonNote){
        try {
            id=jsonNote.getString("id");
            titolo = jsonNote.getJSONObject("volumeInfo").getString("title");
            JSONArray jsonauthors= jsonNote.getJSONObject("volumeInfo").getJSONArray("authors");
            for (int i=0;i<jsonauthors.length();i++){
                authors.add(jsonauthors.getString(i));
            }
            imageView=jsonNote.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");


        } catch (JSONException e) {
            Log.e("Note", e.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getauthors(){
        return authors;
    }

}

