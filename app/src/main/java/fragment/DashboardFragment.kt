package fragment

import adapter.Dashboard_RecyclerAdapter
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sankalp.bookapp.R
import model.Book
import org.json.JSONException
import utill.ConnectionManager
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerDashboard : RecyclerView
    lateinit var layoutManager : RecyclerView.LayoutManager

  //  lateinit var btnCheckInternet: Button

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar : ProgressBar

    /*val bookList = arrayListOf(
        "P.S I love You",
        "The Great Gatsby",
        "Anna karenina",
        "Madame Bovary",
        "War and Peace",
        "Lolita",
        "Middlemarch",
        "The Adventure  of Huckleberry Finn",
        "Moby-Dick",
        "The Lord of the Rings")*/

    lateinit  var  recyclerAdapter : Dashboard_RecyclerAdapter
    val bookInfoList = arrayListOf<Book>()

    val ratingComparator = Comparator<Book> { book1, book2 ->

        if (book1.bookRating.compareTo(book2.bookRating,true) == 0){
            // sort according to name if rating is same
            book1.bookName.compareTo(book2.bookName,true)
        } else{
            book1.bookRating.compareTo(book2.bookRating,true)
        }
    }

   /* val ratingComparator = Comparator<Book> { book1, book2 ->
        book1.bookRating.compareTo(book2.bookRating,true)
    }*/

  /*  val bookInfoList = arrayListOf<Book>(
        Book("P.S I Love You","Cecelia Ahren","Rs. 299","4.5",R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald","Rs. 399","4.1",R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy","Rs. 199","4.3",R.drawable.anna_kare),
        Book("Madame Bovary","Gustave Flaubert","Rs. 500","4.0",R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249","4.8",R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov","Rs. 349","3.9",R.drawable.lolita),
        Book("Middlemarch","George Elliot","Rs. 599", "4.2",R.drawable.middlemarch),
        Book("The Adventure of Huckleberry Finn","Mark Twain","Rs. 699","4.5",R.drawable.adventures_finn),
        Book("Moby-Dick","Herman Melville","Rs. 499","4.5",R.drawable.moby_dick),
        Book("The Lord of the Rings","J.R.R. Tolkien","Rs. 749","5.0",R.drawable.lord_of_rings))*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.dashboard_fragment, container, false)

        setHasOptionsMenu(true)
        recyclerDashboard = view.findViewById(R.id.recycler_Dashboard)
        layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = Dashboard_RecyclerAdapter(activity as Context, bookInfoList)
       // btnCheckInternet = view.findViewById(R.id.button_CheckInternet)
        progressLayout = view.findViewById(R.id.progressBarLayout)
        progressBar = view.findViewById(R.id.progressBar)

        progressLayout.visibility = View.VISIBLE
        // display progressbar if internet connection is slow

       /* btnCheckInternet.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                // Internet is available
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok") { text, listener ->
                    // Do nothing
                }
                dialog.setNegativeButton("Cancel") { text, listener ->
                    // Do nothing
                }
                dialog.create()
                dialog.show()
            } else {
                // Internet is not available
                   val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is not Found")
                dialog.setPositiveButton("Ok"){text, listener ->
                    // Do nothing
                }
                dialog.setNegativeButton("Cancel"){text, listener ->
                    // Do nothing
                }
                dialog.create()
                dialog.show()
             }
            }*/

            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v1/book/fetch_books/"

            if (ConnectionManager().checkConnectivity(activity as Context)) {
                val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener{
                    //  Here we will handle the response
                    // println("Response is $it")
                try {
                    progressLayout.visibility = View.GONE //  This will hide the progressBar Layout
                    val success = it.getBoolean("success")
                    if (success) {
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            val bookObject = Book(
                                    bookJsonObject.getString("book_id"),
                                    bookJsonObject.getString("name"),
                                    bookJsonObject.getString("author"),
                                    bookJsonObject.getString("rating"),
                                    bookJsonObject.getString("price"),
                                    bookJsonObject.getString("image"))

                            bookInfoList.add(bookObject)
                            recyclerAdapter = Dashboard_RecyclerAdapter(activity as Context, bookInfoList)
                            recyclerDashboard.adapter = recyclerAdapter
                            recyclerDashboard.layoutManager = layoutManager

                            // divides the line of RecyclerView List
                           /* recyclerDashboard.addItemDecoration(DividerItemDecoration(recyclerDashboard.context,
                                (layoutManager as LinearLayoutManager).orientation))*/
                        }
                    } else {
                        Toast.makeText(activity as Context, "Some Error Occurred!!!", Toast.LENGTH_SHORT).show()
                    }

                } catch (e:JSONException){
                    Toast.makeText(activity as Context, "Some unexpected error occurred!!!", Toast.LENGTH_SHORT).show()
                }

                }, Response.ErrorListener {
                    // Here we will handle the errors
                   // println("Error is $it")
                    if (activity !=null) {
                        Toast.makeText(activity as Context, "Volley error occurred!!!!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "72d2ef1d8cea31"    // 72d2ef1d8cea31
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)
            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is not Found")
                dialog.setPositiveButton("Open Settings", ) { text, listner ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    activity?.finish()
                    // This Code will let the user Opens the Setting in Device
                }
                dialog.setNegativeButton("Exit") { text, listner ->
                    ActivityCompat.finishAffinity(activity as Activity)
                    // This Code , let the user exit the app
                }
                dialog.create()
                dialog.show()
            }
            return view
        }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        if (id == R.id.action_sort){
            Collections.sort(bookInfoList, ratingComparator)
            bookInfoList.reverse()
         }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
     }
  }