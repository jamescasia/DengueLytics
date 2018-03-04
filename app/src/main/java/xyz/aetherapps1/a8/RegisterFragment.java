package xyz.aetherapps1.a8;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static xyz.aetherapps1.a8.LogInScreen.database;
import static xyz.aetherapps1.a8.LogInScreen.mAuth;
import static xyz.aetherapps1.a8.LogInScreen.databaseUsers;
import static xyz.aetherapps1.a8.LogInScreen.user;


public class RegisterFragment extends Fragment {

    ProgressDialog progressDialog;
     public static UserInfo userInfo;
    LogInScreen logInScreen = new LogInScreen();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    EditText namefield, emailfield, agefield,phonenum,passwordfield;
    Spinner sexfield;
    Button registerbtn;
    CheckBox agreefield;

    boolean agree;
    LogInFragment logInFragment;


    private OnFragmentInteractionListener mListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) { final EditText namefield = getView().findViewById(R.id.namefield);
        final EditText emailfield = getView().findViewById(R.id.emailfield);
        final EditText agefield = getView().findViewById(R.id.agefield);
        final Spinner sexfield = getView().findViewById(R.id.sex);
        final EditText phonefield = getView().findViewById(R.id.phonefield);
        final EditText passwordfield = getView().findViewById(R.id.passwordfield);
        final CheckBox agreefield = getView().findViewById(R.id.agreefield);
        final Button registerbtn = getView().findViewById(R.id.registerbtn);

        namefield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) namefield.setHint("");
                else namefield.setHint("name");
            }
        });
        emailfield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) emailfield.setHint("");
                else emailfield.setHint("email");
            }
        });
        agefield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) agefield.setHint("");
                else agefield.setHint("age");
            }
        });
        phonefield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) phonefield.setHint("");
                else phonefield.setHint("phone");
            }
        });
        passwordfield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) passwordfield.setHint("");
                else passwordfield.setHint("password");
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = namefield.getText().toString().trim();
                String email = emailfield.getText().toString().trim();
                String age = agefield.getText().toString().trim();
                String sex = sexfield.getSelectedItem().toString().trim();
                String password = passwordfield.getText().toString().trim();
                String phone = phonefield.getText().toString().trim();





                if(!TextUtils.isEmpty(email )&&!TextUtils.isEmpty(password ) &&!TextUtils.isEmpty(name )&&!TextUtils.isEmpty(age )  &&!TextUtils.isEmpty(sex )  &&!TextUtils.isEmpty(phone ) && agreefield.isChecked())
                {

                    if(password.length() < 6)
                    {
                        Toast.makeText(getContext(), "Password too short", Toast.LENGTH_LONG).show();
                    }

                    else{  userInfo = new UserInfo(name, email,age,sex,phone, "false");


                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( getActivity(),  new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    user = mAuth.getCurrentUser();
                                    String id = user.getUid();

                                    databaseUsers.child(id).setValue(userInfo);


                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName( userInfo.getName()).build();
                                    user.updateProfile(profileUpdates);
                                    Toast.makeText(getContext(), "User registered", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }


                                else Toast.makeText(getActivity().getApplicationContext(), "Error registering", Toast.LENGTH_LONG).show();
                            }


                        });



                    }}
                else Toast.makeText(getActivity().getApplicationContext(), "Required fields are empty", Toast.LENGTH_LONG).show();




            }
        });


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
