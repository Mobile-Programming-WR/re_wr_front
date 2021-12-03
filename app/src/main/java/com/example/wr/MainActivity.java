package com.example.wr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.db.PreferenceManager;
import com.example.wr.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wr.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private View header;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setSupportActionBar(binding.appBarMain.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        DrawerLayout drawer = binding.drawerLayout;
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_running, R.id.nav_record, R.id.nav_friend, R.id.nav_competition,R.id.nav_challenge,R.id.nav_setting)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 토큰 확인
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String token = preferences.getString("token","");
        String name = preferences.getString("name","");
        if(token==null||token.equals("")){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(intent);
            return;
        } else {
            Log.d("메인엑티비티 token : ", token);
//            header = getLayoutInflater().inflate(R.layout.nav_header_main, null, false);
//            TextView tvName = (TextView) header.findViewById(R.id.tvNavHeaderName);
//            tvName.setText(name);
//            return;
            View nav_header_view = navigationView.getHeaderView(0);
            TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.tvNavHeaderName);
            nav_header_id_text.setText(name);
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}