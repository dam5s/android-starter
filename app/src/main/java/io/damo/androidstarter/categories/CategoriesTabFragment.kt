package io.damo.androidstarter.categories

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.damo.androidstarter.R
import kotlinx.android.synthetic.main.fragment_categories_list.categoriesList

class CategoriesTabFragment : Fragment() {

    interface Delegate {
        fun navigateToCategory(category: CategoryView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_categories_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let { setupView(it) }
    }

    private fun setupView(activity: Activity) {
        activity.title = getString(R.string.categories_title)

        val adapter = CategoriesListAdapter(activity)

        categoriesList.adapter = adapter
        categoriesList.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = adapter.getCategory(position)
            (activity as? Delegate)?.navigateToCategory(selectedCategory)
        }
    }
}
