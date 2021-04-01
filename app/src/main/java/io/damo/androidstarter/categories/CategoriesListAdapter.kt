package io.damo.androidstarter.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import io.damo.androidstarter.R
import java.io.Serializable

class CategoriesListAdapter(appContext: Context) : BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(appContext)

    class ViewHolder(private val view: View) {
        fun setText(text: String) {
            (view as TextView).text = text
        }
    }

    fun getCategory(position: Int) =
        allCategories[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: createNewView(parent)
        val viewHolder = ViewHolder(view)
        val category = getCategory(position)

        viewHolder.setText(category.name)

        return view
    }

    override fun getItem(position: Int): Any =
        getCategory(position)

    override fun getItemId(position: Int): Long =
        position.toLong()

    override fun getCount(): Int =
        allCategories.size

    private fun createNewView(parent: ViewGroup?): View =
        layoutInflater.inflate(R.layout.cell_category, parent, false)
}

data class CategoryView(val name: String) : Serializable

private val allCategories = listOf(
    CategoryView("Explicit"),
    CategoryView("Nerdy")
)
