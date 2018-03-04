package xyz.aetherapps1.a8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static xyz.aetherapps1.a8.LogInScreen.admin;
import static xyz.aetherapps1.a8.LogInScreen.email;
import static xyz.aetherapps1.a8.LogInScreen.mAuth;
import static xyz.aetherapps1.a8.LogInScreen.messagesRef;
import static xyz.aetherapps1.a8.LogInScreen.reports;
import static xyz.aetherapps1.a8.LogInScreen.totalCasesRef;
import static xyz.aetherapps1.a8.LogInScreen.user;
import static xyz.aetherapps1.a8.LogInScreen.username;
import static xyz.aetherapps1.a8.LogInScreen.verticesRef;

public class ChatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mMessageList;
    Message messagesclass; EditText editmessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


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
        ImageView profpic = hView.findViewById(R.id.profilePic);
        if(admin.equals("true")) profpic.setImageDrawable(getResources().getDrawable(R.drawable.adminprofpic));
        else profpic.setImageDrawable(getResources().getDrawable(R.drawable.normalprofpic));
        try{
            nav_name.setText(username.toUpperCase());}
        catch (Exception e ) {e.printStackTrace();}

        final MultiAutoCompleteTextView messagehere = findViewById(R.id.texthere);
        Button sendbtn = findViewById(R.id.sendhere);
        final Spinner address = findViewById(R.id.addresshere);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String context = messagehere.getText().toString().trim();

                Message message = new Message(context, mAuth.getCurrentUser().getUid(), address.getSelectedItem().toString().trim(), 0.1231);
                messagesRef.push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
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

            if(admin.equals("true")) startActivity(new Intent(getApplicationContext(), ReportActivity.class));
            else Toast.makeText(getApplicationContext(), "Not an Admin", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_track) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));


        } else if (id == R.id.nav_alerts) {
            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}
