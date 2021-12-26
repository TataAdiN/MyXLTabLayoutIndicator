package com.tataadin.tablayout;

import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String[] tabTitle = new String[]{"Tab One", "Tab Two"};
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private View indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        indicator = findViewById(R.id.frameIndicator);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init(){
        //remove actionbar shadow
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        viewPager2.setAdapter(new TabFragmentAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                tab.setText(tabTitle[position])).attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)indicator.getLayoutParams();

                //make width following transition to make smooth animation
                float translationOffset =  (positionOffset+position) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                indicator.setLayoutParams(params);
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();

                //make width match to selection Tab
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) indicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                indicatorParams.setMarginStart(40);
                indicator.setLayoutParams(indicatorParams);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}