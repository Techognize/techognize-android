package com.example.techognize.ui.account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.techognize.LoginActivity;
import com.example.techognize.MainActivity;
import com.example.techognize.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    Button buttonSignOut;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user;

    TextView textName;
    TextView textEmail;
    ImageView imageProfile;

    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        accountViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        buttonSignOut = root.findViewById(R.id.id_buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mGoogleSignInClient.signOut();
            }
        });

        textName = root.findViewById(R.id.id_textName);
        textEmail = root.findViewById(R.id.id_textEmail);
        imageProfile = root.findViewById(R.id.id_imageProfile);

        if (user != null) {
            textName.setText(user.getDisplayName());
            textEmail.setText(user.getEmail());
            RetrievePhotoTask retrievePhotoTask = new RetrievePhotoTask();
            retrievePhotoTask.execute();
//            Log.d("SHAH", photoUrl + "");
//            Glide.with(this).load(photoUrl.getPath()).into(imageProfile);
        }

        return root;

    }

    public class RetrievePhotoTask extends AsyncTask<Void, Void, Drawable> {


        @Override
        protected Drawable doInBackground(Void... voids) {

            try {
                URL photoURL = new URL(user.getPhotoUrl() + "");
                InputStream inputStream = (InputStream) photoURL.getContent();
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                return drawable;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            imageProfile.setImageDrawable(drawable);
        }
    }

}