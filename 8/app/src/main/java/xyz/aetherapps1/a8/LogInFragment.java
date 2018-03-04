package xyz.aetherapps1.a8;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;

//import static xyz.aetherapps1.a8.LogInScreen.roboto;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LogInFragment() {
        // Required empty public constructor
    }


  @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

      Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");


      //  welcome.setTypeface(roboto);
        final LinearLayout loginbox = (LinearLayout)getView().findViewById(R.id.loginbox);
      final TextView registerlink = (TextView)getView().findViewById(R.id.registerlink);
        TextView welcome = (TextView)getView().findViewById(R.id.welcome);

welcome.setTypeface(roboto);
      registerlink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {




              getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentspace, new RegisterFragment()).addToBackStack(null).commit();



          }
      });
        loginbox.getChildAt(3).setOnClickListener(new View.OnClickListener() {
            EditText unamefield = (EditText) loginbox.getChildAt(1);
            EditText passfield = (EditText) loginbox.getChildAt(2);
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentspace, new LogInFragment()).addToBackStack(null).commit();

                if(unamefield.getText().toString().   equals("docjuan") && passfield.getText().toString().equals("qwerty") )
                {

                    Intent intent = new Intent(getContext(), MainActivity.class);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(),"Invalid User or Wrong Password", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
