package com.example.techognize.ui.marketplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.techognize.R;

public class MarketplaceFragment extends Fragment {

    private MarketplaceViewModel marketplaceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marketplaceViewModel =
                ViewModelProviders.of(this).get(MarketplaceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_marketplace, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        marketplaceViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}