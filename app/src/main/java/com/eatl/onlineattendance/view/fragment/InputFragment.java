package com.eatl.onlineattendance.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.eatl.onlineattendance.R;
import com.eatl.onlineattendance.databinding.NavigationInputFragmentBinding;
import com.eatl.onlineattendance.model.AttendanceModel;
import com.eatl.onlineattendance.model.Datum;
import com.eatl.onlineattendance.utility.GPSTracker;
import com.eatl.onlineattendance.viewmodel.InputViewModel;

import java.math.BigInteger;
import java.util.Random;


public class InputFragment extends Fragment {


    NavigationInputFragmentBinding binding;
    String latStr,longStr,userName,userID;

    InputViewModel inputViewModel;

    GPSTracker gpsTracker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.navigation_input_fragment,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gpsTracker = new GPSTracker(getActivity());
        binding.setInputViewModel(inputViewModel);

        inputViewModel = ViewModelProviders.of(this).get(InputViewModel.class);


        getLocation();
        getAlphaNumericString();
       // test();

        binding.useridInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                userID=charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                userName=charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Log.e("latt",""+latStr);
        Log.e("long",""+longStr);

        Datum datum = InputFragmentArgs.fromBundle(getArguments()).getItem();

        Toast.makeText(getActivity(), "The item id is " + datum.getId(), Toast.LENGTH_LONG).show();



        inputViewModel.getIsLoading().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){
                    binding.progressBar.setVisibility(View.VISIBLE);
                }else {
                    binding.progressBar.setVisibility(View.GONE);

                }

            }
        });

       binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLocation();
                String req_id= getAlphaNumericString();

                if(validateName(userName) && validateUID(userID) && validateLatitude(latStr) && validateLongitude(longStr)) {

                    sendOnlineAttandace(userName, userID, latStr, longStr, req_id);

                }


            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void getLocation(){

        // String location="";

        try {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            latStr=String.valueOf(latitude);
            longStr=String.valueOf(longitude);
            //return location;

        } catch (Exception e) {

            e.printStackTrace();
        }

        // return location;

    }


    Random random = new Random(System.currentTimeMillis());



    public String getAlphaNumericString() {

            String n = BigInteger.valueOf(Math.abs(random.nextLong())).toString(32).toUpperCase();
            if (n.length() > 8) {
                if (n.length() > 10) {
                    n = n.substring(n.length() - 10);
                }
                System.out.println(n);
                Log.e("unic",""+n);

            }

            return n;
    }


    public void sendOnlineAttandace(String name,String uid,String latitude,String longitude,String req_id){


        inputViewModel.setOnlineAttandance(name,uid,latitude,longitude,req_id).observe(getActivity(), new Observer<AttendanceModel>() {
            @Override
            public void onChanged(AttendanceModel attendanceModel) {


                if(attendanceModel!=null){

                    if(attendanceModel.getAppMessage().contains("Success")){

                        Toast.makeText(getActivity(),""+attendanceModel.getUserMessage(), Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(getActivity(),""+attendanceModel.getUserMessage(), Toast.LENGTH_SHORT).show();
                    }


                }




            }
        });



    }



    private boolean validateName(String userName) {
        if(userName == null || userName.trim().length()<=0)
        {
            Toast.makeText(getActivity(),"Enter User Name.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    private boolean validateUID(String userID) {
        if(userID == null || userID.trim().length()<=0)
        {
            Toast.makeText(getActivity(),"Enter User ID.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private boolean validateLatitude(String lat) {
        if(lat == null || lat.trim().length()<=0)
        {
            Toast.makeText(getActivity(),"Given user Location.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateLongitude(String lon) {
        if(lon == null || lon.trim().length()<=0)
        {
            Toast.makeText(getActivity(),"Given user Location.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }




}
