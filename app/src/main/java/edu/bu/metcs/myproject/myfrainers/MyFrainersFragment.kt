package edu.bu.metcs.myproject.myfrainers

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.MainActivity
import edu.bu.metcs.myproject.MainActivity.Companion.NOTIFICATION_CHANNEL_ID
import edu.bu.metcs.myproject.MainActivity.Companion.default_notification_channel_id
import edu.bu.metcs.myproject.MyNotificationPublisher
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

        val userName = SharePreferenceData.getSharedPrefString(context, "logged_user", "")
        val bundle = arguments

        if (bundle != null) {
            val userName: String = bundle.getString("username", "")
            frainerViewModel.getUsers(userName)
        } else {
            userName?.let { frainerViewModel.getUsers(it) }
        }

        activity?.let {
            frainerViewModel.users.observe(it, Observer {
                adapter = MyFrainersAdapter(it)
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter

            })
        }
    }
}