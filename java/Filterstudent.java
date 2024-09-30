package com.example.attendink;

import android.widget.Filter;

import java.util.ArrayList;

public class Filterstudent extends Filter {

    private Studentadapter adapter1;
    private ArrayList<Studentmodel> filterlist1;

    public Filterstudent(Studentadapter adapter1,ArrayList<Studentmodel> filterlist1)
    {
        this.adapter1=adapter1;
        this.filterlist1=(ArrayList<Studentmodel>) filterlist1;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results=new FilterResults();

        if(constraint!=null && constraint.length()>0)
        {
            constraint=constraint.toString().toUpperCase();
            ArrayList<Studentmodel> filteredModel1=new ArrayList<>();
            for(int i=0;i<filterlist1.size();i++)
            {
                if(filterlist1.get(i).getSid().toUpperCase().contains(constraint) ||
                        filterlist1.get(i).getName().toUpperCase().contains(constraint) ||
                        filterlist1.get(i).getSection().toUpperCase().contains(constraint))
                {
                    filteredModel1.add(filterlist1.get(i));

                }
            }

            results.count=filteredModel1.size();
            results.values=filteredModel1;
        }
        else
        {
            results.count=filterlist1.size();
            results.values=filterlist1;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter1.studentmodelList=(ArrayList<Studentmodel>) results.values;

        adapter1.notifyDataSetChanged();
    }
}

