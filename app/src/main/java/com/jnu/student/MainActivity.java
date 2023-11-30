package com.jnu.student;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
public class MainActivity extends AppCompatActivity {
    public class PageViewFragmentAdapter extends FragmentStateAdapter {
        public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position)
            {
                case 0:
                    return ShopItemFragment.newInstance();
                case 1:
                    return  TecentMapFragment.newInstance();
                case 2:
                    return BrowserFragment.newInstance();
                case 3:
                    return GameViewFragment.newInstance();
            }
            return ShopItemFragment.newInstance();
        }
        @Override
        public int getItemCount() {
            return 4;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ViewPager2 viewPager2Main= findViewById(R.id.viewpager2_main);
        viewPager2Main.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(),getLifecycle()));

        TabLayout tabLayout=findViewById(R.id.tablayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2Main, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position)
                {
                    case 0:
                        tab.setText(R.string.tab_caption_1_item);
                        break;
                    case 1:
                        tab.setText(R.string.tab_caption_2_map);
                        break;
                    case 2:
                        tab.setText(R.string.tab_caption_3_browser);
                        break;
                    case 3:
                        tab.setText(R.string.tab_caption_4_game);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}