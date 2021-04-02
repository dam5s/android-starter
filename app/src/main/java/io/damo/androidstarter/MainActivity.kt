package io.damo.androidstarter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.damo.androidstarter.ui.MainUI

class MainActivity : ComponentActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUI(stateStore)
        }
    }
}

//        bottomNavigation.setOnNavigationItemSelectedListener { item ->
//            switchTabByItemId(item.itemId)
//        }

//        supportFragmentManager.addOnBackStackChangedListener {
//            val backStackIsNotEmpty = supportFragmentManager.backStackEntryCount > 0
//            supportActionBar?.setDisplayHomeAsUpEnabled(backStackIsNotEmpty)
//        }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean =
//        when (item.itemId) {
//            android.R.id.home -> {
//                supportFragmentManager.popBackStack()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }

//    override fun navigateToCategory(category: CategoryView) =
//        addFragmentToBackStack(CategoryJokesFragment.create(category))

//    private fun addFragmentToBackStack(fragment: Fragment) =
//        supportFragmentManager.commit {
//            setTransition(TRANSIT_FRAGMENT_OPEN)
//            replace(R.id.fragment, fragment)
//            addToBackStack(null)
//        }

//    private fun switchTabByItemId(itemId: Int): Boolean =
//        when (itemId) {
//            R.id.categoriesNavigationItem -> switchTab(Tab.Categories)
//            R.id.randomNavigationItem -> switchTab(Tab.Random)
//            else -> false
//        }

//    private fun switchTab(tab: Tab): Boolean {
//        supportFragmentManager.commit {
//            replace(R.id.fragment, tab.fragment)
//            setTransition(TRANSIT_FRAGMENT_FADE)
//        }
//        return true
//    }

//    enum class Tab(val fragment: Fragment) {
//        Random(RandomJokeTabFragment()),
//        Categories(CategoriesTabFragment()),
//    }