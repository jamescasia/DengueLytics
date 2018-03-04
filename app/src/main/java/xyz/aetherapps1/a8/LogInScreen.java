package xyz.aetherapps1.a8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class LogInScreen extends AppCompatActivity implements LogInFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener{
    public static FirebaseAuth mAuth;
    public static boolean loggedin;
    private static boolean connected;
        public static FirebaseDatabase database;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static DatabaseReference databaseUsers;
    public static DatabaseReference verticesRef  ;
    public static DatabaseReference messagesRef  ;
    public static DatabaseReference totalCasesRef  ;
    public static DatabaseReference reports  ;
    public static FirebaseUser user;
    public static DatabaseReference currentuserid;
    public static String username;
    public static String email;
    public static DatabaseReference propertime;
    public static String admin ="false";
    FrameLayout fragmentspace;

    public static Typeface roboto;
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        fragmentspace = (FrameLayout)findViewById(R.id.fragmentspace);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        else connected = false;

        if(connected)
        {

            database = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            databaseUsers = database.getReference("User");
            verticesRef = database.getReference("Functions/DengueTracking/vertices");
            messagesRef = database.getReference("Functions/FireConnect/Messages");
            propertime = database.getReference("Functions/DengueTracking/ProperTime");
            totalCasesRef = database.getReference("Functions/DengueTracking/totalcases");
            reports = database.getReference("Functions/DengueTracking/reports");

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() != null)
                    {
                        databaseUsers.push();
                          user = mAuth.getCurrentUser();
                        currentuserid = databaseUsers.child(user.getUid()).child("name");
                        databaseUsers.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                admin = dataSnapshot.child("admin").getValue().toString().trim();
                                email = dataSnapshot.child("email").getValue().toString().trim();
                                username = dataSnapshot.child("name").getValue().toString().trim();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        startActivity(new Intent( getApplicationContext(), HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                    }



                }
            };

            Animation loginanim = AnimationUtils.loadAnimation(this, R.anim.loginanim);
            fragmentspace.setAnimation(loginanim);
            fragmentspace.animate();


            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentspace, new LogInFragment()).commit();
        }
        else Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_LONG).show();
        roboto = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");






    }

    public LogInScreen()
    {
    //
    }
    @Override
    public void onStart() {
        super.onStart();

        if(connected) {
            mAuth.addAuthStateListener(mAuthListener);
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
