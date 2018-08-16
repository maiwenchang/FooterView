package org.salient.autofooter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mai on 2018/8/16
 * *
 * Description:
 * *
 */
public class ListFragment extends Fragment {

    private int listCount = 12;

    public ListFragment() {
    }

    public static ListFragment newInstance(int listCount) {
        ListFragment f = new ListFragment();
        f.listCount = listCount;
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.content_main, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new Adapter());
        return view;
    }


    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(String.valueOf(position + 1));
        }

        @Override
        public int getItemCount() {
            return listCount;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setPadding(30, 30, 30, 30);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

}
