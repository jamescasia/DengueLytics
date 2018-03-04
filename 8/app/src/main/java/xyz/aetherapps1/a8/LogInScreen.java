package xyz.aetherapps1.a8;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import static android.view.View.GONE;

public class LogInScreen extends AppCompatActivity implements LogInFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener{

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

        Animation loginanim = AnimationUtils.loadAnimation(this, R.anim.loginanim);
        fragmentspace.setAnimation(loginanim);
        fragmentspace.animate();

        roboto = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentspace, new LogInFragment()).commit();




    }

    public LogInScreen()
    {
    //
    }
     public void Registerclicked()
        {

        }

}
