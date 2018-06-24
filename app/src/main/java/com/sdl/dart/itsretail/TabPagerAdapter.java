package com.sdl.dart.itsretail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;
    ArrayList<String> QID=new ArrayList<>();
    Bundle bundle;
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs, ArrayList<String> QID) {
        super(fm);
        this.tabCount = numberOfTabs;
        this.QID=QID;


    }
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
        for(int i=0;i<5;i++)
        {
            QID.add("new");
        }


    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                bundle=new Bundle();
                bundle.putString("QID",QID.get(0));
                MyQuotesActivity tab1 = new MyQuotesActivity();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                bundle=new Bundle();
                bundle.putString("QID",QID.get(1));
                MyQuotesActivity tab2 = new MyQuotesActivity();
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                bundle=new Bundle();
                bundle.putString("QID",QID.get(2));
                MyQuotesActivity tab3 = new MyQuotesActivity();
                tab3.setArguments(bundle);
                return tab3;
            case 3:
                bundle=new Bundle();
                bundle.putString("QID",QID.get(3));
                MyQuotesActivity tab4 = new MyQuotesActivity();
                tab4.setArguments(bundle);
                return tab4;
            case 4:
                bundle=new Bundle();
                bundle.putString("QID",QID.get(4));
                MyQuotesActivity tab5 = new MyQuotesActivity();
                tab5.setArguments(bundle);
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
