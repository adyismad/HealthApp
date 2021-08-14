package edu.bu.metcs.myproject.signup

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import de.hdodenhof.circleimageview.CircleImageView
import edu.bu.metcs.myproject.*
import edu.bu.metcs.myproject.data.SharePreferenceData
import edu.bu.metcs.myproject.data.User
import kotlinx.android.synthetic.main.signup_fragment.*

class SignupFragment : Fragment() {

    private lateinit var user: User

    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_image.setOnClickListener {
            selectImage()
        }

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment)
        }

        submitBtn.setOnClickListener {

            when {
                TextUtils.isEmpty(usernameEt.text) -> {
                    Toast.makeText(context, getString(R.string.user_cannot_be_empty), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(firstNameLastNameEt.text) -> {
                    Toast.makeText(context, getString(R.string.name_cannot_empty), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(passwordEt.text) -> {
                    Toast.makeText(context, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(confirmPasswordEt.text) -> {
                    Toast.makeText(context, getString(R.string.confirm_password_empty), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(ageEt.text) -> {
                    Toast.makeText(context, getString(R.string.age_cannot_empty), Toast.LENGTH_SHORT).show()
                }
                !maleBtn.isChecked && !femaleBtn.isChecked -> {
                    Toast.makeText(context, getString(R.string.select_gender), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(gymDayTimeEt.text) -> {
                    Toast.makeText(context, getString(R.string.gym_location_cannot_empty), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(gymDayTimeEt.text) -> {
                    Toast.makeText(context, getString(R.string.gym_day_time_cannot_empty), Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(partnerPrefEt.text) -> {
                    Toast.makeText(context, getString(R.string.partner_pref_cannot_empty), Toast.LENGTH_SHORT).show()
                }

                !passwordEt.text?.toString().equals(confirmPasswordEt.text?.toString()) -> {
                    Toast.makeText(context, getString(R.string.password_confirm_match), Toast.LENGTH_SHORT).show()
                }

                else -> {
                    user = User(usernameEt.text.toString(), firstNameLastNameEt.text.toString(), passwordEt.text.toString(), maleBtn.isChecked,
                            ageEt.text.toString(), gymLocationEt.text.toString(), gymDayTimeEt.text.toString(), partnerPrefEt.text.toString())
                    signupViewModel.getUser(usernameEt.text.toString())
                }
            }
        }

        activity?.let {
            signupViewModel.user.observe(it) {
                if (it != null) {
                    Toast.makeText(context, getString(R.string.username_already_exists), Toast.LENGTH_SHORT).show()

                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT).show()
                        signupViewModel.insert(user)
                        SharePreferenceData.setSharedPrefString(context, FrainerUtils.LOGGED_USER, user.userName)
                        val action = SignupFragmentDirections.actionSignupFragmentToMyFrainerFragment(user.userName)
                        Navigation.findNavController(view).navigate(action)
                    }
                }
            }
        }

        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>(getString(R.string.take_photo), getString(R.string.cancel))
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.choose_profile_picture))
        builder.setItems(options) { dialog, item ->
            if (options[item] == getString(R.string.take_photo)) {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (options[item] == getString(R.string.cancel)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.extras?.get("data") as Bitmap?
                    view?.findViewById<CircleImageView>(R.id.profile_image)
                            ?.setImageBitmap(selectedImage)
                }
            }
        }
    }
}