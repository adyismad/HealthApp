package edu.bu.metcs.myproject.myfrainers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.SharePreferenceData


class MyFrainersFragment : Fragment() {

    private lateinit var adapter: MyFrainersAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: AppCompatImageView

    private val frainerViewModel: MyFrainerViewModel by viewModels {
        MyFrainerViewModelFactory((activity?.application as FrainerApplication).repository)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.frainers_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        logoutBtn = view.findViewById(R.id.logoutBtn)

        activity?.let {
            frainerViewModel.users.observe(it, Observer {
                adapter = MyFrainersAdapter(it)
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                recyclerView.adapter = adapter

            })
        }

        logoutBtn.setOnClickListener {
            SharePreferenceData.clearAllPreference(context)
            Navigation.findNavController(view).navigate(R.id.action_myFrainerFragment_to_loginFragment)
        }

        return view
    }
}