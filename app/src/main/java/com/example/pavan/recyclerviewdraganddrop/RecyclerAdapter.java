package com.example.pavan.recyclerviewdraganddrop;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pavan on 15/07/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AbstractDummyClass> items;




    public RecyclerAdapter(List<AbstractDummyClass> list) {
        this.items = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vH = null;
        if(viewType == 0) {

            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

             vH = new MyViewHolder(v1);
            return vH;
        }else{
            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout2,parent,false);
             vH = new MyViewHolder2(v1);
            return vH;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder.getItemViewType() == 0) {
            ImageData id = (ImageData) items.get(position);
            MyViewHolder holder1 = (MyViewHolder)holder;
            holder1.iv.setImageResource(id.getId());
            holder1.itemView.setTag(id);
            holder1.itemView.setOnTouchListener(new MyTouchListener());
        }else{
            ImaigData id =(ImaigData) items.get(position);
            MyViewHolder2 holder2 = (MyViewHolder2)holder;
            holder2.iv2.setImageResource(id.getId());
            holder2.itemView.setTag(id);
            holder2.itemView.setOnTouchListener(new MyTouchListener());
        }

        //holder.itemView.setAlpha(1f);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{
        public ImageView iv2;

        public MyViewHolder2(View itemView) {
            super(itemView);
            iv2 = (ImageView) itemView.findViewById(R.id.iv2);
        }
    }





    public class MyTouchListener implements View.OnTouchListener {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Toast.makeText(view.getContext(), motionEvent.getAction()+"", Toast.LENGTH_SHORT).show();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                String color = (view.getTag() instanceof ImageData)? "blue":"red";
                ClipData data = ClipData.newPlainText("source", "recycler");
                data.addItem(new ClipData.Item("position",items.indexOf(view.getTag()) +""));
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);

                //view.setAlpha(0.8f);
               // int index = items.indexOf(view.getTag());
               // removeItem(index);
                return true;
            } else if(motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction()==MotionEvent.ACTION_CANCEL){
                //view.setAlpha(1f);
                return false;
            }else if(motionEvent.getAction() != MotionEvent.ACTION_MOVE){
                //view.setAlpha(1f);
                return false;
            }
            else{
                return false;
            }
        }
    }





    public void removeItem(int position){
        if(items!=null) {
            if (position < items.size()) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void addtem(AbstractDummyClass imageData){
        if(items==null)
            items = new ArrayList<>();
        if(imageData!=null) {
            items.add(imageData);
            notifyItemInserted(items.indexOf(imageData));
        }
    }

    public void move(int oldPos, int newPos){

    }
    public AbstractDummyClass getItemAt(int position){
        if(items!=null) {
            if (position < items.size()) {
                return items.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof ImageData) return 0;
        else return 1;
    }
}
