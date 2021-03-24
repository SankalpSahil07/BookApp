package activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sankalp.bookapp.*
import fragment.AboutFragment
import fragment.DashboardFragment
import fragment.FavouriteFragment
import fragment.ProfileFragment

class NavigationDemoPage : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var framelayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_demopage_activity)

        drawerLayout = findViewById(R.id.drawer_Layout)
        coordinatorLayout = findViewById(R.id.coordinator_Layout)
        toolbar = findViewById(R.id.tool_bar)
        framelayout = findViewById(R.id.frame_Layout)
        navigationView = findViewById(R.id.navigation_view)
        setUpToolbar()

        openDashboard()

        /*supportFragmentManager.beginTransaction()
            .replace(R.id.frame_Layout, DashboardFragment())
            .commit()
        supportActionBar?.title = "Dashboard"*/

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@NavigationDemoPage,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null){
               previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.dashboard -> {
                    openDashboard()
                       /* supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_Layout, DashboardFragment())
                            .addToBackStack("Dashboard")
                            .commit()
                    supportActionBar?.title = "Dashboard"*/
                    drawerLayout.closeDrawers()

                    Toast.makeText(this@NavigationDemoPage,"Clicked on DashBoard", Toast.LENGTH_SHORT).show()
                }
                R.id.favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_Layout, FavouriteFragment())
                        .addToBackStack("Favourite")
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()

                    Toast.makeText(this@NavigationDemoPage,"Clicked on Favourite", Toast.LENGTH_SHORT).show()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_Layout, ProfileFragment())
                        .addToBackStack("Profile")
                        .commit()
                     supportActionBar?.title = "Profile"
                     drawerLayout.closeDrawers()

                    Toast.makeText(this@NavigationDemoPage,"Clicked on Profile", Toast.LENGTH_SHORT).show()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_Layout, AboutFragment())
                        .addToBackStack("About")
                        .commit()
                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()

                    Toast.makeText(this@NavigationDemoPage,"Clicked on About", Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
    private fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_Layout, fragment)
        transaction.commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
       val frag = supportFragmentManager.findFragmentById(R.id.frame_Layout)

        when(frag){
            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}