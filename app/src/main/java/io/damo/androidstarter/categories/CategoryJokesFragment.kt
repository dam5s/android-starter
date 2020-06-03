package io.damo.androidstarter.categories

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.get
import io.damo.androidstarter.R
import io.damo.androidstarter.activityViewModelProvider
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.fragment_category_jokes.categoryJokesList

class CategoryJokesFragment : Fragment() {

    companion object {
        private const val categoryNameKey = "categoryName"

        fun create(category: CategoryView) =
            CategoryJokesFragment().apply {
                arguments = bundleOf(categoryNameKey to category.name)
            }

        fun Bundle.getCategoryName() = getString(categoryNameKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_category_jokes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categoryName = arguments?.getCategoryName()
            ?: throw IllegalStateException("Fragment got instantiated without category name argument")

        setupView(requireActivity(), categoryName)
    }

    private fun setupView(activity: Activity, categoryName: String) {
        val adapter = CategoryJokesListAdapter(activity)
        val viewModel = activityViewModelProvider.get<CategoryJokesViewModel>()

        activity.title = getString(R.string.category_title, categoryName)
        categoryJokesList.adapter = adapter

        viewModel.jokes(categoryName).observe(this) { remoteData ->
            adapter.remoteData = remoteData
        }

        viewModel.loadCategory(categoryName)
    }
}
