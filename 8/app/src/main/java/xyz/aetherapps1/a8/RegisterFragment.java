package xyz.aetherapps1.a8;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    EditText namefield, emailfield, agefield,phonenum,passwordfield;
    Spinner sexfield;
    Button registerbtn;
    CheckBox agreefield;
    DatabaseReference databaseUsers;
    boolean agree;


    private OnFragmentInteractionListener mListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        namefield = (EditText)getView(). findViewById(R.id.namefield);
        emailfield = (EditText)getView(). findViewById(R.id.emailfield);
        agefield = (EditText)getView(). findViewById(R.id.agefield);
        sexfield = (Spinner) getView(). findViewById(R.id.sex);
        phonenum = (EditText)getView(). findViewById(R.id.phonenum);
        passwordfield = (EditText)getView(). findViewById(R.id.passwordfield);
        registerbtn = (Button) getView(). findViewById(R.id.registerbtn);
        agreefield = (CheckBox) getView(). findViewById(R.id.agreefield);
        databaseUsers = FirebaseDatabase.getInstance().getReference("User");


         String name = namefield.getText().toString();
        String email = emailfield.getText().toString();
        String age = agefield.getText().toString();
        String sex = sexfield.getSelectedItem().toString();
        String password = passwordfield.getText().toString();

        if(agreefield.isChecked())  agree= true;
        else agree = false;
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
        private void register()
    {

        String name = namefield.getText().toString().trim();
        String email = emailfield.getText().toString().trim();
        String age = agefield.getText().toString().trim();
        String sex = sexfield.getSelectedItem().toString().trim();
        String password = passwordfield.getText().toString().trim();
        String phone = phonenum.getText().toString().trim();

        if(agreefield.isChecked())  agree= true;
        else agree = false;

        String userId = databaseUsers.push().getKey();
       UserInfo userInfo = new UserInfo(name,email,age,sex,phone,password,agree);

        databaseUsers.child(userId).setValue(userInfo);
        Toast.makeText(getContext(), "User registered", Toast.LENGTH_LONG).show();

    }






    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

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
