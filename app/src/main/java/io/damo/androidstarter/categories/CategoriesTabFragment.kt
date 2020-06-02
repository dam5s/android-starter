package io.damo.androidstarter.categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import io.damo.androidstarter.R
import kotlinx.android.synthetic.main.fragment_categories_list.categoriesList

class CategoriesTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_categories_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context?.let { setupView(it) }
    }

    private fun setupView(context: Context) {
        val adapter = CategoriesListAdapter(context)

        categoriesList.adapter = adapter
        categoriesList.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = adapter.getCategory(position)
            val categoryFragment = CategoryJokesFragment.create(selectedCategory)

            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                replace(R.id.fragment, categoryFragment)
                addToBackStack(null)
            }
        }
    }
}
