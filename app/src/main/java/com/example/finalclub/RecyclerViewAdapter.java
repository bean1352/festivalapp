package com.example.finalclub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mEvent = new ArrayList<>();
    private ArrayList<String> mPrice = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();
//    private List<Event> exampleList;
//    private List<Event> exampleListFull;
    List<Event> recyclerList;
    List<Event> recyclerListAll;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> imageNames, ArrayList<String> images, ArrayList<String> Event, ArrayList<String> Price, ArrayList<String> Date, Context context, List<Event> recyclerList){
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        mEvent = Event;
        mPrice = Price;
        mDate = Date;
        this.recyclerList = recyclerList;
        this.recyclerListAll = new ArrayList<>(recyclerList);
//        this.exampleList = exampleList;
//        exampleListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
       ViewHolder holder = new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        //Event currentItem = recyclerList.get(position);
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);

        holder.imageName.setText(mImageNames.get(position));
        holder.event.setText(mEvent.get(position));
        holder.price.setText(mPrice.get(position));
        holder.date.setText(mDate.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                Toast.makeText(mContext,mImageNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Event> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(recyclerListAll);
            }
            else{
                for(Event movie: recyclerListAll){
                    if(movie.getEventName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recyclerList.clear();
            recyclerList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        TextView event;
        TextView price;
        TextView date;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            event = itemView.findViewById(R.id.event_name);
            price = itemView.findViewById(R.id.event_price);
            date = itemView.findViewById(R.id.date);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }


}
