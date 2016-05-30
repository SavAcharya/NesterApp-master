package in.sayes.nestorapp.base.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sayes.nestorapp.R;

/**
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp.profile
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
/**
 * A simple {@link android.app.Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    protected abstract void getDataFromBundle();

}
