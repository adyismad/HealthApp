package edu.bu.metcs.myproject.myfrainers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.FrainerUtils
import edu.bu.metcs.myproject.MainActivity
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.data.SharePreferenceData


class MyFrainersFragment : Fragment() {

    private lateinit var adapter: MyFrainersAdapter
    private lateinit var recyclerView: RecyclerView

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
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = SharePreferenceData.getSharedPrefString(context, FrainerUtils.LOGGED_USER, "")
        val bundle = arguments

        if (bundle != null) {
            val userName: String = bundle.getString("username", "")
            frainerViewModel.getUsers(userName)
        } else {
            userName?.let { frainerViewModel.getUsers(it) }
        }

        activity?.let {
            frainerViewModel.users.observe(it, {
                adapter = MyFrainersAdapter(it)
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter

            })
        }
    }
}