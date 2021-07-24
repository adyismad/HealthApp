package edu.bu.metcs.myproject.myfrainers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.user.User

class MyFrainersAdapter(private val users: List<User>) : RecyclerView.Adapter<MyFrainersAdapter.ViewHolder>() {

    val colors: IntArray = intArrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4)

    // holder class to hold reference
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(user: User, position: Int) {
            itemView.findViewById<AppCompatTextView>(R.id.nameTv).text = user.name
            itemView.findViewById<AppCompatTextView>(R.id.genderTv).text = if (user.male) "Male" else "Female"
            itemView.findViewById<AppCompatTextView>(R.id.gymDayTime).text = user.daytime
            itemView.findViewById<AppCompatTextView>(R.id.ageTv).text = user.age
            itemView.findViewById<AppCompatTextView>(R.id.inviteBtn).setOnClickListener {
            }

            Glide.with(itemView.context)
                    .load(if (position > colors.size - 1) R.drawable.image1 else colors[position])
                    .override(150, 250)
                    .into(itemView.findViewById(R.id.frainer_img));
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFrainersAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.frainer_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyFrainersAdapter.ViewHolder, position: Int) {
        holder.bindItems(users.get(position), position)
    }

    override fun getItemCount(): Int = users.size
}