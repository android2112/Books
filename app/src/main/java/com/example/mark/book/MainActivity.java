package com.example.mark.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    public RecyclerView mrecyclerView;
    public RecyclerView.LayoutManager mlayoutManager;
    public Adapter madapter;
    public ArrayList<Book> mdataset=new ArrayList<>();
    public RequestQueue requestQueue;
    public EditText nometitolo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mrecyclerView=findViewById(R.id.rv);

        mlayoutManager=new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mlayoutManager);

        madapter=new Adapter(mdataset);
        mrecyclerView.setAdapter(madapter);

        nometitolo=findViewById(R.id.nometitolo);
        /*nometitolo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String strValue = nometitolo.getText().toString();
                getNoteFromURL(strValue);


                return false;
            }
        });
*/




        nometitolo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strValue = nometitolo.getText().toString();
                getNoteFromURL(strValue);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void getNoteFromURL(String value){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://www.googleapis.com/books/v1/volumes?q="+ URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("jsonRequest", response.toString());
                        try {
                            JSONArray result = response.getJSONArray("items");
                            ArrayList<Book> noteListFromResponse = Book.getBooksList(result);
                            madapter.setBooksList(noteListFromResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Si è verificato un errore: "+error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
            }
        });



        queue.add(jsonRequest);

    }

}
