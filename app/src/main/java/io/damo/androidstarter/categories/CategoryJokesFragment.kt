package io.damo.androidstarter.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import io.damo.androidstarter.R
import kotlinx.android.synthetic.main.fragment_category_jokes.categoryJokesText

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
        arguments?.getCategoryName()?.let { setupView(it) }
    }

    private fun setupView(categoryName: String) {
        categoryJokesText.text = categoryName
    }
}
