package videodownloader.privatebrowser.free.hd.statussaver.tool;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClassForZoomImgView extends AppCompatImageView {
    public static final float FLING_DAMPING_FACTOR = 0.9f;
    private static final float MAX_SCALE = 4.0f;
    public static final int PINCH_MODE_FREE = 0;
    public static final int PINCH_MODE_SCALE = 2;
    public static final int PINCH_MODE_SCROLL = 1;
    public static final int SCALE_ANIMATOR_DURATION = 200;
    private int mDispatchOuterMatrixChangedLock;
    private FlingAnimator mFlingAnimator;
    private final GestureDetector mGestureDetector;
    private final PointF mLastMovePoint;
    private RectF mMask;
    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;
    private final Matrix mOuterMatrix;
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListeners;
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListenersCopy;
    private int mPinchMode;
    private ScaleAnimator mScaleAnimator;
    private float mScaleBase;
    private final PointF mScaleCenter;

    public class FlingAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private final float[] mVector;

        public FlingAnimator(float vectorX, float vectorY) {
            setFloatValues(0.0f, 1.0f);
            setDuration(1000000L);
            addUpdateListener(this);
            this.mVector = new float[]{vectorX, vectorY};
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            ClassForZoomImgView classForZoomImgView = ClassForZoomImgView.this;
            float[] fArr = this.mVector;
            boolean scrollBy = classForZoomImgView.scrollBy(fArr[0], fArr[1]);
            float[] fArr2 = this.mVector;
            fArr2[0] = fArr2[0] * 0.9f;
            fArr2[1] = fArr2[1] * 0.9f;
            if (!scrollBy || MathUtils.getDistance(0.0f, 0.0f, fArr2[0], fArr2[1]) < 1.0f) {
                animation.cancel();
            }
        }
    }

    public static class MathUtils {
        private static final MatrixPool mMatrixPool = new MatrixPool(16);
        private static final RectFPool mRectFPool = new RectFPool(16);

        public static float[] getCenterPoint(float x1, float y1, float x2, float y2) {
            return new float[]{(x1 + x2) / 2.0f, (y1 + y2) / 2.0f};
        }

        public static float getDistance(float x1, float y1, float x2, float y2) {
            float f = x1 - x2;
            float f2 = y1 - y2;
            return (float) Math.sqrt((f2 * f2) + (f * f));
        }

        public static float[] getMatrixScale(Matrix matrix) {
            if (matrix != null) {
                float[] fArr = new float[9];
                matrix.getValues(fArr);
                return new float[]{fArr[0], fArr[4]};
            }
            return new float[2];
        }

        public static float[] inverseMatrixPoint(float[] point, Matrix matrix) {
            if (point == null || matrix == null) {
                return new float[2];
            }
            float[] fArr = new float[2];
            Matrix matrixTake = matrixTake();
            matrix.invert(matrixTake);
            matrixTake.mapPoints(fArr, point);
            matrixGiven(matrixTake);
            return fArr;
        }

        public static void matrixGiven(Matrix matrix) {
            mMatrixPool.given(matrix);
        }

        public static Matrix matrixTake() {
            return mMatrixPool.take();
        }

        public static void rectFGiven(RectF rectF) {
            mRectFPool.given(rectF);
        }

        public static RectF rectFTake() {
            return mRectFPool.take();
        }

        public static Matrix matrixTake(Matrix matrix) {
            Matrix take = mMatrixPool.take();
            if (matrix != null) {
                take.set(matrix);
            }
            return take;
        }

        public static RectF rectFTake(float left, float top, float right, float bottom) {
            RectF take = mRectFPool.take();
            take.set(left, top, right, bottom);
            return take;
        }
    }

    public static class MatrixPool extends ObjectsPool<Matrix> {
        public MatrixPool(int size) {
            super(size);
        }

        @Override
        public Matrix newInstance() {
            return new Matrix();
        }

        @Override
        public Matrix resetInstance(Matrix obj) {
            obj.reset();
            return obj;
        }
    }

    public static abstract class ObjectsPool<T> {
        private final Queue<T> mQueue = new LinkedList<>();
        private final int mSize;

        public ObjectsPool(int size) {
            this.mSize = size;
        }

        public void given(T obj) {
            if (obj == null || this.mQueue.size() >= this.mSize) {
                return;
            }
            this.mQueue.offer(obj);
        }

        public abstract T newInstance();

        public abstract T resetInstance(T obj);

        public T take() {
            if (this.mQueue.size() == 0) {
                return newInstance();
            }
            return resetInstance(this.mQueue.poll());
        }
    }

    public interface OuterMatrixChangedListener {
        void onOuterMatrixChanged(ClassForZoomImgView classForZoomImgView);
    }

    public static class RectFPool extends ObjectsPool<RectF> {
        public RectFPool(int size) {
            super(size);
        }

        @Override
        public RectF newInstance() {
            return new RectF();
        }

        @Override
        public RectF resetInstance(RectF obj) {
            obj.setEmpty();
            return obj;
        }
    }

    public class ScaleAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private final float[] mEnd;
        private final float[] mResult;
        private final float[] mStart;

        public ScaleAnimator(final ClassForZoomImgView this$0, Matrix start, Matrix end) {
            this(start, end, 200L);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float floatValue = (Float) animation.getAnimatedValue();
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mResult;
                float[] fArr2 = this.mStart;
                fArr[i] = m(this.mEnd[i], fArr2[i], floatValue, fArr2[i]);
            }
            ClassForZoomImgView.this.mOuterMatrix.setValues(this.mResult);
            ClassForZoomImgView.this.dispatchOuterMatrixChanged();
            ClassForZoomImgView.this.invalidate();
        }

        public ScaleAnimator(Matrix start, Matrix end, long duration) {
            this.mStart = new float[9];
            this.mEnd = new float[9];
            this.mResult = new float[9];
            setFloatValues(0.0f, 1.0f);
            setDuration(duration);
            addUpdateListener(this);
            start.getValues(this.mStart);
            end.getValues(this.mEnd);
        }
    }

    public float m(float f, float f2, float f3, float f4) {
        return ((f - f2) * f3) + f4;
    }

    public ClassForZoomImgView(Context context) {
        super(context);
        this.mOuterMatrix = new Matrix();
        this.mPinchMode = 0;
        this.mLastMovePoint = new PointF();
        this.mScaleCenter = new PointF();
        this.mScaleBase = 0.0f;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (ClassForZoomImgView.this.mPinchMode == 1 && (ClassForZoomImgView.this.mScaleAnimator == null || !ClassForZoomImgView.this.mScaleAnimator.isRunning())) {
                    ClassForZoomImgView.this.doubleTap(e.getX(), e.getY());
                }
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (ClassForZoomImgView.this.mPinchMode == 0) {
                    if (ClassForZoomImgView.this.mScaleAnimator == null || !ClassForZoomImgView.this.mScaleAnimator.isRunning()) {
                        ClassForZoomImgView.this.fling(velocityX, velocityY);
                        return true;
                    }
                    return true;
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (ClassForZoomImgView.this.mOnLongClickListener != null) {
                    ClassForZoomImgView.this.mOnLongClickListener.onLongClick(ClassForZoomImgView.this);
                }
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (ClassForZoomImgView.this.mOnClickListener != null) {
                    ClassForZoomImgView.this.mOnClickListener.onClick(ClassForZoomImgView.this);
                    return true;
                }
                return true;
            }
        });
        initView();
    }

    private void cancelAllAnimator() {
        ScaleAnimator scaleAnimator = this.mScaleAnimator;
        if (scaleAnimator != null) {
            scaleAnimator.cancel();
            this.mScaleAnimator = null;
        }
        FlingAnimator flingAnimator = this.mFlingAnimator;
        if (flingAnimator != null) {
            flingAnimator.cancel();
            this.mFlingAnimator = null;
        }
    }

    public void dispatchOuterMatrixChanged() {
        List<OuterMatrixChangedListener> list;
        List<OuterMatrixChangedListener> list2 = this.mOuterMatrixChangedListeners;
        if (list2 == null) {
            return;
        }
        this.mDispatchOuterMatrixChangedLock++;
        for (OuterMatrixChangedListener outerMatrixChangedListener : list2) {
            outerMatrixChangedListener.onOuterMatrixChanged(this);
        }
        int i = this.mDispatchOuterMatrixChangedLock - 1;
        this.mDispatchOuterMatrixChangedLock = i;
        if (i != 0 || (list = this.mOuterMatrixChangedListenersCopy) == null) {
            return;
        }
        this.mOuterMatrixChangedListeners = list;
        this.mOuterMatrixChangedListenersCopy = null;
    }

    public void doubleTap(float x, float y) {
        if (isReady()) {
            Matrix matrixTake = MathUtils.matrixTake();
            getInnerMatrix(matrixTake);
            float f = MathUtils.getMatrixScale(matrixTake)[0];
            float f2 = MathUtils.getMatrixScale(this.mOuterMatrix)[0];
            float f3 = f * f2;
            float width = getWidth();
            float height = getHeight();
            float maxScale = getMaxScale();
            float calculateNextScale = calculateNextScale(f, f2);
            if (calculateNextScale <= maxScale) {
                maxScale = calculateNextScale;
            }
            if (maxScale >= f) {
                f = maxScale;
            }
            Matrix matrixTake2 = MathUtils.matrixTake(this.mOuterMatrix);
            float f4 = f / f3;
            matrixTake2.postScale(f4, f4, x, y);
            float f5 = width / 2.0f;
            float f6 = height / 2.0f;
            matrixTake2.postTranslate(f5 - x, f6 - y);
            Matrix matrixTake3 = MathUtils.matrixTake(matrixTake);
            matrixTake3.postConcat(matrixTake2);
            float f7 = 0.0f;
            RectF rectFTake = MathUtils.rectFTake(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            matrixTake3.mapRect(rectFTake);
            float f8 = rectFTake.right;
            float f9 = rectFTake.left;
            float f10 = f8 - f9 < width ? f5 - ((f8 + f9) / 2.0f) : f9 > 0.0f ? -f9 : f8 < width ? width - f8 : 0.0f;
            float f11 = rectFTake.bottom;
            float f12 = rectFTake.top;
            if (f11 - f12 < height) {
                f7 = f6 - ((f11 + f12) / 2.0f);
            } else if (f12 > 0.0f) {
                f7 = -f12;
            } else if (f11 < height) {
                f7 = height - f11;
            }
            matrixTake2.postTranslate(f10, f7);
            cancelAllAnimator();
            ScaleAnimator scaleAnimator = new ScaleAnimator(this, this.mOuterMatrix, matrixTake2);
            this.mScaleAnimator = scaleAnimator;
            scaleAnimator.start();
            MathUtils.rectFGiven(rectFTake);
            MathUtils.matrixGiven(matrixTake3);
            MathUtils.matrixGiven(matrixTake2);
            MathUtils.matrixGiven(matrixTake);
        }
    }

    public void fling(float vx, float vy) {
        if (isReady()) {
            cancelAllAnimator();
            FlingAnimator flingAnimator = new FlingAnimator(vx / 60.0f, vy / 60.0f);
            this.mFlingAnimator = flingAnimator;
            flingAnimator.start();
        }
    }

    private void initView() {
        super.setScaleType(ScaleType.MATRIX);
    }

    private boolean isReady() {
        return getDrawable() != null && getDrawable().getIntrinsicWidth() > 0 && getDrawable().getIntrinsicHeight() > 0 && getWidth() > 0 && getHeight() > 0;
    }

    private void saveScaleContext(float x1, float y1, float x2, float y2) {
        this.mScaleBase = MathUtils.getMatrixScale(this.mOuterMatrix)[0] / MathUtils.getDistance(x1, y1, x2, y2);
        float[] inverseMatrixPoint = MathUtils.inverseMatrixPoint(MathUtils.getCenterPoint(x1, y1, x2, y2), this.mOuterMatrix);
        this.mScaleCenter.set(inverseMatrixPoint[0], inverseMatrixPoint[1]);
    }

    private void scale(PointF scaleCenter, float scaleBase, float distance, PointF lineCenter) {
        if (isReady()) {
            float f = scaleBase * distance;
            Matrix matrixTake = MathUtils.matrixTake();
            matrixTake.postScale(f, f, scaleCenter.x, scaleCenter.y);
            matrixTake.postTranslate(lineCenter.x - scaleCenter.x, lineCenter.y - scaleCenter.y);
            this.mOuterMatrix.set(matrixTake);
            MathUtils.matrixGiven(matrixTake);
            dispatchOuterMatrixChanged();
            invalidate();
        }
    }

    private void scaleEnd() {
        float f = 0;
        float f2;
        float f3;
        float f4 = 0;
        if (!isReady()) {
            return;
        }
        Matrix matrixTake = MathUtils.matrixTake();
        getCurrentImageMatrix(matrixTake);
        boolean z = false;
        float f5 = MathUtils.getMatrixScale(matrixTake)[0];
        float f6 = MathUtils.getMatrixScale(this.mOuterMatrix)[0];
        float width = getWidth();
        float height = getHeight();
        float maxScale = getMaxScale();
        float f7 = f5 > maxScale ? maxScale / f5 : 1.0f;
        if (f6 * f7 < 1.0f) {
            f7 = 1.0f / f6;
        }
        boolean z2 = true;
        if (f7 != 1.0f) {
            z = true;
        }
        Matrix matrixTake2 = MathUtils.matrixTake(matrixTake);
        PointF pointF = this.mLastMovePoint;
        matrixTake2.postScale(f7, f7, pointF.x, pointF.y);
        RectF rectFTake = MathUtils.rectFTake(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        matrixTake2.mapRect(rectFTake);
        float f8 = rectFTake.right;
        float f9 = rectFTake.left;
        if (f8 - f9 >= width) {
            if (f9 > 0.0f) {
                f = -f9;
            } else if (f8 >= width) {
                f = 0.0f;
            }
            f2 = rectFTake.bottom;
            f3 = rectFTake.top;
            if (f2 - f3 < height) {
                if (f3 > 0.0f) {
                    f4 = -f3;
                } else if (f2 >= height) {
                    f4 = 0.0f;
                }
                if (f == 0.0f && f4 == 0.0f) {
                    z2 = z;
                }
                if (z2) {
                    Matrix matrixTake3 = MathUtils.matrixTake(this.mOuterMatrix);
                    PointF pointF2 = this.mLastMovePoint;
                    matrixTake3.postScale(f7, f7, pointF2.x, pointF2.y);
                    matrixTake3.postTranslate(f, f4);
                    cancelAllAnimator();
                    ScaleAnimator scaleAnimator = new ScaleAnimator(this, this.mOuterMatrix, matrixTake3);
                    this.mScaleAnimator = scaleAnimator;
                    scaleAnimator.start();
                    MathUtils.matrixGiven(matrixTake3);
                }
                MathUtils.rectFGiven(rectFTake);
                MathUtils.matrixGiven(matrixTake2);
                MathUtils.matrixGiven(matrixTake);
            }
            height /= 2.0f;
            f2 = (f2 + f3) / 2.0f;
            if (f == 0.0f) {
                z2 = z;
            }
            MathUtils.rectFGiven(rectFTake);
            MathUtils.matrixGiven(matrixTake2);
            MathUtils.matrixGiven(matrixTake);
        }
        width /= 2.0f;
        f8 = (f8 + f9) / 2.0f;
        f = width - f8;
        f2 = rectFTake.bottom;
        f3 = rectFTake.top;
        MathUtils.rectFGiven(rectFTake);
        MathUtils.matrixGiven(matrixTake2);
        MathUtils.matrixGiven(matrixTake);
    }

    public boolean scrollBy(float xDiff, float yDiff) {
        float f;
        float f2;
        if (!isReady()) {
            return false;
        }
        RectF rectFTake = MathUtils.rectFTake();
        getImageBound(rectFTake);
        float width = getWidth();
        float height = getHeight();
        float f3 = rectFTake.right;
        float f4 = rectFTake.left;
        if (f3 - f4 >= width) {
            if (f4 + xDiff <= 0.0f) {
                if (f3 + xDiff < width) {
                    if (f3 > width) {
                        xDiff = width - f3;
                    }
                }
                f = rectFTake.bottom;
                f2 = rectFTake.top;
                yDiff = 0.0f;
                MathUtils.rectFGiven(rectFTake);
                this.mOuterMatrix.postTranslate(xDiff, yDiff);
                dispatchOuterMatrixChanged();
                invalidate();
            } else if (f4 < 0.0f) {
                xDiff = -f4;
                f = rectFTake.bottom;
                f2 = rectFTake.top;
                if (f - f2 >= height) {
                    if (f2 + yDiff <= 0.0f) {
                        if (f + yDiff < height) {
                            if (f > height) {
                                yDiff = height - f;
                            }
                        }
                        MathUtils.rectFGiven(rectFTake);
                        this.mOuterMatrix.postTranslate(xDiff, yDiff);
                        dispatchOuterMatrixChanged();
                        invalidate();
                    } else if (f2 < 0.0f) {
                        yDiff = -f2;
                        MathUtils.rectFGiven(rectFTake);
                        this.mOuterMatrix.postTranslate(xDiff, yDiff);
                        dispatchOuterMatrixChanged();
                        invalidate();
                        return xDiff == 0.0f || yDiff != 0.0f;
                    }
                }
                yDiff = 0.0f;
                MathUtils.rectFGiven(rectFTake);
                this.mOuterMatrix.postTranslate(xDiff, yDiff);
                dispatchOuterMatrixChanged();
                invalidate();
            }
        }
        xDiff = 0.0f;
        f = rectFTake.bottom;
        f2 = rectFTake.top;
        yDiff = 0.0f;
        MathUtils.rectFGiven(rectFTake);
        this.mOuterMatrix.postTranslate(xDiff, yDiff);
        dispatchOuterMatrixChanged();
        invalidate();
        return false;
    }

    public float calculateNextScale(float innerScale, float outerScale) {
        return outerScale * innerScale < MAX_SCALE ? MAX_SCALE : innerScale;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        if (this.mPinchMode == 2) {
            return true;
        }
        RectF imageBound = getImageBound(null);
        if (imageBound == null || imageBound.isEmpty()) {
            return false;
        }
        return direction > 0 ? imageBound.right > ((float) getWidth()) : imageBound.left < 0.0f;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (this.mPinchMode == 2) {
            return true;
        }
        RectF imageBound = getImageBound(null);
        if (imageBound == null || imageBound.isEmpty()) {
            return false;
        }
        return direction > 0 ? imageBound.bottom > ((float) getHeight()) : imageBound.top < 0.0f;
    }

    public Matrix getCurrentImageMatrix(Matrix matrix) {
        Matrix innerMatrix = getInnerMatrix(matrix);
        innerMatrix.postConcat(this.mOuterMatrix);
        return innerMatrix;
    }

    public RectF getImageBound(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        } else {
            rectF.setEmpty();
        }
        if (isReady()) {
            Matrix matrixTake = MathUtils.matrixTake();
            getCurrentImageMatrix(matrixTake);
            rectF.set(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            matrixTake.mapRect(rectF);
            MathUtils.matrixGiven(matrixTake);
            return rectF;
        }
        return rectF;
    }

    public Matrix getInnerMatrix(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }
        if (isReady()) {
            RectF rectFTake = MathUtils.rectFTake(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            RectF rectFTake2 = MathUtils.rectFTake(0.0f, 0.0f, getWidth(), getHeight());
            matrix.setRectToRect(rectFTake, rectFTake2, Matrix.ScaleToFit.CENTER);
            MathUtils.rectFGiven(rectFTake2);
            MathUtils.rectFGiven(rectFTake);
        }
        return matrix;
    }

    public float getMaxScale() {
        return MAX_SCALE;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isReady()) {
            Matrix matrixTake = MathUtils.matrixTake();
            setImageMatrix(getCurrentImageMatrix(matrixTake));
            MathUtils.matrixGiven(matrixTake);
        }
        if (this.mMask != null) {
            canvas.save();
            canvas.clipRect(this.mMask);
            super.onDraw(canvas);
            canvas.restore();
            return;
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ScaleAnimator scaleAnimator;
        super.onTouchEvent(event);
        int action = event.getAction() & 255;
        if (action == 1 || action == 3) {
            if (this.mPinchMode == 2) {
                scaleEnd();
            }
            this.mPinchMode = 0;
        } else if (action == 6) {
            if (this.mPinchMode == 2 && event.getPointerCount() > 2) {
                if ((event.getAction() >> 8) == 0) {
                    saveScaleContext(event.getX(1), event.getY(1), event.getX(2), event.getY(2));
                } else if ((event.getAction() >> 8) == 1) {
                    saveScaleContext(event.getX(0), event.getY(0), event.getX(2), event.getY(2));
                }
            }
        } else if (action == 0) {
            ScaleAnimator scaleAnimator2 = this.mScaleAnimator;
            if (scaleAnimator2 == null || !scaleAnimator2.isRunning()) {
                cancelAllAnimator();
                this.mPinchMode = 1;
                this.mLastMovePoint.set(event.getX(), event.getY());
            }
        } else if (action == 5) {
            cancelAllAnimator();
            this.mPinchMode = 2;
            saveScaleContext(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
        } else if (action == 2 && ((scaleAnimator = this.mScaleAnimator) == null || !scaleAnimator.isRunning())) {
            int i = this.mPinchMode;
            if (i == 1) {
                scrollBy(event.getX() - this.mLastMovePoint.x, event.getY() - this.mLastMovePoint.y);
                this.mLastMovePoint.set(event.getX(), event.getY());
            } else if (i == 2 && event.getPointerCount() > 1) {
                float distance = MathUtils.getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                float[] centerPoint = MathUtils.getCenterPoint(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                this.mLastMovePoint.set(centerPoint[0], centerPoint[1]);
                scale(this.mScaleCenter, this.mScaleBase, distance, this.mLastMovePoint);
            }
        }
        this.mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mOnClickListener = l;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        this.mOnLongClickListener = l;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
    }

    public ClassForZoomImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOuterMatrix = new Matrix();
        this.mPinchMode = 0;
        this.mLastMovePoint = new PointF();
        this.mScaleCenter = new PointF();
        this.mScaleBase = 0.0f;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (ClassForZoomImgView.this.mPinchMode == 1 && (ClassForZoomImgView.this.mScaleAnimator == null || !ClassForZoomImgView.this.mScaleAnimator.isRunning())) {
                    ClassForZoomImgView.this.doubleTap(e.getX(), e.getY());
                }
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (ClassForZoomImgView.this.mPinchMode == 0) {
                    if (ClassForZoomImgView.this.mScaleAnimator == null || !ClassForZoomImgView.this.mScaleAnimator.isRunning()) {
                        ClassForZoomImgView.this.fling(velocityX, velocityY);
                        return true;
                    }
                    return true;
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (ClassForZoomImgView.this.mOnLongClickListener != null) {
                    ClassForZoomImgView.this.mOnLongClickListener.onLongClick(ClassForZoomImgView.this);
                }
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (ClassForZoomImgView.this.mOnClickListener != null) {
                    ClassForZoomImgView.this.mOnClickListener.onClick(ClassForZoomImgView.this);
                    return true;
                }
                return true;
            }
        });
        initView();
    }

    public ClassForZoomImgView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mOuterMatrix = new Matrix();
        this.mPinchMode = 0;
        this.mLastMovePoint = new PointF();
        this.mScaleCenter = new PointF();
        this.mScaleBase = 0.0f;
        this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (ClassForZoomImgView.this.mPinchMode == 1 && (ClassForZoomImgView.this.mScaleAnimator == null || !ClassForZoomImgView.this.mScaleAnimator.isRunning())) {
                    ClassForZoomImgView.this.doubleTap(e.getX(), e.getY());
                }
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (ClassForZoomImgView.this.mPinchMode == 0) {
                    if (ClassForZoomImgView.this.mScaleAnimator == null || !ClassForZoomImgView.this.mScaleAnimator.isRunning()) {
                        ClassForZoomImgView.this.fling(velocityX, velocityY);
                        return true;
                    }
                    return true;
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (ClassForZoomImgView.this.mOnLongClickListener != null) {
                    ClassForZoomImgView.this.mOnLongClickListener.onLongClick(ClassForZoomImgView.this);
                }
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (ClassForZoomImgView.this.mOnClickListener != null) {
                    ClassForZoomImgView.this.mOnClickListener.onClick(ClassForZoomImgView.this);
                    return true;
                }
                return true;
            }
        });
        initView();
    }
}
