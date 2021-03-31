package io.damo.androidstarter.categories

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import io.damo.androidstarter.AppLifeCycle.categoryJokes
import io.damo.androidstarter.Category
import io.damo.androidstarter.R
import io.damo.androidstarter.appComponent
import io.damo.androidstarter.backend.RemoteData
import io.damo.androidstarter.categories.CategoryJokesInteractions.loadCategory
import io.damo.androidstarter.databinding.FragmentCategoryJokesBinding
import io.damo.androidstarter.joke.JokeView
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.stateStore

class CategoryJokesFragment : Fragment(), Redux.Subscriber<RemoteData<List<JokeView>>> {

    companion object {
        private const val categoryNameKey = "categoryName"

        fun create(category: CategoryView) =
            CategoryJokesFragment().apply {
                arguments = bundleOf(categoryNameKey to category.name)
            }

        fun Bundle.getCategoryName() = getString(categoryNameKey)
    }

    private var binding: FragmentCategoryJokesBinding? = null

    private val list get() = binding?.categoryJokesList
    private val adapter get() = list?.adapter as? CategoryJokesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryJokesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val category = arguments?.getCategoryName()
            ?: throw IllegalStateException("Fragment got instantiated without category name argument")

        activity?.let { setupView(it, category) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stateStore.unsubscribe(this)
        binding = null
    }

    override fun onStateChanged(state: RemoteData<List<JokeView>>) {
        adapter?.remoteData = state
    }

    private fun setupView(activity: Activity, category: Category) {
        activity.title = getString(R.string.category_title, category)
        list?.adapter = CategoryJokesListAdapter(activity)

        stateStore.subscribe(this) {
            it.categoryJokes(category)
        }

        loadCategory(stateStore, appComponent.jokeApi, category)
    }
}
