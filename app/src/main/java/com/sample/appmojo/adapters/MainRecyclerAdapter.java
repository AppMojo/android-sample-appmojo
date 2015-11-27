package com.sample.appmojo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.appmojo.R;

/**
 * Created by nutron on 11/27/15 AD.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    //Interface
    public interface IViewItemClickListener {
        void onItemClick(View view, int position);
    }

    private String[] mDataset;
    private IViewItemClickListener mItemClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainRecyclerAdapter(String[] dataset) {
        mDataset = dataset;
    }

    public void setOnItemClickListener(IViewItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public MainRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(MainRecyclerAdapter.ViewHolder holder, int position) {
        holder.position = position;
        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public IViewItemClickListener listener;
        public int position;

        public TextView mTextView;
        public ViewHolder(View v, IViewItemClickListener listener) {
            super(v);
            this.listener = listener;
            mTextView = (TextView) v.findViewById(R.id.item_title_tv);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener != null)
                listener.onItemClick(v, position);
        }
    }
}
