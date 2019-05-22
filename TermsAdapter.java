package com.example.mattjoslenproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {

    private static final String TAG = "TermsAdapter";

    private Context mContext;
    private Cursor mCursor;


    public TermsAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_term_list, parent, false);
        TermsAdapter.ViewHolder holder = new ViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String termName = mCursor.getString(mCursor.getColumnIndex(Terms.TermEntry.COLUMN_NAME));
        String endDate = mCursor.getString(mCursor.getColumnIndex(Terms.TermEntry.COLUMN_END));
        String startDate = mCursor.getString(mCursor.getColumnIndex(Terms.TermEntry.COLUMN_START));
        final int id = mCursor.getInt(mCursor.getColumnIndex(Terms.TermEntry.COLUMN_ID));

        holder.termNameText.setText(termName);
        holder.startDateText.setText(startDate);
        holder.endDateText.setText(endDate);
        holder.hyphenText.setText("-");
        holder.itemView.setTag(id);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment termCoursesFragment = new TermCoursesFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("termID", id);
                termCoursesFragment.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, termCoursesFragment).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor (Cursor newCursor) {
        if(mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView termNameText;
        public TextView startDateText;
        public TextView endDateText;
        public TextView hyphenText;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            termNameText = itemView.findViewById(R.id.termNameText);
            startDateText = itemView.findViewById(R.id.startDateText);
            endDateText = itemView.findViewById(R.id.endDateText);
            hyphenText = itemView.findViewById(R.id.hyphenText);
            parentLayout = itemView.findViewById(R.id.parent_term_layout);
        }
    }


}
