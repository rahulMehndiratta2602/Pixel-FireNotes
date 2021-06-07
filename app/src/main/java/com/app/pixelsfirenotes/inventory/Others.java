package com.app.pixelsfirenotes.inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.pixelsfirenotes.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Others extends AppCompatActivity {

    private FirebaseFirestore dbOthers = FirebaseFirestore.getInstance();
    private CollectionReference OthersRef = dbOthers.collection("users");
    private FirebaseAuth fAuth;
    private EAdapter adapter;
    private FloatingActionButton savebutton;
    private String asd;

    private Button Others_button;
    private String search;
    private EditText searchbar_Others;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Others");


        fAuth = FirebaseAuth.getInstance();
        savebutton = findViewById(R.id.add_item_Others);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Others.this, NewItemOthers.class));
            }
        });

        buildrecyclerview();

        searchbar_Others=findViewById(R.id.searchbar_Others);
        Others_button=findViewById(R.id.Others_button);

        Others_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search= searchbar_Others.getText().toString();
                Query query = OthersRef.document(asd).collection("OthersRef").orderBy("title").startAt(search).endAt(search + "\uf8ff");
                FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
                adapter.updateOptions(options);

            }
        });





    }
    private void buildrecyclerview(){
        asd = fAuth.getCurrentUser().getUid();
        Log.d("test","fetching Others for "+asd);
        Query query = OthersRef.document(asd).collection("OthersRef");
        FirestoreRecyclerOptions<Set_item> options = new FirestoreRecyclerOptions.Builder<Set_item>().setQuery(query, Set_item.class).build();
        adapter = new EAdapter(options,this);
        RecyclerView recyclerView = findViewById(R.id.recviewOthers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new EAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Set_item note = documentSnapshot.toObject(Set_item.class);
                shareitem(note);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void shareitem(Set_item note) {
        Intent myIntent= new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody=note.getTitle();
        String shareSub="Others ITEM SHARING";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        String sharebody1=note.getInitqty();
        String Img= note.getImgres();
        myIntent.putExtra(Intent.EXTRA_TEXT,"Item Name: "+shareBody+"\n"+ "Available Quantity: "+sharebody1+"The Pic:"+Img);

        startActivity(Intent.createChooser(myIntent,"Share using"));
    }

}