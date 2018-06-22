package com.example.mark.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Detail_Activity extends AppCompatActivity {
     TextView title;
     TextView autore;
     ImageView imageView;
     TextView descrizione;
     TextView casaeditrice;
     TextView autoreinfo;
     TextView paginenum;
     CheckBox checkbox;
     Boolean checked=true;
     Integer num=0;
     ImageView pinfavourite;


    private RequestQueue queue;
    public static final String TAG = "MyTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
        title = findViewById(R.id.testo);
        autore = findViewById(R.id.autore);
        imageView = findViewById(R.id.imageview);
        descrizione = findViewById(R.id.descrizione);
        casaeditrice = findViewById(R.id.casaeditrice);
        autoreinfo = findViewById(R.id.autoreinfo);
        paginenum = findViewById(R.id.paginenum);
        checkbox = findViewById(R.id.check_box_favorite);
        pinfavourite=findViewById(R.id.pin_favorite);



       checkbox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (checkbox.isChecked()){
                   pinfavourite.setVisibility(View.VISIBLE);

               }else{
                   pinfavourite.setVisibility(View.INVISIBLE);
               }
           }
       });


        Intent intent = getIntent();
        String c = intent.getStringExtra("id");
        getNoteFromURL(c);



    }
    public boolean isChecked() {
        return checked;
    }

    private void bindBookid(Book book) {
        title.setText(book.getTitolo());


        Glide.with(imageView.getContext())
                .load(book.getImageView())
                .into(imageView);

        int listAuthors = book.getauthors().size();

        String authorlist = "";
        for (int i = 0; i < listAuthors; i++) {
            authorlist += book.getauthors().get(i);
            if (i < listAuthors - 1)
                authorlist += ", ";

        }
        autore.setText(authorlist);



        descrizione.setText(book.getDescrizione());
        casaeditrice.setText(book.getCasaeditrice());



        autoreinfo.setText(authorlist);

        paginenum.setText(book.getPaginenum());


        autoreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Detail_Activity.this,MainActivity.class);
                intent.putExtra("autore",autoreinfo.getText().toString());
                startActivity(intent);
            }
        });
    }



    private void getNoteFromURL(String value) {

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://www.googleapis.com/books/v1/volumes/" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("jsonRequest", response.toString());
                        findViewById(R.id.simpleProgressBar).setVisibility(View.GONE);

                        try {
                            //JSONArray result = response.getJSONArray("items");
                            bindBookid(new Book(response));
                        }catch(JSONException e){
                            Toast.makeText(Detail_Activity.this, "Mancata connessione", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Detail_Activity.this, "Si è verificato un errore: " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonRequest);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }

    }



}

