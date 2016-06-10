package inscope.inscope;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Display;

import tabs.SlidingTabLayout;

/**
 * Created by tirthrami on 3/18/16.
 */
public class Main extends AppCompatActivity {
    private Toolbar toolbar;
    private Toolbar timeline_bar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("test", "Before superOnCreate");
        super.onCreate(savedInstanceState);
        Log.d("test", "In Main.class");
        setContentView(R.layout.activity_main);
        Log.d("test", "set view to activity_main");
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setCollapsible(true);
        toolbar.setTitleTextColor(Color.WHITE);
        timeline_bar = (Toolbar) findViewById(R.id.timeline_bar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.setCurrentItem(1,true);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);
        mTabs.setViewPager(mPager);

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        int[] icons = {R.drawable.timeline_selected,R.drawable.scope_selected,R.drawable.nav_selected};
        String[] tabs = getResources().getStringArray(R.array.tabs);
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new Timeline();
                    break;
                case 1:
                    fragment = new Scope();
                    break;
                case 2:
                    fragment = new Map();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = ContextCompat.getDrawable(Main.this,icons[position]);
            if(position != 1) drawable.setBounds(0,0,resizeTabIcon(),resizeTabIcon());
            else drawable.setBounds(0,0,resizeTabIcon()+20,resizeTabIcon()+20);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public int resizeTabIcon(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (size.x * 512)/7000;  //5462
    }
}
