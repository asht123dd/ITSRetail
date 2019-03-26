package com.sdl.dart.itsretail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;


public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;
    ArrayList<String> QID=new ArrayList<>();
    Bundle bundle;
    Boolean rice;
    String commodity;
    MyQuotesActivity tab1,tab2,tab3,tab4,tab5;
    MyRiceQuotesActivity tab1Rice,tab2Rice,tab3Rice,tab4Rice,tab5Rice;
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs, ArrayList<String> QID,String commodity) {
        super(fm);
        this.tabCount = numberOfTabs;
        this.QID=QID;
        this.commodity=commodity;

    }
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs,String commodity) {
        super(fm);
        this.tabCount = numberOfTabs;
        this.commodity=commodity;

        for(int i=0;i<5;i++)
        {
            this.QID.add("new");
        }

        Log.d("xyzr22","fresh tab added");
    }
    @Override
    public Fragment getItem(int position) {
        if(commodity.substring(0,4).equals("rice"))

        {
                Log.d("xyzr22","if executes");
            switch (position) {
                case 0:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(0));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");
                    tab1Rice = new MyRiceQuotesActivity();
                    tab1Rice.setArguments(bundle);
                    return tab1Rice;
                case 1:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(1));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab2Rice = new MyRiceQuotesActivity();
                    tab2Rice.setArguments(bundle);
                    return tab2Rice;
                case 2:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(2));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab3Rice = new MyRiceQuotesActivity();
                    tab3Rice.setArguments(bundle);
                    return tab3Rice;
                case 3:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(3));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab4Rice = new MyRiceQuotesActivity();
                    tab4Rice.setArguments(bundle);
                    return tab4Rice;
                case 4:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(4));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab5Rice = new MyRiceQuotesActivity();
                    tab5Rice.setArguments(bundle);
                    return tab5Rice;
                default:
                    return null;
            }
        }
        else {
            Log.d("xyzr22","else executes");
            switch (position) {
                case 0:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(0));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");
                    tab1 = new MyQuotesActivity();
                    tab1.setArguments(bundle);
                    return tab1;
                case 1:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(1));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab2 = new MyQuotesActivity();

                    tab2.setArguments(bundle);
                    return tab2;
                case 2:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(2));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab3 = new MyQuotesActivity();
                    tab3.setArguments(bundle);
                    return tab3;
                case 3:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(3));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab4 = new MyQuotesActivity();
                    tab4.setArguments(bundle);
                    return tab4;
                case 4:
                    bundle = new Bundle();
                    bundle.putString("QID", QID.get(4));
                    bundle.putString("commodity", commodity);
                    bundle.putString("RID", "RID1");

                    tab5 = new MyQuotesActivity();
                    tab5.setArguments(bundle);
                    return tab5;
                default:
                    return null;
            }
        }
    }
    public void removeTabPage(int position) {

            notifyDataSetChanged();
        if(commodity.substring(0,4).equals("rice"))
        {
            MyRiceQuotesActivity item=null;
            switch(position){
                case 0: item=tab1Rice;
                    break;
                case 1:item=tab2Rice;
                    break;
                case 2:item=tab3Rice;
                    break;
                case 3:item=tab4Rice;
                    break;
                case 4:item=tab5Rice;
                    break;
            }
            item.remove();
        }
        else {
            MyQuotesActivity item = null;
            switch (position) {
                case 0:
                    item = tab1;
                    break;
                case 1:
                    item = tab2;
                    break;
                case 2:
                    item = tab3;
                    break;
                case 3:
                    item = tab4;
                    break;
                case 4:
                    item = tab5;
                    break;
            }
            Log.d("xyzr22","Removing item at "+position);
            if(item!=null)
            item.remove();
            else
            {
                Log.d("xyzr22","item null");
            }
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
