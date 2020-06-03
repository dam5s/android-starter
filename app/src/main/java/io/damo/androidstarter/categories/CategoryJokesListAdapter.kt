package io.damo.androidstarter.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import io.damo.androidstarter.R
import io.damo.androidstarter.categories.CategoryJokesListAdapter.Cell.ErrorCell
import io.damo.androidstarter.categories.CategoryJokesListAdapter.Cell.LoadedCell
import io.damo.androidstarter.categories.CategoryJokesListAdapter.Cell.LoadingCell
import io.damo.androidstarter.categories.CategoryJokesListAdapter.Cell.NotLoadedCell
import io.damo.androidstarter.randomjoke.JokeView
import io.damo.androidstarter.support.Explanation
import io.damo.androidstarter.support.RemoteData
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded

class CategoryJokesListAdapter(context: Context) : BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(context)

    var remoteData: RemoteData<List<JokeView>> = NotLoaded()
        set(newData) {
            field = newData
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: layoutInflater.inflate(R.layout.cell_category_joke, parent, false)
        val viewHolder = ViewHolder(view)

        viewHolder.updateWithCell(getCell(position))

        return view
    }

    override fun getItem(position: Int): Any =
        getCell(position)

    private fun getCell(position: Int): Cell {
        return when (val d = remoteData) {
            is NotLoaded -> NotLoadedCell
            is Loading -> LoadingCell
            is Loaded -> LoadedCell(d.data[position])
            is Error -> ErrorCell(d.explanation)
        }
    }

    private val notLoadedCellId = -1L
    private val loadingCellId = -2L
    private val errorCellId = -3L

    override fun getItemId(position: Int): Long =
        when (remoteData) {
            is NotLoaded -> notLoadedCellId
            is Loading -> loadingCellId
            is Loaded -> position.toLong()
            is Error -> errorCellId
        }

    override fun getCount(): Int =
        when (val d = remoteData) {
            is NotLoaded -> 0
            is Loading -> 1
            is Loaded -> d.data.size
            is Error -> 1
        }

    class ViewHolder(view: View) {

        private val textView = view as TextView

        fun updateWithCell(cell: Cell) =
            when (cell) {
                NotLoadedCell -> Unit
                LoadingCell -> textView.text = "Loading..."
                is LoadedCell -> textView.text = cell.view.content
                is ErrorCell -> textView.text = cell.explanation.message
            }
    }

    sealed class Cell {
        object NotLoadedCell : Cell()
        object LoadingCell : Cell()
        data class LoadedCell(val view: JokeView) : Cell()
        data class ErrorCell(val explanation: Explanation) : Cell()
    }
}
