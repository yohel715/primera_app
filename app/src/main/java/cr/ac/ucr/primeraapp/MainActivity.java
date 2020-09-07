package cr.ac.ucr.primeraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import cr.ac.ucr.primeraapp.adapters.MainViewPagerAdapter;
import cr.ac.ucr.primeraapp.fragments.ToDoListFragment;
import cr.ac.ucr.primeraapp.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private String todoStr;
    private PagerAdapter pagerAdapter;
    private ViewPager vpPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vpPager = findViewById(R.id.vp_pager);

        bottomNavigationView = findViewById(R.id.bnv_bottom_menu);

        setUpViewpagerListener();

        setupBottomNavViewListener();

        setUpViewPager();
    }

    private void setUpViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(ToDoListFragment.newInstance());
        fragments.add(ToDoListFragment.newInstance());
        // ViewPager <-----> Adapter <----> ArrayList
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpPager.setAdapter(mainViewPagerAdapter);
    }

    private void setupBottomNavViewListener() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_tasks:
                        vpPager.setCurrentItem(0);
                        return true;
                    case R.id.profile:
                        vpPager.setCurrentItem(1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setUpViewpagerListener() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(){
        AppPreferences.getInstance(this).clear();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

    }

}