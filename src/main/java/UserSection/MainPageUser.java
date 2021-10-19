package UserSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.reformingkeralathroughdigitilization.R;

import Admin.Fragments_Admin.FragmentProfile;
import Admin.viewpageAdaptorMain;

public class MainPageUser extends AppCompatActivity {

    Button tabLayout,btn;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_user);

        tabLayout=findViewById(R.id.tabLayout);
        btn=findViewById(R.id.btn);

        viewPager=findViewById(R.id.view_pager);
        String id=getIntent().getStringExtra("id");
        String name=getIntent().getStringExtra("name");
        String authority_Place=getIntent().getStringExtra("authority_Place");

        final viewpageAdaptorMain viewpageAdaptorMain = new viewpageAdaptorMain(getSupportFragmentManager());
        viewpageAdaptorMain.AddFragment(new FragmentProfile(name, id), "Profile");
        viewPager.setAdapter(viewpageAdaptorMain);
        Toast.makeText(this, authority_Place, Toast.LENGTH_SHORT).show();
        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPageUser.this,UserMainPage.class);
                intent.putExtra("authority","Villege");
                intent.putExtra("authority_Place",authority_Place);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPageUser.this,UserMainPage.class);
                intent.putExtra("authority","Panchayath");
                intent.putExtra("authority_Place",authority_Place);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });



//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setTranslationY(300);
//
//        tabLayout.setAlpha(0);
//
//        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}