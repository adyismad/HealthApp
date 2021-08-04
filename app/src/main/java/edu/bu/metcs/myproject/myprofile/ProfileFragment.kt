package edu.bu.metcs.myproject.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.MainActivity
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.data.SharePreferenceData

class ProfileFragment : Fragment() {

    private val profileViewModel: MyProfileViewModel by viewModels {
        MyProfileViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        val nameTv = view.findViewById<AppCompatTextView>(R.id.nameTv)
        val ageTv = view.findViewById<AppCompatTextView>(R.id.ageTv)
        val genderTv = view.findViewById<AppCompatTextView>(R.id.genderTv)
        val gymLocationTv = view.findViewById<AppCompatTextView>(R.id.gymLocationTv)
        val gymDayTimeTv = view.findViewById<AppCompatTextView>(R.id.gymDayTimeTv)
        val partnerPrefTv = view.findViewById<AppCompatTextView>(R.id.partnerPrefTv)
        val logoutBtn = view.findViewById<AppCompatImageView>(R.id.logoutBtn)
        val editProfileBtn = view.findViewById<AppCompatImageView>(R.id.editBtn)

        val userName = SharePreferenceData.getSharedPrefString(context, "logged_user", "")
        userName?.let {
            profileViewModel.getUser(it)
        }

        activity?.let {
            profileViewModel.user.observe(it, {
                it.name.let {
                    nameTv.text = it
                }
                ageTv.text = it.age
                genderTv.text = if (it.male) "Male" else "Female"
                gymLocationTv.text = it.location
                gymDayTimeTv.text = it.daytime
                partnerPrefTv.text = it.partnerPref

                editProfileBtn.setOnClickListener { view ->
                    val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(it)
                    Navigation.findNavController(view).navigate(action)
                }

            })
        }

        logoutBtn.setOnClickListener {
            SharePreferenceData.clearAllPreference(context)
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

}