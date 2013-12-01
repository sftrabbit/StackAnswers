/*
 * Copyright (C) 2013 Joseph Mansfield
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uk.co.sftrabbit.stackanswers.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SlidableFrameLayout extends FrameLayout {

	public SlidableFrameLayout(Context context) {
		this(context, null);
	}

	public SlidableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public float getXFraction() {
		return getX() / getWidth();
	}

	public void setXFraction(float xFraction) {
		final int width = getWidth();
		setX(xFraction * width);
	}

	public float getYFraction() {
		return getY() / getHeight();
	}

	public void setYFraction(float yFraction) {
		final int height = getHeight();
		setY(yFraction * height);
	}
}
