package com.example.attendink;

import android.widget.Filter;

import java.util.ArrayList;

public class Filterproduct extends Filter {

    private teacheradapter adapter;
    private ArrayList<teachermodel> filterlist;

    public Filterproduct(teacheradapter adapter, ArrayList<teachermodel> filterlist)
    {
        this.adapter=adapter;
        this.filterlist= (ArrayList<teachermodel>) filterlist;

    }



    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint!=null&& constraint.length()>0)
        {
            constraint=constraint.toString().toUpperCase();
            ArrayList<teachermodel> filteredModel=new ArrayList<>();
            for(int i=0;i<filterlist.size();i++)
            {
                if(filterlist.get(i).getTid().toUpperCase().contains(constraint) ||
                        filterlist.get(i).getName().toUpperCase().contains(constraint) ||
                        filterlist.get(i).getSection().toUpperCase().contains(constraint))
                {
                    filteredModel.add(filterlist.get(i));

                }
            }

            results.count=filteredModel.size();
            results.values=filteredModel;
        }
        else
        {
            results.count=filterlist.size();
            results.values=filterlist;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.teachermodelList=(ArrayList<teachermodel>) results.values;

        adapter.notifyDataSetChanged();
    }
}
