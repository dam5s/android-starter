package io.damo.androidstarter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import io.damo.androidstarter.categories.CategoriesFragment
import io.damo.androidstarter.randomjoke.RandomJokeFragment
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

        displayFragment(RandomJokeFragment())

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.categoriesNavigationItem -> {
                    displayFragment(CategoriesFragment())
                    true
                }
                R.id.randomNavigationItem -> {
                    displayFragment(RandomJokeFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment, fragment)
        }
    }
}
