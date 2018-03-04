package xyz.aetherapps1.a8;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.support.v4.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static xyz.aetherapps1.a8.DownloadTask.HUM;
import static xyz.aetherapps1.a8.DownloadTask.PRESSURE;
import static xyz.aetherapps1.a8.DownloadTask.TEMP;
import static xyz.aetherapps1.a8.DownloadTask.WIND;
import static xyz.aetherapps1.a8.LogInScreen.admin;
import static xyz.aetherapps1.a8.LogInScreen.email;
import static xyz.aetherapps1.a8.LogInScreen.hideKeyboard;
import static xyz.aetherapps1.a8.LogInScreen.mAuth;
import static xyz.aetherapps1.a8.LogInScreen.reports;
import static xyz.aetherapps1.a8.LogInScreen.username;

public class DiagnoseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    LocationManager locationManager;
    public static double latitude;
    public static String location;
  public static double longitude;
  public  static double temperature;
  public static double humidity;
  public static double windSpeed;
  public static double pressure;

    TextView weatheract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        hideKeyboard(this);
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
        tagokeyboard();



        nav_email.setText(email);
        ImageView profpic = hView.findViewById(R.id.profilePic);
        if(admin.equals("true")) profpic.setImageDrawable(getResources().getDrawable(R.drawable.adminprofpic));
        else profpic.setImageDrawable(getResources().getDrawable(R.drawable.normalprofpic));
        try{
        nav_name.setText(username.toUpperCase());}
        catch(Exception e){e.printStackTrace();}
        weatheract =  findViewById(R.id.weatheract);
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            showSettingsAlert();        }


        Button diagnose =  findViewById(R.id.diagnose);
        if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            Toast.makeText( getApplicationContext(), "ERROR getting location", Toast.LENGTH_LONG).show();
            displayPromptForEnablingGPS(this);


        }

        else{ getLocation();}
        diagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  DownloadTask dx = new DownloadTask();
                if(latitude == 0 && longitude == 0){
                    Toast.makeText( getApplicationContext(), "Error Submitting. Try again later", Toast.LENGTH_LONG).show();}
                else{

                     dx.execute("http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude)+ "&lon=" +String.valueOf(longitude)+"&appid=PLACE_APP_ID_HERE");


                    try {
                        location = getAddress(getApplicationContext(), latitude, longitude).replace(",", "").trim();
                        Report report = new Report(username,  mAuth.getCurrentUser().getUid(), latitude, longitude, 1, 0, false, location, TEMP, WIND, HUM, PRESSURE);

                        if(TEMP !=0){
                            report = new Report(username, mAuth.getCurrentUser().getUid(), latitude, longitude, 1, 0, false, location, TEMP, WIND, HUM, PRESSURE);
                            String id = reports.push().getKey();
                            reports.child(id).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText( getApplicationContext(), "Diagnosis submitted" , Toast.LENGTH_LONG).show();
                                  //  weatheract.setText( String.valueOf("Temp: " + TEMP +"wind: "+ WIND + "hum: " + HUM +"press" +PRESSURE));
                                }
                            });}
                        else  Toast.makeText( getApplicationContext(), "Error Submitting. Try again later", Toast.LENGTH_LONG).show();

                    }

                    catch (Exception e){

                        e.printStackTrace();}
                }

            }
        });

        Switch toggleLocation = findViewById(R.id.locationSwitch);
        final TextView using = findViewById(R.id.using);
        final EditText currentLocation = findViewById(R.id.geolocation);




        toggleLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {currentLocation.setClickable(true);currentLocation.setFocusable(true); currentLocation.setFocusableInTouchMode(true); currentLocation.setActivated(true); using.setText("Using geolocation");  }
                else { currentLocation.setClickable(false); currentLocation.setFocusable(false);  currentLocation.setFocusableInTouchMode(false);currentLocation.setActivated(false); using.setText("Using current location");
                tagokeyboard();
                 }
            }
        });





    }
    void getLocation()
    {
        try {

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

        } else if (id == R.id.nav_report) {
            if(admin.equals("true")) startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            else Toast.makeText(getApplicationContext(), "Not an Admin", Toast.LENGTH_SHORT).show();


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
    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }
    public static void displayPromptForEnablingGPS(final Activity activity)
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
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is required");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(  Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent  );
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    } private  void tagokeyboard()
    {
        hideKeyboard(this);

    }
    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);

            String add = obj.getAddressLine(0);


            return add;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public String placesDropDown(Context context, String locName) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locName ,4);
            Address obj = addresses.get(0);

            String add = obj.getAddressLine(0);
            return String.valueOf(addresses);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}

