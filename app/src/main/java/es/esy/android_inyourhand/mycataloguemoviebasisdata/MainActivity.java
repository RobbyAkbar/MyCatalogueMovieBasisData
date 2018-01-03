package es.esy.android_inyourhand.mycataloguemoviebasisdata;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments.FavouriteFragment;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments.HomeFragment;
import es.esy.android_inyourhand.mycataloguemoviebasisdata.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private final static String SELECTED_TAG = "selected_index";
    private static int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState!=null){
            navigationView.getMenu().findItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            return;
        }

        selectedIndex = R.id.nav_home;

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment(), HomeFragment.class.getSimpleName()).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    public void setupNavigationDrawer(Toolbar toolbar){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_home;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(), HomeFragment.class.getSimpleName()).commit();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_search:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_search;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment(), SearchFragment.class.getSimpleName()).commit();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_favourite:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_favourite;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavouriteFragment(), FavouriteFragment.class.getSimpleName()).commit();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_setting:
                if(!item.isChecked()){
                    //selectedIndex = R.id.nav_setting;
                    //item.setChecked(true);
                    Toast.makeText(this, "Features will be added in the future", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_exit:
                if(!item.isChecked()){
                    exit();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

    private void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(R.string.dialog_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }
}
