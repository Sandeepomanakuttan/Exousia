package MainAdmin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.reformingkeralathroughdigitilization.R;

import Admin.Fragments_Admin.FragmentMassegeMain;

public class MassegeMain extends AppCompatActivity {
Fragment fragment;
View view;
String id,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massege_main);
        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.h,new FragmentMassegeMain(id,"General")).addToBackStack(null).commit();


    }
}