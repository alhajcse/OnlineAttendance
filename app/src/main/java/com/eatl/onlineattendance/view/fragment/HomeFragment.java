package com.eatl.onlineattendance.view.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.eatl.onlineattendance.R;
import com.eatl.onlineattendance.databinding.NavigationHomeFragmentBinding;
import com.eatl.onlineattendance.model.Datum;
import com.eatl.onlineattendance.view.adapter.ItemAdapter;
import com.eatl.onlineattendance.viewmodel.HomeViewModel;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    NavigationHomeFragmentBinding binding;
    HomeViewModel homeViewModel;
    ItemAdapter adapter;


    List<Datum> movies=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.navigation_home_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //homeViewModel=new HomeViewModel()
        binding.setHomeViewModel(homeViewModel);

        movies=new ArrayList<>();
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ItemAdapter(getActivity(), new ItemAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Datum datum) {
                Toast.makeText(getActivity(), datum.getName(), Toast.LENGTH_LONG).show();
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToInputeFragment(datum));
            }
        });

        homeViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(PagedList<Datum> data) {
                adapter.submitList(data);

            }
        });
        binding.recyclerView.setAdapter(adapter);

    }





}