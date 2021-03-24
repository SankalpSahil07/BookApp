package fragment

import adapter.Favourite_RecyclerAdapter
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.sankalp.bookapp.R
import database.BookDatabase
import database.BookEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var  recyclerFavourite : RecyclerView
    lateinit var  progressLayout: RelativeLayout
    lateinit var  progressBar : ProgressBar
    lateinit var  layoutManager : RecyclerView.LayoutManager
    lateinit var  recyclerAdapter : Favourite_RecyclerAdapter
    var dbBookList = listOf<BookEntity>()

    val previousMenuItem: MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.favourite_fragment, container, false)

        recyclerFavourite = view.findViewById(R.id.recycler_Favourite)
        progressLayout = view.findViewById(R.id.progressLayoutFav)
        progressBar = view.findViewById(R.id.progressBarFav)

        layoutManager = GridLayoutManager(activity as Context,2)
        dbBookList = RetrieveFavourites(activity as Context).execute().get()

        if (activity != null){
            progressLayout.visibility = View.GONE
            recyclerAdapter = Favourite_RecyclerAdapter(activity as Context, dbBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutManager
        }
        return view
    }
    class RetrieveFavourites(val context:Context):AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context, BookDatabase::class.java,"books-db").build()
            return  db.bookDao().getAllBooks()
        }
    }
}