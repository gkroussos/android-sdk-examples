package com.indooratlas.android.sdk.examples.wayfinding;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.indooratlas.android.sdk.examples.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Extends great ImageView library by Dave Morrissey. See more:
 * https://github.com/davemorrissey/subsampling-scale-image-view.
 */
public class BlueDotView extends SubsamplingScaleImageView {

    private float radius = 1.0f;
    private PointF dotCenter = null;
    private List<PointF> points;
    private Path path = new Path();

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setDotCenter(PointF dotCenter) {
        this.dotCenter = dotCenter;
    }

    public BlueDotView(Context context) {
        this(context, null);
    }

    public BlueDotView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    private void initialise() {
        setWillNotDraw(false);
        setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_CENTER);
    }

    public void addDrawPoints(List<PointF> pointList) {
        points = new ArrayList<>(pointList);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isReady()) {
            return;
        }

        if (dotCenter != null) {
            PointF vPoint = sourceToViewCoord(dotCenter);
            float scaledRadius = getScale() * radius;
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(R.color.ia_blue));
            canvas.drawCircle(vPoint.x, vPoint.y, scaledRadius, paint);
        }
        if (points != null && !points.isEmpty()) {
            // If we have multiple points we want to draw, draw them here

            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setColor(getResources().getColor(R.color.red));
            paint2.setStrokeWidth(25);
            paint2.setPathEffect(new DashPathEffect(new float[] {10,10}, 5));
            paint2.setAlpha(120);

            int iter = 0;
            path.reset();
            for (PointF point : points) {
                PointF vPoint = sourceToViewCoord(point);
                if (iter > 0) {
                    path.lineTo(vPoint.x, vPoint.y);
                } else {
                    path.moveTo(vPoint.x, vPoint.y);
                }
                iter += 1;
            }
            canvas.drawPath(path, paint2);
        }
    }
}
