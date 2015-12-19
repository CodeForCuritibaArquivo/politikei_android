package politikei.com.br.politikei;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import io.fabric.sdk.android.Fabric;
import politikei.com.br.politikei.activity.LoginActivity;
import politikei.com.br.politikei.database.DataBase;
import politikei.com.br.politikei.fragment.FragmentProjetosLei;
import politikei.com.br.politikei.utils.Utils;

public class MainActivity extends AppCompatActivity implements FragmentProjetosLei.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.logo);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              startLogic();
            }
        }, 3000);

        //Using this 3secs to load facebook
        FacebookSdk.sdkInitialize(this);
    }

    private void startLogic() {
        if (!DataBase.getInstance(MainActivity.this).isAccountSet()) {
            //Creating an account
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, Utils.tagStartActivityLogin);
        } else {
            init();
        }
    }

    private void init() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
//
//        new Handler().post(new Runnable() {
//            public void run() {
//                FragmentProjetosLei fragmentProjetoLei = new FragmentProjetosLei();
//
//                //ItemFragment fragment = new ItemFragment();
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.fragmentList, fragmentProjetoLei);
//                fragmentTransaction.commit();
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(resultCode, requestCode, intent);

        switch (requestCode) {
            case Utils.tagStartActivityLogin:
                if (resultCode == Activity.RESULT_OK) {
                    init();
                    break; //Only starts if really worked. otherwise, close the app.
                }
            default:
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_detail_project, menu);
        return true;
    }

    @Override
    public void onItemSelected(String id) {
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
//        Intent detailIntent = new Intent(this, ActivityDetailProject.class);

//        startActivity(detailIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
