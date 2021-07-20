package edu.bu.metcs.myproject.login.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sandeep.frainer.login.view.SliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.login.model.SliderItem


class LoginFragment : Fragment() {
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

        view.findViewById<AppCompatTextView>(R.id.registerBtn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}