package com.cornershopapp.core.presentation

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.view.View
import android.view.View.GONE
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Interpolator
import androidx.core.view.animation.PathInterpolatorCompat

// http://easings.net/
var easeInOutQuart: Interpolator = PathInterpolatorCompat.create(0.77f, 0f, 0.175f, 1f)
const val MEDIUM_ANIMATION_DURATION = 600L
const val SHORT_ANIMATION_DURATION = 400L

private fun computeDurationFromHeight(view: View): Long {
    // 1dp/ms * multiplier
    return ((view.measuredHeight / view.context.resources.displayMetrics.density) * 0.3).toLong()
}

fun View.expand(): ValueAnimator {
    val matchParentMeasureSpec = makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = measuredHeight
    // Older versions of android (pre API 21) cancel animations for views with a height of 0 so use 1 instead.
    layoutParams.height = 1
    visibility = VISIBLE
    val animator = ValueAnimator.ofInt(0, targetHeight)
    animator.duration = computeDurationFromHeight(this)
    animator.interpolator = easeInOutQuart
    animator.addUpdateListener { animation ->
        val value = animation.animatedValue as Int
        if (value == targetHeight) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            layoutParams.height = value
        }
        requestLayout()
    }
    animator.start()
    return animator
}

fun View.collapse(): ValueAnimator {
    val initialHeight = measuredHeight
    val animator = ValueAnimator.ofInt(initialHeight, 0)
    animator.duration = computeDurationFromHeight(this)
    animator.interpolator = easeInOutQuart
    animator.addUpdateListener { animation ->
        val value = animation.animatedValue as Int
        if (value == 0) {
            visibility = GONE
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            layoutParams.height = value
            requestLayout()
        }
    }
    animator.start()
    return animator
}

fun View.stopBreathing() {
    clearAnimation()
    alpha = 1F
}

fun View.disableAnimated(minAlpha: Float = 0.45F): ValueAnimator {
    clearAnimation()
    val animator = ValueAnimator.ofFloat(alpha, minAlpha)
    animator.duration = MEDIUM_ANIMATION_DURATION
    animator.interpolator = easeInOutQuart
    animator.addUpdateListener { animation ->
        alpha = animation.animatedValue as Float
    }
    animator.start()
    return animator
}

fun View.enableAnimated(): ValueAnimator {
    val animator = ValueAnimator.ofFloat(alpha, 1F)
    animator.duration = MEDIUM_ANIMATION_DURATION
    animator.interpolator = easeInOutQuart
    animator.addUpdateListener { animation ->
        alpha = animation.animatedValue as Float
    }
    animator.start()
    return animator
}

fun View.startBreathing(minAlpha: Float = 0.35F) {
    val animation = AlphaAnimation(1F, minAlpha)
    animation.duration = MEDIUM_ANIMATION_DURATION
    animation.interpolator = easeInOutQuart
    animation.repeatCount = INFINITE
    animation.repeatMode = REVERSE
    startAnimation(animation)
}

fun View.disableClipOnParents() {
    if (parent == null) {
        return
    }
    if (this is ViewGroup) {
        clipChildren = false
    }
    if (parent is View) {
        (parent as View).disableClipOnParents()
    }
}

fun View.show(doShow: Boolean = true) {
    visibility = if (doShow) {
        VISIBLE
    } else {
        GONE
    }
}

fun View.hide() {
    visibility = GONE
}

fun View.enable() {
    isEnabled = true
    alpha = 1.0f
}

fun View.disable() {
    isEnabled = false
    alpha = 0.3f
}

fun View.isVisible(): Boolean {
    return visibility == VISIBLE
}

fun View.doOnVisible(callback: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            callback.invoke()
        }
    })
}
