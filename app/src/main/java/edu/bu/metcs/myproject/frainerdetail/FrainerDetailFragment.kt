package edu.bu.metcs.myproject.frainerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.pendingrequests.PendingRequestViewModel
import edu.bu.metcs.myproject.pendingrequests.PendingRequestViewModelFactory
import edu.bu.metcs.myproject.user.User

class FrainerDetailFragment : Fragment() {

    private lateinit var nameTv: AppCompatTextView
    private lateinit var ageTv: AppCompatTextView
    private lateinit var genderTv: AppCompatTextView
    private lateinit var gymLocationTv: AppCompatTextView
    private lateinit var gymDayTimeTv: AppCompatTextView
    private lateinit var partnerPrefTv: AppCompatTextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        val toolbar = view.findViewById<ConstraintLayout>(R.id.toolbar)
        nameTv = view.findViewById(R.id.nameTv)
        ageTv = view.findViewById(R.id.ageTv)
        genderTv = view.findViewById(R.id.genderTv)
        gymLocationTv = view.findViewById(R.id.gymLocationTv)
        gymDayTimeTv = view.findViewById(R.id.gymDayTimeTv)
        partnerPrefTv = view.findViewById(R.id.partnerPrefTv)
        toolbar.visibility = View.GONE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        if (bundle != null) {

            val user: User = bundle.getSerializable("User") as User

            nameTv.text = user.name
            ageTv.text = user.age
            genderTv.text = if (user.male) "Male" else "Female"
            gymLocationTv.text = user.location
            gymDayTimeTv.text = user.daytime
            partnerPrefTv.text = user.partnerPref
        }
    }
}