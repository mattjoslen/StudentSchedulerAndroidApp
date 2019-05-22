package com.example.mattjoslenproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {

    private static final String TAG = "AssessmentAdapter";

    private Context mContext;
    private Cursor mCursor;


    public AssessmentAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_assessment_list, parent, false);
        AssessmentAdapter.ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String assessmentName = mCursor.getString(mCursor.getColumnIndex(Assessments.AssessmentEntry.COLUMN_NAME));
        String dueDate = mCursor.getString(mCursor.getColumnIndex(Assessments.AssessmentEntry.COLUMN_DUE));
        String type = mCursor.getString(mCursor.getColumnIndex(Assessments.AssessmentEntry.COLUMN_TYPE));
        final int assessmentID = mCursor.getInt(mCursor.getColumnIndex(Assessments.AssessmentEntry.COLUMN_ID));

        holder.assessmentNameText.setText(assessmentName);
        holder.dueDateText.setText(dueDate);
        holder.typeText.setText(type);
        holder.itemView.setTag(assessmentID);
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

        public TextView assessmentNameText;
        public TextView dueDateText;
        public TextView typeText;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            assessmentNameText = itemView.findViewById(R.id.assessmentNameText);
            dueDateText = itemView.findViewById(R.id.dueDateText);
            typeText = itemView.findViewById(R.id.typeText);
            parentLayout = itemView.findViewById(R.id.parent_course_layout);
        }
    }

}
