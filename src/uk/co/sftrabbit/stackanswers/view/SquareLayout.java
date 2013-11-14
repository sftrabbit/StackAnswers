package uk.co.sftrabbit.stackanswers.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class SquareLayout extends FrameLayout {
  private final Context context;

  public SquareLayout(Context context) {
    this(context, null);
  }

  public SquareLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec,
      int heightMeasureSpec) {
    final int height = getDefaultSize(getSuggestedMinimumHeight(),
      heightMeasureSpec);
    setMeasuredDimension(height, height);

    final int count = getChildCount();
    for (int i = 0; i < count; i++) {
      final View child = getChildAt(i);
      child.measure(
        MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
          MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
          MeasureSpec.EXACTLY)
      );
    }
  }
}
