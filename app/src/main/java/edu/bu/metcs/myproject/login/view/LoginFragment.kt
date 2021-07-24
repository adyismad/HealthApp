package edu.bu.metcs.myproject.login.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.sandeep.frainer.login.view.SliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.SharePreferenceData
import edu.bu.metcs.myproject.login.model.SliderItem
import edu.bu.metcs.myproject.user.LoggedInUser


class LoginFragment : Fragment() {

    private val userViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    private lateinit var userNameEt: TextInputEditText
    private lateinit var passwordEt: TextInputEditText
    private lateinit var registerBtn: AppCompatTextView

    private var sliderView: SliderView? = null
    private lateinit var adapter: SliderAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        sliderView = view.findViewById(R.id.imageSlider)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SliderAdapter(context)

        userNameEt = view.findViewById(R.id.userEt)
        passwordEt = view.findViewById(R.id.passEt)
        registerBtn = view.findViewById(R.id.registerBtn)

        sliderView?.apply {
            setSliderAdapter(adapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
            indicatorSelectedColor = Color.WHITE
            indicatorUnselectedColor = Color.GRAY
            scrollTimeInSec = 3
            isAutoCycle = true
            startAutoCycle()
        }

        val images =
                listOf(
                        "https://nypost.com/wp-content/uploads/sites/2/2018/01/012018_gym_buddies.jpg?quality=90&strip=all&w=618&h=410&crop=1",
                        "https://stuffoholics.com/wp-content/uploads/2019/05/Gym-Buddy-2.jpg",
                        "https://stuffoholics.com/wp-content/uploads/2019/05/Gym-Buddy-3.jpg",
                        "https://www.thebeardmag.com/wp-content/uploads/fitness-1291997_1920.jpg"
                )

        images.forEach {
            val item = SliderItem()
            item.imageUrl = it
            adapter.addItem(item)
        }

        registerBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment)
        }

        view.findViewById<AppCompatButton>(R.id.loginBtn).setOnClickListener {

            if (userNameEt.text?.isNotEmpty() == true && passwordEt.text?.isNotEmpty() == true) {
                userViewModel.getUser(userNameEt.text.toString(), passwordEt.text.toString())
            }
        }

        activity?.let {
            userViewModel.user.observe(it, {

                if (it != null) {
                    SharePreferenceData.saveObject(context, "logged_user", LoggedInUser(it.userName, it.password))
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_myFrainerFragment)
                } else {
                    Toast.makeText(context, "Please correct your username and password", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}