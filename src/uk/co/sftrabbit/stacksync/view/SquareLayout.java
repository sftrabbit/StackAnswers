package uk.co.sftrabbit.stacksync.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import uk.co.sftrabbit.stacksync.R;

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
