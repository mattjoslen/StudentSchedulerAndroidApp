package com.example.mattjoslenproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TermsFragment extends Fragment {
    private SQLiteDatabase mDatabase;
    private TermsAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.term_list, container, false);

        FloatingActionButton addTermButton = view.findViewById(R.id.addTermButton);
        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTerm.class);
                startActivity(intent);
            }
        });

        DBHelper myHelper = new DBHelper(getActivity());
        mDatabase = myHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.termRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TermsAdapter(getActivity(), getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


        return view;
    }


    private void removeItem(int id) { // Remove term by swiping it left or right
        String query = "SELECT count(*) FROM course_tbl WHERE termID = " + id;
        Cursor mCursor = mDatabase.rawQuery(query, null);
        mCursor.moveToFirst();
        int count = mCursor.getInt(0);

        if(count > 0) {
            mAdapter.swapCursor(getAllItems());

            Toast.makeText(getActivity(), "Cannot delete terms with courses...", Toast.LENGTH_LONG).show();
        } else {
            mDatabase.delete(Terms.TermEntry.TABLE_NAME,
                    Terms.TermEntry.COLUMN_ID + "=" + id, null);
            mAdapter.swapCursor(getAllItems());
        }
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                Terms.TermEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Terms.TermEntry.COLUMN_START
        );
    }
}
