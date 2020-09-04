package cr.ac.ucr.primeraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.primeraapp.fragments.PageFragment1;
import cr.ac.ucr.primeraapp.fragments.PageFragment2;
import cr.ac.ucr.primeraapp.fragments.PageFragment3;
import cr.ac.ucr.primeraapp.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<String> todosArr;
    private ArrayAdapter<String> todosAdapter;
    private ListView lvTodos;

    private Gson gson;
    private String todoStr;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gson = new Gson();

        //ListView <--> ArrayAdapter <--> ArrayList
        lvTodos = findViewById(R.id.lv_todos);
        todosArr = new ArrayList<>();
        todoStr = AppPreferences.getInstance(this).getString(AppPreferences.keys.ITEMS);

        if(!todoStr.equals("")) { //!todo.isEmpty()
            String[] todoArray = gson.fromJson(todoStr, String[].class);
            todosArr.addAll(Arrays.asList(todoArray));
        }
        todosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , todosArr);

        lvTodos.setAdapter(todosAdapter);

        //todosArr.add("prueba");
        setupListViewListener();

        //-------PAGER VIEW
        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment1());
        list.add(new PageFragment2());
        list.add(new PageFragment3());

        pager = findViewById(R.id.pager);
        pagerAdapter = new SliderPageAdapter(getSupportFragmentManager(), list);

        pager.setAdapter(pagerAdapter);

    }

    private void setupListViewListener(){
        final AppCompatActivity activity = this;
        lvTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setMessage(R.string.want_to_delete)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                todosArr.remove(position);
                                todosAdapter.notifyDataSetChanged();

                                //todoStr = gson.toJson(todosArr);
                                //AppPreferences.getInstance(activity).put(AppPreferences.keys.ITEMS, todoStr);
                                saveListToPreferences();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .create()
                        .show();
                return true;
            }
        });

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
            case R.id.clean_list:
                cleanList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void cleanList(){
        todosArr.clear();
        todosAdapter.notifyDataSetChanged();
        saveListToPreferences();
    }
    private  void saveListToPreferences(){
        todoStr = gson.toJson(todosArr);
        AppPreferences.getInstance(this).put(AppPreferences.keys.ITEMS, todoStr);
    }
    private void logout(){
        AppPreferences.getInstance(this).clear();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_todo:
                showAlert();
                break;
            default:
                break;
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //cargar el layout flotante
        LayoutInflater inflater = getLayoutInflater();
        //cargar la vista del layout
        final View view =  inflater.inflate(R.layout.dialog_add_task, null);

        final AppCompatActivity activity = this; //requiere el contexto global

        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //capturar el tap
                        TextInputEditText etTaskName = view.findViewById(R.id.et_task_name);

                        String taskName = etTaskName.getText().toString();
                        if(!taskName.isEmpty()){
                            todosArr.add(taskName);
                            todosAdapter.notifyDataSetChanged();

                            //todoStr = gson.toJson(todosArr);
                            //AppPreferences.getInstance(activity).put(AppPreferences.keys.ITEMS, todoStr);
                            saveListToPreferences();
                            dialog.dismiss();
                        }
                    }
                })
                //null cierra la alter
                .setNegativeButton(R.string.cancel, null);
        builder.create();
        builder.show();
    }
}