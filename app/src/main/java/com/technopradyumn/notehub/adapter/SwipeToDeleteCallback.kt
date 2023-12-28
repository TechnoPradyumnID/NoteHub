package com.technopradyumn.notehub.adapter

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDeleteCallback(context: Context) :ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFleg =  ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT or ItemTouchHelper.DOWN or
                ItemTouchHelper.UP or
                ItemTouchHelper.ANIMATION_TYPE_SWIPE_SUCCESS or
                ItemTouchHelper.ANIMATION_TYPE_SWIPE_CANCEL or
                ItemTouchHelper.END or
                ItemTouchHelper.START

        return makeMovementFlags(0,swipeFleg)
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

}