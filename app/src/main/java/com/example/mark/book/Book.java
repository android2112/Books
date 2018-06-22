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
    String image;
    String descrizione;
    String casaeditrice;
    String paginenum;
    private boolean isShowOnTop;

    public String getPaginenum() {
        return paginenum;
    }

    public void setPaginenum(String paginenum) {
        this.paginenum = paginenum;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCasaeditrice() {
        return casaeditrice;
    }

    public void setCasaeditrice(String casaeditrice) {
        this.casaeditrice = casaeditrice;
    }

    ArrayList<String>authors=new ArrayList<>();



    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public Book(String titolo,String imageView,String paginenum) {

        this.titolo = titolo;

        this.imageView = imageView;

        this.paginenum=paginenum;
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

    public Book(JSONObject jsonNote) throws JSONException{

        id=jsonNote.getString("id");
        JSONObject volumeInfo=jsonNote.getJSONObject("volumeInfo");
         titolo = volumeInfo.optString("title","No Title ");


            JSONArray jsonauthors = volumeInfo.optJSONArray("authors");
            if(jsonauthors!=null){
            for (int i = 0; i < jsonauthors.length(); i++) {
                authors.add(jsonauthors.getString(i));
            }} else authors.add(" ");


            imageView = volumeInfo.getJSONObject("imageLinks").optString("thumbnail","No thumbnail");

            descrizione = volumeInfo.optString("description","No description");
            casaeditrice = volumeInfo.optString("publisher","No casa editrice");
            paginenum = volumeInfo.optString("pageCount","No paginenum");

    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getauthors(){
        return authors;
    }
    public Book(){};

    public boolean isShowOnTop() {
        return isShowOnTop;
    }


}

