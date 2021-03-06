package com.example.note.seoulddok.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.note.seoulddok.Model.ExpandableItem;
import com.example.note.seoulddok.Model.RecvData;
import com.example.note.seoulddok.R;
import com.example.note.seoulddok.dialog.HistoryDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyun_home on 2018-05-12.
 */

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    private final int EXISTENCE = 3;
    private final int NONEXISTENCE = 4;
    private ArrayList<Item> data;
    private Context context;

    public HistoryRecyclerAdapter(ArrayList<Item> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.expandable_header, parent, false);
                HeaderViewHolder header = new HeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater inflater_child = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater_child.inflate(R.layout.expandable_child, parent, false);
                ChildViewHolder child = new ChildViewHolder(view);
                return child;
        }
        return null;
    }

    public void notifyDataChange(ArrayList<Item> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final HeaderViewHolder itemController = (HeaderViewHolder) holder;
                itemController.redderalItem = item;
                Log.e("########", item.date);
                itemController.header_title.setText(item.date);
                if (item.invisibleChildren == null) {
                    //itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    //itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.redderalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.second_left);
                            //itemController.btn_expand_toggle.animate().rotationBy(-450);


                        } else {
                            int pos = data.indexOf(itemController.redderalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.second_down);
                            //itemController.btn_expand_toggle.animate().rotationBy(450);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ChildViewHolder itemChild_Controller = (ChildViewHolder) holder;
                if(data.get(position).distinction.equals("emer")){
                    itemChild_Controller.childMsg.setTextColor(Color.RED);
                }else {
                    itemChild_Controller.childMsg.setTextColor(Color.BLACK);
                }
                itemChild_Controller.childTime.setText(data.get(position).time);
                itemChild_Controller.childMsg.setText(data.get(position).message);

               /* if (data.get(position).i==NONEXISTENCE){
                    itemChild_Controller.imageView.setImageResource(R.drawable.no_video);
                }else if(data.get(position).i==EXISTENCE){
                    itemChild_Controller.imageView.setImageResource(R.drawable.video_ok);
                }
                */
                itemChild_Controller.childMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.get(position).distinction.equals("emer")) {
                            Intent intent = new Intent(context.getApplicationContext(), HistoryDialog.class);
                            intent.putExtra("latlang",data.get(position).latlang);
                            view.getContext().startActivity(intent);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public Item redderalItem;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            header_title = (TextView) itemView.findViewById(R.id.header_Title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView childTime;
        public TextView childMsg;

        public ChildViewHolder(View itemView) {
            super(itemView);
            childTime = itemView.findViewById(R.id.chile_time);
            childMsg = itemView.findViewById(R.id.child_message);
        }
    }

    /*public static class Item {
        public int type;
        public String date;
        public String time;
        public String message;
        public List<Item> invisibleChildren;


        public Item() {

        }

        public Item(int type, String date) {
            this.type = type;
            this.date = date;
        }
        public Item(int type, String time,String message) {
            this.type = type;
            this.time = time;
            this.message = message;
        }
    }*/
    public static class Item {
        public int type;
        public String date;
        public String time;
        public String message;
        private String distinction;
        private String latlang;
        public List<Item> invisibleChildren;


        public Item() {

        }

        public Item(int type, String date) {
            this.type = type;
            this.date = date;
        }

        public Item(int type, String time, String message, String distinction, String latlang) {
            this.type = type;
            this.time = time;
            this.message = message;
            this.distinction = distinction;
            this.latlang = latlang;
        }
    }
}
