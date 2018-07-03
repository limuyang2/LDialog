package top.limuyang2.customldialog

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

/**
 * iOS Style Dialog
 * Date 2018/6/26
 * @author limuyang
 */
object DialogAnimation {

    interface AnimationListener {
        fun onFinish()
    }

    fun slideToUp(view: View) {
        val slide = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f)

        slide.duration = 300
        slide.fillAfter = true
        slide.isFillEnabled = true
        view.startAnimation(slide)

        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }

    fun slideToDown(view: View, listener: AnimationListener?) {
        val slide = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f)

        slide.duration = 300
        slide.fillAfter = true
        slide.isFillEnabled = true
        view.startAnimation(slide)

        slide.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                listener?.onFinish()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }
}
