package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sankalp.bookapp.R
import com.squareup.picasso.Picasso
import database.BookEntity

class Favourite_RecyclerAdapter(val context: Context, val fav_bookList:List<BookEntity>):
        RecyclerView.Adapter<Favourite_RecyclerAdapter.FavouriteViewHolder>() {

    class FavouriteViewHolder(view : View):RecyclerView.ViewHolder(view){
        val txtFav_BookName : TextView = view.findViewById(R.id.txtFav_BookTitle)
        val txtFav_BookAuthor : TextView = view.findViewById(R.id.txtFav_BookAuthor)
        val txtFav_BookPrice : TextView = view.findViewById(R.id.txtFav_BookPrice)
        val txtFav_BookRating : TextView = view.findViewById(R.id.txtFav_BookRating)
        val image_BookFavourite: ImageView = view.findViewById(R.id.fav_BookImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite_single_row,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val book = fav_bookList[position]
        holder.txtFav_BookName.text = book.bookName
        holder.txtFav_BookAuthor.text = book.bookAuthor
        holder.txtFav_BookPrice.text = book.bookPrice
        holder.txtFav_BookRating.text = book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.image_BookFavourite)
     }
    override fun getItemCount(): Int {
        return fav_bookList.size
    }
}