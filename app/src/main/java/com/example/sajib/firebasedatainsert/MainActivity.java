package com.example.sajib.firebasedatainsert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
  //  private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;
   // private FirebaseAuth firebaseAuth;
  //  private FirebaseAuth.AuthStateListener authStateListener;
    private EditText editTextone;
    private EditText editTexttwo;
    private EditText editTextthree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextone=(EditText)findViewById(R.id.edittexone);
        editTexttwo=(EditText)findViewById(R.id.edittexttwo);
        editTextthree=(EditText)findViewById(R.id.edittextthree);

        //firebaseAuth=FirebaseAuth.getInstance();
        //firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("AlLocation");
        progressDialog=new ProgressDialog(this);
    }

    private void savedata()
    {
        final String name=editTextone.getText().toString().trim();
        final double geoLat= Double.valueOf(editTexttwo.getText().toString().trim()); // parsedouble
        final double geoLong= Double.valueOf(editTextthree.getText().toString().trim());
        if (TextUtils.isEmpty(name)) {
            editTextone.setError("Your name is empty");
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(geoLat))) {
            editTexttwo.setError("Your geolat is empty");
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(geoLong))) {
            editTexttwo.setError("Your geolong is empty");
            return;
        }
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(String.valueOf(geoLat))
                && !TextUtils.isEmpty(String.valueOf(geoLong))){
            progressDialog.setTitle("Loading");
            progressDialog.show();
            final DatabaseReference newpost=databaseReference.push();
            String id=databaseReference.push().getKey();
            newpost.child("Idm").setValue(id);
            newpost.child("title").setValue(name);
            newpost.child("latitude").setValue(geoLat);
            newpost.child("longitude").setValue(geoLong)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "successfull", Toast.LENGTH_SHORT).show();

                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
    public void savebutton(View view) {
        savedata();
    }
}
