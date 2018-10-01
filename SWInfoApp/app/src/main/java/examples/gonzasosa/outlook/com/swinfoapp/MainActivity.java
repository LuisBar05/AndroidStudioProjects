package examples.gonzasosa.outlook.com.swinfoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import examples.gonzasosa.outlook.com.swinfoapp.Fragments.SWElementsFragment;
import examples.gonzasosa.outlook.com.swinfoapp.Interfaces.OnElementTouchedListener;

public class MainActivity extends AppCompatActivity implements OnElementTouchedListener{
    public static final String KEY="ElementPosition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        SWElementsFragment swElementsFragment=new SWElementsFragment();

        getSupportFragmentManager()
                .beginTransaction ()
                .replace (R.id.rootContainer, swElementsFragment)
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }

    @Override
    public void onElementTouched(int index) {
        Intent myIntent= new Intent(getBaseContext(), ElementDetailsActivity.class);
        myIntent.putExtra(KEY, index);
        startActivity(myIntent);
    }
}
