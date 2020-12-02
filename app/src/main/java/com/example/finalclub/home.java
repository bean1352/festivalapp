package com.example.finalclub;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class home extends AppCompatActivity {
     ListView listView;
     CardView cardview;ActionBar.LayoutParams layoutparams;
     TextView textview,textview1,textview2;
     LinearLayout LinearLayout;
    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
    private static final String TAG = "home";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageURLs = new ArrayList<>();
    private ArrayList<String> mEvent = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //final TextView textview = findViewById(R.id.textView);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colref = db.collection("events");
        //LinearLayout = (LinearLayout)findViewById(R.id.linearlayout);


        colref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){


                    Event event = queryDocumentSnapshot.toObject(Event.class);

                    String eventName = event.getEventName();
                    String eventPrice = event.getEventPrice();
                    String clubName = event.getClubName();
                    String date = event.getDate();
                    String image = event.getImage();
                    //data +=  " "+eventName+" "+eventPrice+" "+clubName+" \n\n";
                    //sfd.format(new Date(date));

                    //CreateCardView(eventName,eventPrice,clubName);
                    initImageBitmaps(eventName,eventPrice,clubName, date, image);
                }
                //textView.setText(data);
            }
        });



    }

    private void initImageBitmaps(String event_name, String event_price, String club_name, String date, String image){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");

          mImageURLs.add(image);
          mNames.add(club_name);
          mEvent.add(event_name);
          mPrice.add("R "+event_price);
          mDate.add(date);


        initRecyclerView();

    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter( mNames, mImageURLs, mEvent, mPrice, mDate, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void CreateCardView(String event_name, String event_price, String club_name){
//        cardview = new CardView(this);
//        layoutparams = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT
//        );
//        // Set the layoutParams on the CardView
//        cardview.setLayoutParams(layoutparams);
//
//
//        // Set the card’s corner radius
//        cardview.setRadius(6);
//
//
//        // Set its background color
//       // cardview.setCardBackgroundColor(Color.GREEN);
//
//        // Set its maximum elevation
//        cardview.setMaxCardElevation(6);
//
//        // Create a TextView
//        textview = new TextView(this);
//        textview1 = new TextView(this);
//        textview2 = new TextView(this);
//
//        // Apply the layoutParams (wrap_content) to this TextView
//
//        textview.setLayoutParams(layoutparams);
//
//        textview1.setLayoutParams(layoutparams);
//
//        textview2.setLayoutParams(layoutparams);
//
//
//
//        // Define the text you want to display
//       // textview.setText(event_name+"\n"+event_price+"\n"+club_name+"\n\n");
//        textview.setText(event_name);
//        textview1.setText(event_price);
//        textview2.setText(club_name);
//
//
//
//
//
//        // Define the text’s appearance, including its color
//        textview.setTextAppearance(android.R.style.TextAppearance_Material_Headline);
//        textview.setTextColor(Color.BLACK);
//        textview1.setTextAppearance(android.R.style.TextAppearance_Material_Headline);
//        textview1.setTextColor(Color.BLACK);
//        textview2.setTextAppearance(android.R.style.TextAppearance_Material_Headline);
//        textview2.setTextColor(Color.BLACK);
//        cardview.addView(textview);
//        cardview.addView(textview1);
//        cardview.addView(textview2);
//        LinearLayout.addView(cardview);
//    }
}