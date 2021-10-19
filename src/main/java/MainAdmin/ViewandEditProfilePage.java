package MainAdmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.reformingkeralathroughdigitilization.R;
import com.google.android.material.tabs.TabLayout;

public class ViewandEditProfilePage extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewand_edit_profile_page);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.view_pager);
        String id=getIntent().getStringExtra("id");
        String name=getIntent().getStringExtra("name");


        final viewProfilefragment AdaptorMain = new viewProfilefragment(getSupportFragmentManager());
        AdaptorMain.AddFragment(new VIEW_FRAGMENT_CLASS(id,name), "View");
        AdaptorMain.AddFragment(new EDIT_VIEW_FRAGMENT_CLASS(id,name), "Edit");

        viewPager.setAdapter(AdaptorMain);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTranslationY(300);

        tabLayout.setAlpha(0);

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}