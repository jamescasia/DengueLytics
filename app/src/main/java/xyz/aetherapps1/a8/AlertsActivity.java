package xyz.aetherapps1.a8;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static xyz.aetherapps1.a8.DiagnoseActivity.latitude;
import static xyz.aetherapps1.a8.DiagnoseActivity.longitude;
import static xyz.aetherapps1.a8.LogInScreen.admin;
import static xyz.aetherapps1.a8.LogInScreen.databaseUsers;
import static xyz.aetherapps1.a8.LogInScreen.email;
import static xyz.aetherapps1.a8.LogInScreen.mAuth;
import static xyz.aetherapps1.a8.LogInScreen.reports;
import static xyz.aetherapps1.a8.LogInScreen.username;

public class AlertsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button createAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_name =  hView.findViewById(R.id.nav_name);
        TextView nav_email = hView.findViewById(R.id.nav_email);
        nav_email.setText(email);
        try{
        nav_name.setText(username.toUpperCase());}
        catch (Exception e ) {e.printStackTrace();}
        ImageView profpic = hView.findViewById(R.id.profilePic);
        if(admin.equals("true")) profpic.setImageDrawable(getResources().getDrawable(R.drawable.adminprofpic));
        else profpic.setImageDrawable(getResources().getDrawable(R.drawable.normalprofpic));

          createAlert = findViewById(R.id.createAlert);
        databaseUsers.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                admin = dataSnapshot.child("admin").getValue().toString().trim();
                if(admin== "true")
                {
                    createAlert.setVisibility(View.VISIBLE);
                    createAlert.setClickable(true);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LogInScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_diagnose) {
            startActivity(new Intent(getApplicationContext(), DiagnoseActivity.class));
        } else if (id == R.id.nav_report) {
            if(admin.equals("true"))  startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            else Toast.makeText(getApplicationContext(), "Not an Admin", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_track) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));


        } else if (id == R.id.nav_alerts) {


        } else if (id == R.id.nav_send) {
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
