package ru.skillbranch.skillarticles.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import ru.skillbranch.skillarticles.R

class Bottombar@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr)
{
    init {
        val view = View.inflate(context, R.layout.layout_bottombar, null)
        addView(view)
    }
}