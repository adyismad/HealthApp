package edu.bu.metcs.myproject.pendingrequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.MainActivity
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.data.SharePreferenceData

class PendingRequestFragment : Fragment() {

    private lateinit var adapter: PendingRequestAdapter
    private var recyclerView: RecyclerView? = null

    private val pendingRequestViewModel: PendingRequestViewModel by viewModels {
        PendingRequestViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.pending_request_fragment, container, false)

        recyclerView = view.findViewById(R.id.pendingRequestRv)
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = SharePreferenceData.getSharedPrefString(context, "logged_user", "")
        val bundle = arguments
        if (bundle != null) {
            val userName: String = bundle.getString("username", "")
            pendingRequestViewModel.getUsers(userName)
        } else {
            userName?.let { pendingRequestViewModel.getUsers(it) }
        }

        activity?.let {
            pendingRequestViewModel.users.observe(it, {
                adapter = PendingRequestAdapter(it)
                recyclerView?.let {
                    it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    it.adapter = adapter
                }
            })
        }
    }
}