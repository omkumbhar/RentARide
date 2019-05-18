package com.omkar.kumbhar.rentaride.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.omkar.kumbhar.rentaride.R;



import java.util.List;

public class Upload extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FragmentActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_license_upload);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof LicenseUploadFragment) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }

    }
}
