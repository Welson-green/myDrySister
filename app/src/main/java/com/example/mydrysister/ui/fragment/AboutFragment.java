package com.example.mydrysister.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydrysister.R;
import com.example.mydrysister.utils.PackageUtils;
import com.example.mydrysister.utils.ResUtils;

public class AboutFragment extends Fragment {

    private TextView tv_app_version;

    public static AboutFragment newInstance(){
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about,container,false);
        tv_app_version = view.findViewById(R.id.tv_app_version);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String version = PackageUtils.packageName();
        if(version != null){
            String msg = String.format(ResUtils.getString(R.string.app_version),version);
            tv_app_version.setText(msg);
        }
    }


}
