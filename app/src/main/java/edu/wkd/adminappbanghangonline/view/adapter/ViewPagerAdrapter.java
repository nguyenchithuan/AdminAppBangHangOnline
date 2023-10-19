package edu.wkd.adminappbanghangonline.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import edu.wkd.adminappbanghangonline.view.fragment.RevenueFragment;
import edu.wkd.adminappbanghangonline.view.fragment.TopProductFragment;

public class ViewPagerAdrapter extends FragmentStateAdapter {

    public ViewPagerAdrapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = TopProductFragment.newInstance();
                break;
            case 1:
                fragment = RevenueFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
