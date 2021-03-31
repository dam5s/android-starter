package io.damo.androidstarter.categories

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import io.damo.androidstarter.R
import io.damo.androidstarter.databinding.FragmentCategoriesListBinding

class CategoriesTabFragment : Fragment() {

    interface Delegate {
        fun navigateToCategory(category: CategoryView)
    }

    private var binding: FragmentCategoriesListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let { setupView(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupView(activity: Activity) {
        activity.title = getString(R.string.categories_title)
        binding?.categoriesList?.setupCategoriesList(activity)
    }

    private fun ListView.setupCategoriesList(activity: Activity) {
        val categoriesListAdapter = CategoriesListAdapter(activity)
        adapter = categoriesListAdapter

        setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = categoriesListAdapter.getCategory(position)
            (activity as? Delegate)?.navigateToCategory(selectedCategory)
        }
    }
}
