package edu.bu.metcs.myproject.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.FrainerUtils
import edu.bu.metcs.myproject.MainActivity
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.data.SharePreferenceData
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private val profileViewModel: MyProfileViewModel by viewModels {
        MyProfileViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = SharePreferenceData.getSharedPrefString(context, FrainerUtils.LOGGED_USER, "")
        userName?.let {
            profileViewModel.getUser(it)
        }

        activity?.let {
            profileViewModel.user.observe(it, {
                it.name.let {
                    nameTv.text = it
                }
                ageTv.text = it.age
                genderTv.text = if (it.male) getString(R.string.male) else getString(R.string.female)
                gymLocationTv.text = it.location
                gymDayTimeTv.text = it.daytime
                partnerPrefTv.text = it.partnerPref

                editBtn.setOnClickListener { view ->
                    val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(it)
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }

        logoutBtn.setOnClickListener {
            SharePreferenceData.clearAllPreference(context)
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

}