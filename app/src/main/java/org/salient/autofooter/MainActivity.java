package org.salient.autofooter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static int listCount = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newFragment();

        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCount++;
                newFragment();
            }
        });

        findViewById(R.id.fab_reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCount > 0) {
                    listCount--;
                    newFragment();
                }
            }
        });
    }

    private void newFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, ListFragment.newInstance(listCount));
        transaction.commit();
    }

}
