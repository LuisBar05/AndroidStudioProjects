package examples.gonzasosa.outlook.com.swinfoapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import examples.gonzasosa.outlook.com.swinfoapp.Fragments.BaseApiFragment;
import examples.gonzasosa.outlook.com.swinfoapp.Fragments.FilmsApiFragment;
import examples.gonzasosa.outlook.com.swinfoapp.Fragments.PeopleApiFragment;
import examples.gonzasosa.outlook.com.swinfoapp.Fragments.SpeciesApiFragment;
import examples.gonzasosa.outlook.com.swinfoapp.Fragments.StarshipsApiFragment;
import examples.gonzasosa.outlook.com.swinfoapp.Fragments.VehiclesApiFragment;

public class ElementDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_details);

        if(getIntent()!=null) {
            int index =getIntent().getIntExtra(MainActivity.KEY, 0);
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

            switch(index){
                case 0:
                    fragmentTransaction.replace(R.id.elementContainer, new BaseApiFragment());
                    break;
                case 1:
                    fragmentTransaction.replace(R.id.elementContainer, new PeopleApiFragment());
                    break;
                case 2:
                    fragmentTransaction.replace(R.id.elementContainer, new StarshipsApiFragment());
                    break;
                case 3:
                    fragmentTransaction.replace(R.id.elementContainer, new FilmsApiFragment());
                    break;
                case 4:
                    fragmentTransaction.replace(R.id.elementContainer, new VehiclesApiFragment());
                    break;
                case 5:
                    fragmentTransaction.replace(R.id.elementContainer, new SpeciesApiFragment());
                    break;
            }
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}
