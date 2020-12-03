package com.example.finalclub;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class home<navController> extends AppCompatActivity {
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

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.filter) {
                    Log.d(TAG, "onClick: FILTER");
                    Toast.makeText(getApplicationContext(), "Filter", Toast.LENGTH_SHORT).show();
                }

                else if(id == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getApplicationContext(), "Successfully Logged out", Toast.LENGTH_SHORT).show();
                }


                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
//                      startActivity(new Intent(getApplicationContext(), home.class));
//                      overridePendingTransition(0,0);
                      return true;
                    case R.id.map_nav:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colref = db.collection("events");




        colref.orderBy("event_name").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){


                    Event event = queryDocumentSnapshot.toObject(Event.class);

                    String eventName = event.getEventName();
                    String eventPrice = event.getEventPrice();
                    String clubName = event.getClubName();
                    String date = event.getDate();
                    String image = event.getImage();

                    initImageBitmaps(eventName,eventPrice,clubName, date, image);
                }

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

}