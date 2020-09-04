package cr.ac.ucr.primeraapp;

import android.transition.Slide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.slider.Slider;

import java.util.List;

public class SliderPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    public SliderPageAdapter(FragmentManager fm,  List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
    return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
