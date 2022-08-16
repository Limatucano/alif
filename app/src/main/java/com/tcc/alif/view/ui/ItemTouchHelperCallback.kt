package com.tcc.alif.view.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val listener: ItemTouchHelperListener,
    private val isLongPressDragEnabled : Boolean
) : ItemTouchHelper.Callback(){

    override fun isLongPressDragEnabled(): Boolean = isLongPressDragEnabled

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
             ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return listener.onItemMove(recyclerView, viewHolder.adapterPosition, target.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onItemDismiss(viewHolder,viewHolder.adapterPosition)
    }

    override fun isItemViewSwipeEnabled(): Boolean = true


}