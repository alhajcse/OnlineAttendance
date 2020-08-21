package com.eatl.onlineattendance.view.fragment;
import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.eatl.onlineattendance.R;
import com.eatl.onlineattendance.databinding.NavigationHomeFragmentBinding;
import com.eatl.onlineattendance.model.Datum;
import com.eatl.onlineattendance.model.ItemModel;
import com.eatl.onlineattendance.network.NetworkApiClient;
import com.eatl.onlineattendance.network.NetworkApiInterface;
import com.eatl.onlineattendance.view.adapter.ItemAdapter;
import com.eatl.onlineattendance.viewmodel.HomeViewModel;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomeFragment extends Fragment {

    NavigationHomeFragmentBinding binding;
    private PagedList<Datum> datumPagedList;
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

        //creating the Adapter
        // final ItemAdapter adapter = new ItemAdapter(getActivity());
        //setting the adapter
        // binding.recyclerView.setAdapter(adapter);
//        homeViewModel.getStoreList(1).observe(this, new Observer<List<Datum>>() {
//            @Override
//            public void onChanged(List<Datum> data) {
//
//
//                movies.clear();
//                movies.addAll(data);
//                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                binding.recyclerView.setHasFixedSize(true);
//
//                moviesAdapter = new MoviesAdapter(getActivity(),movies);
//                binding.recyclerView.setAdapter(moviesAdapter);
//
//                //  adapter.submitList(datumPagedList);
//
//                moviesAdapter.notifyDataSetChanged();
//
//
//
//                moviesAdapter.setLoadMoreListener(new MoviesAdapter.OnLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//
//                        binding.recyclerView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                int index = movies.size()-1;
//                                loadMore(index);
//                            }
//                        });
//                        //Calling loadMore function in Runnable to fix the
//                        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
//                    }
//                });
//
//
//
//            }
//        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerView.setHasFixedSize(true);

        adapter = new ItemAdapter(getActivity(), new ItemAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Datum datum) {
                Toast.makeText(getActivity(), datum.getName(), Toast.LENGTH_LONG).show();
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToInputeFragment(datum));
            }
        });
        //adapter.submitList(datumPagedList);
        //observing the itemPagedList from view model
        homeViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(PagedList<Datum> data) {
                Log.e("modelVs",""+data.size());
                adapter.submitList(data);
                //datumPagedList=data;
                // showOnRecyclerView();
            }
        });
        binding.recyclerView.setAdapter(adapter);






        final NavController navController=Navigation.findNavController(view);
        // View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_firstFragment_to_secondFragment);
        // Button button = view.findViewById(R.id.button_frag1);
        // button.setOnClickListener(s);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                HomeFragmentDirections.ActionHomeFragmentToInputeFragment actionHomeFragmentToInputeFragment=HomeFragmentDirections.actionHomeFragmentToInputeFragment();
//
//                actionHomeFragmentToInputeFragment.getArguments().putString("message","manu");
//
//
//                navController.navigate(actionHomeFragmentToInputeFragment);
//
//            }
//        });
    }


    private void showOnRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        //adapter = new ItemAdapter(getActivity());
        adapter.submitList(datumPagedList);
        adapter.notifyDataSetChanged();
    }


}