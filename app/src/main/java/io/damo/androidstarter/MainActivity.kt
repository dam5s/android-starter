package io.damo.androidstarter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import androidx.fragment.app.commit
import io.damo.androidstarter.categories.CategoriesTabFragment
import io.damo.androidstarter.categories.CategoryJokesFragment
import io.damo.androidstarter.categories.CategoryView
import io.damo.androidstarter.randomjoke.RandomJokeTabFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigation

class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    CategoriesTabFragment.Delegate {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        switchTab(Tab.Random)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            switchTabByItemId(item.itemId)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val backStackIsNotEmpty = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.setDisplayHomeAsUpEnabled(backStackIsNotEmpty)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun navigateToCategory(category: CategoryView) =
        addFragmentToBackStack(CategoryJokesFragment.create(category))

    private fun addFragmentToBackStack(fragment: Fragment) =
        supportFragmentManager.commit {
            setTransition(TRANSIT_FRAGMENT_OPEN)
            replace(R.id.fragment, fragment)
            addToBackStack(null)
        }

    private fun switchTabByItemId(itemId: Int): Boolean =
        when (itemId) {
            R.id.categoriesNavigationItem -> switchTab(Tab.Categories)
            R.id.randomNavigationItem -> switchTab(Tab.Random)
            else -> false
        }

    private fun switchTab(tab: Tab): Boolean {
        supportFragmentManager.commit {
            replace(R.id.fragment, tab.fragment)
            setTransition(TRANSIT_FRAGMENT_FADE)
        }
        return true
    }

    enum class Tab(val fragment: Fragment) {
        Random(RandomJokeTabFragment()),
        Categories(CategoriesTabFragment()),
    }
}
