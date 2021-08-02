package edu.bu.metcs.myproject.pendingrequests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.myfrainers.MyFrainersFragmentDirections
import edu.bu.metcs.myproject.user.User

class PendingRequestAdapter(private val users: List<User>) : RecyclerView.Adapter<PendingRequestAdapter.ViewHolder>() {

    val colors: IntArray = intArrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4)

    // holder class to hold reference
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(user: User, position: Int) {
            itemView.findViewById<AppCompatTextView>(R.id.nameTv).text = user.name
            itemView.findViewById<AppCompatTextView>(R.id.genderTv).text = if (user.male) "Male" else "Female"
            itemView.findViewById<AppCompatTextView>(R.id.ageTv).text = user.age

            Glide.with(itemView.context)
                    .load(if (position > colors.size - 1) R.drawable.image1 else colors[position])
                    .override(150, 250)
                    .into(itemView.findViewById(R.id.frainer_img));

            itemView.setOnClickListener {
                val action = PendingRequestFragmentDirections.actionPendingRequestFragmentToFrainerDetailFragment(user)
                Navigation.findNavController(itemView).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingRequestAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pending_request_item, parent, false))
    }

    override fun onBindViewHolder(holder: PendingRequestAdapter.ViewHolder, position: Int) {
        holder.bindItems(users.get(position), position)
    }

    override fun getItemCount(): Int = users.size
}