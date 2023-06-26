package com.example.note_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Element> mElementList;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(Element element);
    }

    public ElementListAdapter(Context context, OnItemClickListener onItemClickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mElementList = null;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.note_row, parent, false);
        ElementViewHolder elementViewHolder = new ElementViewHolder(rootView);
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        holder.bindToElementViewHolder(position);
    }

    @Override
    public int getItemCount() {
        if (mElementList != null)
            return mElementList.size();
        return 0;
    }

    public void setElementList(List<Element> elementList) {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    //proba chat
    public List<Element> getElementList() {
        return mElementList;
    }

    //chat proposed
    public void deleteElement(int position) {
        if (mElementList != null && position >= 0 && position < mElementList.size()) {
            mElementList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView note_order_TextView;
        TextView note_title_TextView;
        TextView note_date_TextView;
        TextView note_description_TextView;

        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);

            note_order_TextView = itemView.findViewById(R.id.note_order_TextView);

            note_title_TextView = itemView.findViewById(R.id.note_title_TextView);
            note_date_TextView = itemView.findViewById(R.id.note_date_TextView);
            note_description_TextView = itemView.findViewById(R.id.note_description_TextView);
            itemView.setOnClickListener(this);
        }

        public void bindToElementViewHolder(int position) {
            note_order_TextView.setText(position+1+".");
            note_title_TextView.setText(mElementList.get(position).getName());
            Date modificationDate = mElementList.get(position).getModification_date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = sdf.format(modificationDate);
            note_date_TextView.setText(formattedDate);
            note_description_TextView.setText(mElementList.get(position).getDescription());
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Element clickedElement = mElementList.get(position);
                mOnItemClickListener.onItemClickListener(clickedElement);
            }
        }
    }
}
