package xyz.aetherapps1.a8;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import static xyz.aetherapps1.a8.DiagnoseActivity.latitude;
import static xyz.aetherapps1.a8.DiagnoseActivity.location;
import static xyz.aetherapps1.a8.DiagnoseActivity.longitude;
import static xyz.aetherapps1.a8.LogInScreen.admin;
import static xyz.aetherapps1.a8.LogInScreen.mAuth;
import static xyz.aetherapps1.a8.LogInScreen.reports;
import static xyz.aetherapps1.a8.LogInScreen.username;

public class ReportActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_report);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView nav_name = findViewById(R.id.nav_name);
        try{
            nav_name.setText(username.toUpperCase());}
        catch (Exception e ) {e.printStackTrace();}
        View hView =  navigationView.getHeaderView(0);
        ImageView profpic = hView.findViewById(R.id.profilePic);
        if(admin.equals("true")) profpic.setImageDrawable(getResources().getDrawable(R.drawable.adminprofpic));
        else profpic.setImageDrawable(getResources().getDrawable(R.drawable.normalprofpic));

        if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            Toast.makeText( getApplicationContext(), "ERROR getting location", Toast.LENGTH_LONG).show();
            displayPromptForEnablingGPS(this);

        }

        else{ getLocation();}


        Button submitReport = findViewById(R.id.submitReport);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(latitude == 0 && longitude == 0){
                    Toast.makeText( getApplicationContext(), "Error Submitting. Try again later", Toast.LENGTH_LONG).show();

                  }
                else{
                    DownloadTask dx = new DownloadTask();
                    dx.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude)+ "&lon=" +String.valueOf(longitude)+"&appid=389915ca20f3fde572ac9a9ff77593e9");

                    Report report = new Report( username,  mAuth.getCurrentUser().getUid(), latitude, longitude, 1, 0, false, location, dx.getTemperature(),dx.getWindspeed(),dx.getPressure(),dx.getHumidity());
                    String id = reports.push().getKey();
                    reports.child(id).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText( getApplicationContext(), "Report Submitted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

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
    void getLocation()
    {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
            }

            else
            {
                Toast.makeText( getApplicationContext(), "ERROR getting location", Toast.LENGTH_LONG).show();
            }
        }
        catch(SecurityException e) {
            e.printStackTrace();
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

        } else if (id == R.id.nav_track) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));


        } else if (id == R.id.nav_alerts) {
            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));


        } else if (id == R.id.nav_send) {
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public   void displayPromptForEnablingGPS(final Activity activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Do you want open GPS setting?";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
}
