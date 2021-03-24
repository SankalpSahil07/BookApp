package adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import activity.DescriptionActivity
import com.sankalp.bookapp.R
import com.squareup.picasso.Picasso
import model.Book

class  Dashboard_RecyclerAdapter(val context: Context, val itemList: ArrayList<Book>):
                            RecyclerView.Adapter<Dashboard_RecyclerAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val textBooKName : TextView = view.findViewById(R.id.text_NameOfBook)
        val textBookAuthor: TextView = view.findViewById(R.id.text_NameOfAuthor)
        val textBookPrice: TextView = view.findViewById(R.id.text_PriceOfBook)
        val textRating: TextView = view.findViewById(R.id.text_RatingOfBook)
        val imageBookImage : ImageView = view.findViewById(R.id.bookImage)
        val llContent : LinearLayout = view.findViewById(R.id.linearLayout_Content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
      /*  val text = itemList[position]
        holder.textView.text = text*/
        val book = itemList[position]
        holder.textBooKName.text = book.bookName
        holder.textBookAuthor.text = book.bookAuthor
        holder.textBookPrice.text = book.bookPrice
        holder.textRating.text = book.bookRating

      //  holder.imageBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imageBookImage)

        holder.llContent.setOnClickListener{
            val intent = Intent(context, DescriptionActivity:: class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }

      /*  holder.llContent.setOnClickListener {
            Toast.makeText(context,"Clicked on ${holder.textBooKName.text}", Toast.LENGTH_SHORT).show()
        }*/

    }
    override fun getItemCount(): Int {
        return itemList.size
    }
}


