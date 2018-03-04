package xyz.aetherapps1.a8;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import static xyz.aetherapps1.a8.DiagnoseActivity.latitude;
import static xyz.aetherapps1.a8.DiagnoseActivity.longitude;
import static xyz.aetherapps1.a8.LogInScreen.admin;
import static xyz.aetherapps1.a8.LogInScreen.databaseUsers;
import static xyz.aetherapps1.a8.LogInScreen.email;
import static xyz.aetherapps1.a8.LogInScreen.mAuth;
import static xyz.aetherapps1.a8.LogInScreen.propertime;
import static xyz.aetherapps1.a8.LogInScreen.reports;
import static xyz.aetherapps1.a8.LogInScreen.totalCasesRef;
import static xyz.aetherapps1.a8.LogInScreen.user;
import static xyz.aetherapps1.a8.LogInScreen.username;
import static xyz.aetherapps1.a8.LogInScreen.verticesRef;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    public static int totalcases;
    JSONObject student1;
    JSONObject verticesCSV;
    private GoogleMap mMap;
    ArrayList<Integer> totalcasesarray;
    LineGraphSeries<DataPoint> series;
    Double lasty;
    ArrayList<String> xAxes;
    public static String locationplace;
    String timeofreport;
    ArrayList<String> realtimevalues;
    ArrayList<Long> timevalues;
    ArrayList<Double> locationlat;
    public static ArrayList<Double> tempValues;
    public static ArrayList<Double> humidityValues;
    public static ArrayList<Double> pressureValues;
    public static ArrayList<Double> windSpeedValues;
    public static ArrayList<String> usernameValues;
    ArrayList<Double> locationlong;
    ArrayList<String> places;
    boolean checked = false;
    LineChart lineChart;
    ArrayList<Entry> yAxes;
    DataPoint dataPoint;
    TextView skyDescView;
    TextView tempView;
    TextView skyView;
    boolean connected;
    TextView humView;
    TextView windView;
    TextView pressView;
    LinearLayout profPic;
    long x;
    GraphView graph;
    int y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        verticesCSV = new JSONObject();
        tempValues = new ArrayList<   >();
        humidityValues = new ArrayList<    >();
        pressureValues = new ArrayList<  >();
        windSpeedValues = new ArrayList<  >();
        usernameValues = new ArrayList<  >();
       // downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?lat=" +String.valueOf(latitude) +"&lon=" +String.valueOf(longitude) + "&appid=389915ca20f3fde572ac9a9ff77593e9");




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =   findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =   findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        }
        else connected = false;

        TextView nav_name =  hView.findViewById(R.id.nav_name);
        TextView nav_email = hView.findViewById(R.id.nav_email);
        ImageView profpic = hView.findViewById(R.id.profilePic);

        nav_email.setText(email);
        try{
        nav_name.setText( username .toUpperCase());}
        catch (Exception e){e.printStackTrace();}
        if(admin.equals("true")) profpic.setImageDrawable(getResources().getDrawable(R.drawable.adminprofpic));
        else profpic.setImageDrawable(getResources().getDrawable(R.drawable.normalprofpic));


          tempView = findViewById(R.id.tempView);
          skyDescView= findViewById(R.id.skyDescView);
          skyView = findViewById(R.id.skyView);
          humView = findViewById(R.id.humView);
          pressView = findViewById(R.id.pressureView);
          windView = findViewById(R.id.windspeedView);
        //  profPic.setBackground(   getDrawable(R.drawable.ic_menu_send));


     //   while(tempView.getText().toString().trim().equals("") || Double.parseDouble(tempView.getText().toString().trim()  )==0.0 )
      //  {

          //  skyView.setText("Sky is: "+dt.getSky());
          //  pressView.setText("pressure is " + dt.getPressure());
           // humView.setText("humidity: " + dt.getHumidity());


      //  }



        realtimevalues = new ArrayList<String>();
        locationlong = new ArrayList<Double>();
        places = new ArrayList<String>();
        locationlat = new ArrayList<Double>();


        graph = (GraphView)findViewById(R.id.graph);
        graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values//
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX)  ;
                }
            }
        });


        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
   // graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        //graph.getGridLabelRenderer().setNumHorizontalLabels(10);
        // Date date = new Date(Integer.parseInt(""+value));
        // Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
         series = new LineGraphSeries<DataPoint>();

        series.setColor(R.color.colorAccent);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
    //    paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.STROKE);
       // series.setCustomPaint(paint);
        series.setDrawDataPoints(true);

        final TextView cases = (TextView)findViewById(R.id.cases);
        totalCasesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalcases =Integer.parseInt(dataSnapshot.getValue().toString());
                cases.setText("CONFIRMED: " +totalcases);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        verticesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> vertices = dataSnapshot. getChildren();


                //Iterable<Integer> yvalues;
                  timevalues= new ArrayList<>();
                totalcasesarray= new ArrayList<>();
                for(DataSnapshot a :vertices){

                    y= Integer.parseInt (a.child("totalcases").getValue().toString().trim());

                     x=Long.parseLong(a.child("time").getValue().toString().trim()) ;
                   //  timevalues.add(x);
                    totalcasesarray.add(y);
//                  series.appendData(new DataPoint( x  , y), false, 999999999);
                }
                checked = true;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        if(checked){

        verticesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> vertices = dataSnapshot. getChildren();
                for(DataSnapshot a :vertices){

                    y= Integer.parseInt (   a.child("totalcases").getValue().toString().trim()   );

                    x=Long.parseLong(a.child("time").getValue().toString().trim()) ;


                }
               // timevalues.add(x);
                totalcasesarray.add(y);

                series.appendData(new DataPoint( x  , y), false, 999999999);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); }




        propertime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> signofthetimes = dataSnapshot.getChildren();

                for(DataSnapshot o :signofthetimes)
                {

                    timeofreport = o.getValue().toString();
                    realtimevalues.add(timeofreport);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    //    series.resetData(DataPoint[]{});
        graph.addSeries(series);
        series.setColor(getResources().getColor(R.color.colorAccent));

    //  graph.getViewport().setMinY(0);
       // graph.getViewport().setMaxY(series.getHighestValueY());


      //  graph.getViewport().setMinX(0);
        //graph.getViewport().setMaxX(series.getHighestValueX());

        Button exportCSV = findViewById(R.id.exportCSV);
        exportCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWritingPermission();
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {


                        ActivityCompat.requestPermissions(HomeActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                101);

                    }
                }

                else generateNoteOnSD(getApplicationContext(), "DengueLytics-Log.csv", " ");


            }
        });
    }
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                generateNoteOnSD(getApplicationContext(), "DengueLytics-Log.csv", " ");
            } else {
                // permission wasn't granted
            }
        }
    }

    private void checkWritingPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // permission wasn't granted
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                generateNoteOnSD(getApplicationContext(), "DengueLytics-Log.csv", " ");
            }
        }
    }







    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        reports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> casereports = dataSnapshot.getChildren();

                for(DataSnapshot a :casereports)
                {
                    String username = (a.child("username").getValue().toString());
                    double lat = Double.parseDouble(a.child("latitude").getValue().toString());
                    double lon = Double.parseDouble(a.child("longitude").getValue().toString());
                    double temperature = Double.parseDouble(a.child("temperature").getValue().toString());
                    double humidity = Double.parseDouble(a.child("humidity").getValue().toString());
                    double pressure = Double.parseDouble(a.child("pressure").getValue().toString());
                    double windspeed  = Double.parseDouble(a.child("windspeed").getValue().toString());

                    tempValues.add(temperature);windSpeedValues.add(windspeed);pressureValues.add(pressure);humidityValues.add(humidity);usernameValues.add(username);
                    String con = a.child("confirmed").getValue().toString();

                    locationlat.add(lat);
                    locationlong.add(lon);
                    MarkerOptions mo = new MarkerOptions();
                    places.add(getAddress(getApplicationContext(), lat,lon) );
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).alpha(0.8f).position(new LatLng(lat, lon)).snippet(username    );
                    if( lat!=0   && lon!=0) {
                        if (con == "true"){


                        mo.title("INFECTED"); }
                        else{ mo.title("UNCONFIRMED");}
                       mMap.addMarker(mo);
                        float zoomLevel = 18f; //This goes up to 21
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), zoomLevel));


                    }
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });/**

        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                 zoom = 8;
                 x= 10;
                 y=123;

                String s = String.format(Locale.US, "http://tile.openweathermap.org/map/pressure/%d/%d/%d.png?appid=389915ca20f3fde572ac9a9ff77593e9",
                        zoom, x, y);


                try {
                    return new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
            }

            /*
             * Check that the tile server supports the requested x, y and zoom.
             * Complete this stub according to the tile range you support.
             * If you support a limited range of tiles at different zoom levels, then you
             * need to define the supported x, y range at each zoom level.


        };

        TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
        */
    }


    /* Define the URL pattern for the tile images */
             //   String s = String.format(Locale.US, "http://tile.openweathermap.org/map/precipitation/21/10/123.png?appid=389915ca20f3fde572ac9a9ff77593e9",
               //         zoom, x, y);


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        System.out.println(Calendar.getInstance().getTime());
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
            startActivity(new Intent(getApplicationContext(), LogInScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
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


        } else if (id == R.id.nav_alerts) {
            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));


        } else if (id == R.id.nav_send) {
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
                File root = new File(Environment.getExternalStorageDirectory(), "DengueLytics");
                if (!root.exists())
                {
                        root.mkdirs();
                }
                File gpxfile = new File(root, sFileName);
                gpxfile.delete();
            FileWriter writer = new FileWriter(gpxfile);
            writer.append("Time, Total Cases ,Name, Latitude, Longitude, Place, Temperature, Humidity, Pressure, WindSpeed \n");
            int index = 0;
            try{
            for(String b : realtimevalues) {
                b = b.replace(", ", " ");
                writer.append(""+ b + ",");

                writer.append(String.valueOf(totalcasesarray.get(index) +","+ usernameValues.get(index)+  "," + locationlat.get(index) + "," + locationlong.get(index) + "," + places.get(index).replace(",", "") +","
                        + (tempValues.get(index)) + "," + (humidityValues.get(index)) + "," + (pressureValues.get(index)) + "," + (windSpeedValues.get(index))   ) +"\n"  );

                index++;
            }}
            catch (Exception e ){e.printStackTrace();}


            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
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
}
