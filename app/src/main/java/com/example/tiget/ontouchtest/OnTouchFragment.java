package com.example.tiget.ontouchtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

public class OnTouchFragment extends Fragment {

    Context context;

    RelativeLayout container;
    ImageView imageView;
    Toolbar toolbar;
    int width;
    int height;




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.on_touch_fragment_layout, null);
        context = getContext();


        imageView = view.findViewById(R.id.imageView);
        container = view.findViewById(R.id.main_container);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                width = view.getWidth();
                height = view.getHeight();



                int horizontalMargin = 64;
                int verticalMargin = 200;

                //Фотке ставим размеры экрана(нашей вьюшки)
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width - horizontalMargin * 2, height - verticalMargin * 2);
                layoutParams.leftMargin = horizontalMargin;
                layoutParams.topMargin = verticalMargin;
                layoutParams.bottomMargin = verticalMargin;
                layoutParams.rightMargin = horizontalMargin;
                imageView.setLayoutParams(layoutParams);


            }
        });




        imageView.setOnTouchListener(new View.OnTouchListener() {

            RelativeLayout.LayoutParams parms;
            int startwidth;
            int startheight;
            float dx = 0, dy = 0, x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ImageView view = (ImageView) v;


                /**
                 * Если используем не фотку, а цвет, то удаляем это строку
                 * ((BitmapDrawable) view.getDrawable()).setAntiAlias(true);
                 */
                //((BitmapDrawable) view.getDrawable()).setAntiAlias(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        parms = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        startwidth = parms.width;
                        startheight = parms.height;
                        dx = event.getRawX() - parms.leftMargin;
                        dy = event.getRawY() - parms.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:

                        x = event.getRawX();
                        y = event.getRawY();

                        parms.leftMargin = (int) (x - dx);
                        parms.topMargin = (int) (y - dy);

                        parms.rightMargin = 0;
                        parms.bottomMargin = 0;
                        parms.rightMargin = parms.leftMargin + (5 * parms.width);
                        parms.bottomMargin = parms.topMargin + (10 * parms.height);

                        imageView.setRotation(0.02f * (x - dx));

                        view.setLayoutParams(parms);

                        break;
                    case MotionEvent.ACTION_UP:
                        imageView.setRotation(0);
                        imageView.setY(height / 2 - imageView.getHeight() / 2);
                        imageView.setX(width / 2 - imageView.getWidth() / 2);
                        break;

                }

                return true;

            }
        });
        return view;
    }
}
