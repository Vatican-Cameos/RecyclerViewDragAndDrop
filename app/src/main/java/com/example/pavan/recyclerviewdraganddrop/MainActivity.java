package com.example.pavan.recyclerviewdraganddrop;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private List<AbstractDummyClass> list= new ArrayList<>();
    RecyclerView rv;
    Button button,button2;
    private RecyclerAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.rv).setOnDragListener(new MyDragListener());
        button = (Button) findViewById(R.id.bLel);
        button2 = (Button) findViewById(R.id.bLelel);
        button2.setOnTouchListener(new MyTouchListener());
        button.setOnTouchListener(new MyTouchListener());
        rv = (RecyclerView) findViewById(R.id.rv);
        list.add(new ImageData(R.mipmap.ic_launcher));
        list.add(new ImaigData(R.mipmap.ic_launcher));
        list.add(new ImageData(R.mipmap.ic_launcher));
        list.add(new ImaigData(R.mipmap.ic_launcher));


        mAdapter = new RecyclerAdapter(list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);




    }




    private class MyDragListener implements View.OnDragListener {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setAlpha(1f);
                    break;
                case DragEvent.ACTION_DROP:
                    ClipData clipData = event.getClipData();

                    if(clipData.getDescription().getLabel().toString().equals("source")){
                        String source = clipData.getItemAt(0).getText().toString();
                        if(source.equals("panel")){
                            String color = clipData.getItemAt(1).getHtmlText();
                            AbstractDummyClass id=null;
                            if(color.equals("blue")){
                                id = new ImageData(R.mipmap.ic_launcher);
                            }else if(color.equals("red")){
                                id=new ImaigData(R.mipmap.ic_launcher);
                            }
                            if(id!=null)
                                mAdapter.addtem(id);
                        }
                        else if(source.equals("recycler")){
                            Integer position = Integer.parseInt(clipData.getItemAt(1).getHtmlText());
                            AbstractDummyClass id = mAdapter.getItemAt(position);
                            if(id!=null){
                                mAdapter.removeItem(position);
                                mAdapter.addtem(id);
                            }
                        }
                    }
//                        Log.d("data",data);
//                        if(data.equals("source")) {
//
//                            ImageData id = new ImageData(R.mipmap.ic_launcher);
//                            mAdapter.addtem(id);
//                        }else if(data.equals("recycler")){
//                            //hashhshsh
//
//                        }else{
//                            ImaigData id2 = new ImaigData(R.mipmap.ic_launcher);
//                            mAdapter.addtem(id2);
//                        }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setAlpha(1f);
                    break;
                default:
                    break;
            }
            return true;
        }
    }



    public class MyTouchListener implements View.OnTouchListener {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                switch (view.getId()) {

                    case R.id.bLel:
                        ClipData data = ClipData.newPlainText("source", "panel");
                        data.addItem(new ClipData.Item("color","blue"));
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                view);
                        view.startDrag(data, shadowBuilder, view, 0);


                    break;
                    case R.id.bLelel:

                        ClipData data2 = ClipData.newPlainText("source", "panel");
                        data2.addItem(new ClipData.Item("color","red"));
                        View.DragShadowBuilder shadowBuilder2 = new View.DragShadowBuilder(
                                view);
                        view.startDrag(data2, shadowBuilder2, view, 0);

                        break;
                }

                    return true;
            } else {
                return false;
            }
        }
    }


}
