package edu.bu.metcs.myproject.frainerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.data.User
import kotlinx.android.synthetic.main.profile_fragment.*

class FrainerDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        if (bundle != null) {
            val user: User = bundle.getSerializable("User") as User
            nameTv.text = user.name
            ageTv.text = user.age
            genderTv.text = if (user.male) getString(R.string.male) else getString(R.string.female)
            gymLocationTv.text = user.location
            gymDayTimeTv.text = user.daytime
            partnerPrefTv.text = user.partnerPref
        }
    }
}