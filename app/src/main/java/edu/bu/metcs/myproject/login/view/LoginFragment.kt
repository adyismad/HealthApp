package edu.bu.metcs.myproject.login.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.sandeep.frainer.login.view.SliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.FrainerUtils
import edu.bu.metcs.myproject.MainActivity
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.data.SharePreferenceData
import edu.bu.metcs.myproject.login.model.SliderItem
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {

    private val userViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    private lateinit var adapter: SliderAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SliderAdapter(context)

        imageSlider?.apply {
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

        loginBtn.setOnClickListener {

            if (userEt.text?.isNotEmpty() == true && passEt.text?.isNotEmpty() == true) {
                userViewModel.getUser(userEt.text.toString(), passEt.text.toString())
            }
        }

        activity?.let {
            userViewModel.user.observe(it, {

                if (it != null) {
                    activity?.runOnUiThread {
                        SharePreferenceData.setSharedPrefString(context, FrainerUtils.LOGGED_USER, it.userName)
                        val action = LoginFragmentDirections.actionLoginFragmentToMyFrainerFragment(it.userName)
                        Navigation.findNavController(view).navigate(action)
                    }
                } else {
                    Toast.makeText(context, getString(R.string.please_correct_username_password), Toast.LENGTH_SHORT).show()
                }
            })
        }

        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
    }
}