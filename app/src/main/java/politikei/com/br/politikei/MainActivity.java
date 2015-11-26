package politikei.com.br.politikei;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements FragmentProjetosLei.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BusinessLogic.getInstance().getAccessToken() == null) {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivityForResult(intent, Utils.tagStartActivityLogin);
        } else {
//            if(savedInstanceState != null) {
            init();
//            }
        }
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        new Handler().post(new Runnable() {
            public void run() {
                FragmentProjetosLei fragmentProjetoLei = new FragmentProjetosLei();

                //ItemFragment fragment = new ItemFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentList, fragmentProjetoLei);
                fragmentTransaction.commit();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(resultCode, requestCode, intent);


        switch (requestCode) {
            case Utils.tagStartActivityLogin:
                if (resultCode == Activity.RESULT_OK) {
                    init();
                }
        }
    }

    @Override
    public void onItemSelected(String id) {

        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
//        Intent detailIntent = new Intent(this, ActivityDetailProject.class);

//        startActivity(detailIntent);

    }
}
