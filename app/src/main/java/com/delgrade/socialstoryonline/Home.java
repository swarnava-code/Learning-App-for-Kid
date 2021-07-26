package com.delgrade.socialstoryonline;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    CardView funtime;
    int clickTime = 0;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavigationView navigationView = findViewById(R.id.nav_view);


        // funtime = findViewById(R.id.funtime);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString("key", "Covid Story");
                Intent i = new Intent(getApplicationContext(), SocialStoryActivity.class);
                i.putExtras(b);
                startActivity(i);

                //Snackbar.make(view, "Version 2021, STCET, CSE DEPARTMENT", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                /*

                 */


            }
        });
        drawer = findViewById(R.id.drawer_layout);

        //navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //navigationView.setNavigationItemSelectedListener(this);

/*        try {
            GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.a );
            gifFromResource.
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Toast.makeText(this, "Share app!", Toast.LENGTH_LONG).show();
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Social Story v2021");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=7807632365798190244";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                return true;

            case R.id.action_todays_story:
                String[] arr = {"Hygiene", "Birthday", "Angry", "Ask For Help", "Brushing", "School Time", "Hide and Seek", "Hitting Others", "Making Noise", "Personal Space", "Playing Games", "Sharing", "Summer Vacation", "Taking Turns", "School Work"};
                Random random = new Random();
                int rand = random.nextInt(arr.length);

                Bundle b = new Bundle();
                b.putString("key", arr[rand]);
                Intent i = new Intent(getApplicationContext(), SocialStoryActivity.class);
                i.putExtras(b);
                startActivity(i);

                //Toast.makeText(this, "Today\'s Story!", Toast.LENGTH_LONG).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Toast.makeText(this, menu.toString(), Toast.LENGTH_LONG).show();
        getMenuInflater().inflate(R.menu.activity_home, menu);
/*        switch (menu.getItemId()) {
            case R.id.menu_item:
                // Action goes here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*public void play_math(View view) {
        startActivity(new Intent(getApplicationContext(), Mathematics.class));
    }

    public void play_english(View view) {
        startActivity(new Intent(getApplicationContext(), English.class));
    }

    public void play_social_story(View view) {
        startActivity(new Intent(getApplicationContext(), SocialStoryMenuActivity.class));
    }
        public void funtime(View view) {
        Intent i = new Intent(Home.this, FunTimeMenu.class);
        startActivity(i);
    }*/

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(Home.this)
                .setTitle("Want to Exit ?")
                .setIcon(R.drawable.logo)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        //finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
/*        int id= item.getItemId();
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        if (id == R.id.nav_home) {
            Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            getFragmentManager().beginTransaction()
                    .replace(R.id., fragment)
                    .commit();

        } else if (id == R.id.nav_slideshow) {

        }  else if (id == R.id.nav_share) {

        }*/


/*        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            Toast.makeText(this, id, Toast.LENGTH_LONG).show();
            loadFragment(new GalleryFragment());
        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + "7807632365798190244" + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        } else if (id == R.id.nav_home) {
            loadFragment(new HomeFragment());
        }else if (id == R.id.nav_slideshow) {
            loadFragment(new SlideshowFragment());
        }*/

        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }
}