package io.damo.androidstarter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.commit
import io.damo.androidstarter.categories.CategoriesTabFragment
import io.damo.androidstarter.randomjoke.RandomJokeTabFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigation

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
            when (item.itemId) {
                R.id.categoriesNavigationItem -> switchTab(Tab.Categories)
                R.id.randomNavigationItem -> switchTab(Tab.Random)
                else -> false
            }
        }
    }

    private fun switchTab(tab: Tab): Boolean {
        supportFragmentManager.commit {
            replace(R.id.fragment, tab.fragment)
            setTransition(TRANSIT_FRAGMENT_FADE)
            title = getString(tab.title)
        }
        return true
    }

    enum class Tab(@StringRes val title: Int, val fragment: Fragment) {
        Random(R.string.random_title, RandomJokeTabFragment()),
        Categories(R.string.categories_title, CategoriesTabFragment()),
    }
}
