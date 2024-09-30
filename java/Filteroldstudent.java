package com.example.attendink;

import android.widget.Filter;

import java.util.ArrayList;

public class Filteroldstudent extends Filter {

    private Oldstudentadapter oldadapter1;
    private ArrayList<Oldstudentmodel> oldfilterlist1;

    public Filteroldstudent(Oldstudentadapter oldstudentadapter, ArrayList<Oldstudentmodel> oldfilterlist1)
    {
        this.oldadapter1=oldadapter1;
        this.oldfilterlist1=(ArrayList<Oldstudentmodel>) oldfilterlist1;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results=new FilterResults();

        if(constraint!=null && constraint.length()>0)
        {
            constraint=constraint.toString().toUpperCase();
            ArrayList<Oldstudentmodel> filteredModel1=new ArrayList<>();
            for(int i=0;i<oldfilterlist1.size();i++)
            {
                if(oldfilterlist1.get(i).getSid().toUpperCase().contains(constraint) ||
                        oldfilterlist1.get(i).getName().toUpperCase().contains(constraint) ||
                        oldfilterlist1.get(i).getSection().toUpperCase().contains(constraint))
                {
                    filteredModel1.add(oldfilterlist1.get(i));

                }
            }

            results.count=filteredModel1.size();
            results.values=filteredModel1;
        }
        else
        {
            results.count=oldfilterlist1.size();
            results.values=oldfilterlist1;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        oldadapter1.oldstudentmodelList=(ArrayList<Oldstudentmodel>) results.values;

        oldadapter1.notifyDataSetChanged();

    }
}
