package es.esy.android_inyourhand.mycataloguemoviebasisdata.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments.ViewPagerFragment;

/**
  Created by robby on 01/01/18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] title = new String[]{
            "Now Playing", "Upcoming"
    };


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putString("Menu", "movie/now_playing");
                fragment.setArguments(bundle);
                break;
            case 1:
                bundle.putString("Menu", "movie/upcoming");
                fragment.setArguments(bundle);
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
