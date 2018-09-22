package com.sptech.yt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Main3Activity extends AppCompatActivity {
    TextView t1;
    Firebase url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        t1=(TextView)findViewById(R.id.text3);
        Firebase.setAndroidContext(this);
        url=new Firebase("https://connecting-india-a708f.firebaseio.com/text");
        url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);
                t1.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
