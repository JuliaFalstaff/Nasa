package com.example.nasaapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.nasaapp.R

import com.google.android.material.appbar.AppBarLayout

class CustomBehavior @JvmOverloads constructor(
        context: Context? = null, attrs: AttributeSet? = null,
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
            parent: CoordinatorLayout,
            child: View,
            dependency: View,
    ) = dependency is AppBarLayout

    @SuppressLint("ResourceAsColor")
    override fun onDependentViewChanged(
            parent: CoordinatorLayout,
            child: View,
            dependency: View,
    ): Boolean {
        val appBar = dependency as AppBarLayout
        val currentAppBarHeight = appBar.height + appBar.y
        val parentHeight = parent.height
        val textViewDescriptionHeight = (parentHeight - currentAppBarHeight).toInt()
        child.layoutParams?.height = textViewDescriptionHeight
//        child.layoutParams?.width = textViewDescriptionHeight
        if (appBar.isLifted) {
            child.setBackgroundColor(R.color.light_apricot)
        }
        child.requestLayout()
        return false
    }
}