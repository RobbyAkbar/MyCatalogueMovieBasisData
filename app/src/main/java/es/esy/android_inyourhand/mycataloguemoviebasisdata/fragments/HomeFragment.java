package es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.MainActivity;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.R;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Toolbar toolbar;
    private MainActivity mainActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = view.findViewById(R.id.tab_toolbar);
        mainActivity.setSupportActionBar(toolbar);
        ViewPager viewPager = view.findViewById(R.id.tab_view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));

        return view;
    }

}
