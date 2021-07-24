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
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import edu.bu.metcs.myproject.AppConstant
import edu.bu.metcs.myproject.FrainerApplication
import edu.bu.metcs.myproject.R
import edu.bu.metcs.myproject.SharePreferenceData
import edu.bu.metcs.myproject.user.LoggedInUser
import edu.bu.metcs.myproject.user.User

class SignupFragment : Fragment() {

    private lateinit var userNameEt: TextInputEditText
    private lateinit var userNameLayout: TextInputLayout
    private lateinit var nameEt: TextInputEditText
    private lateinit var nameLayout: TextInputLayout
    private lateinit var passwordEt: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordEt: TextInputEditText
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var ageEt: TextInputEditText
    private lateinit var ageLayout: TextInputLayout
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var gymLocationEt: TextInputEditText
    private lateinit var gymLocationLayout: TextInputLayout
    private lateinit var gymDayTimeEt: TextInputEditText
    private lateinit var gymDayTimeLayout: TextInputLayout
    private lateinit var partnerPrefLayout: TextInputLayout
    private lateinit var partnerPrefEt: TextInputEditText
    private lateinit var submitBtn: AppCompatTextView
    private lateinit var circleImageView: CircleImageView
    private lateinit var backButton: AppCompatImageView

    private lateinit var user: User

    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory((activity?.application as FrainerApplication).repository)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        userNameEt = view.findViewById(R.id.usernameEt)
        userNameLayout = view.findViewById(R.id.usernameLayout)
        nameEt = view.findViewById(R.id.firstNameLastNameEt)
        nameLayout = view.findViewById(R.id.nameLayout)
        passwordEt = view.findViewById(R.id.passwordEt)
        passwordLayout = view.findViewById(R.id.passwordLayout)
        confirmPasswordEt = view.findViewById(R.id.confirmPasswordEt)
        confirmPasswordLayout = view.findViewById(R.id.confirmPasswordLayout)
        ageEt = view.findViewById(R.id.ageEt)
        ageLayout = view.findViewById(R.id.ageLayout)
        maleRadioButton = view.findViewById(R.id.maleBtn)
        femaleRadioButton = view.findViewById(R.id.femaleBtn)
        gymDayTimeEt = view.findViewById(R.id.gymDayTimeEt)
        gymDayTimeLayout = view.findViewById(R.id.gymDayTimeLayout)
        gymLocationEt = view.findViewById(R.id.gymLocationEt)
        gymLocationLayout = view.findViewById(R.id.gymLocationLayout)
        partnerPrefEt = view.findViewById(R.id.partnerPrefEt)
        partnerPrefLayout = view.findViewById(R.id.partnerPrefLayout)
        submitBtn = view.findViewById(R.id.submitBtn)
        circleImageView = view.findViewById(R.id.profile_image)
        backButton = view.findViewById(R.id.backButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        circleImageView.setOnClickListener {
            selectImage()
        }

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment)
        }

        submitBtn.setOnClickListener {

            when {
                TextUtils.isEmpty(userNameEt.text) -> {
                    Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(nameEt.text) -> {
                    Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(passwordEt.text) -> {
                    Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(confirmPasswordEt.text) -> {
                    Toast.makeText(context, "Confirm password cannot be empty", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(ageEt.text) -> {
                    Toast.makeText(context, "Age cannot be empty", Toast.LENGTH_SHORT).show()
                }
                !maleRadioButton.isChecked && !femaleRadioButton.isChecked -> {
                    Toast.makeText(context, "Please select gender", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(gymDayTimeEt.text) -> {
                    Toast.makeText(context, "Gym location cannot be empty", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(gymDayTimeEt.text) -> {
                    Toast.makeText(context, "Gym day/time cannot be empty", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(partnerPrefEt.text) -> {
                    Toast.makeText(context, "Partner preference cannot be empty", Toast.LENGTH_SHORT).show()
                }

                !passwordEt.text?.toString().equals(confirmPasswordEt.text?.toString()) -> {
                    Toast.makeText(context, "Password and confirm password does not match", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    user = User(userNameEt.text.toString(), nameEt.text.toString(), passwordEt.text.toString(), maleRadioButton.isChecked,
                            ageEt.text.toString(), gymLocationEt.text.toString(), gymDayTimeEt.text.toString(), partnerPrefEt.text.toString())
                    signupViewModel.getUser(userNameEt.text.toString())
                }
            }
        }

        activity?.let {
            signupViewModel.user.observe(it) {
                if (it != null) {
                    Toast.makeText(context, "Username already exists, please select different username", Toast.LENGTH_SHORT).show()

                } else {
                    SharePreferenceData.saveObject(context, "logged_user", LoggedInUser(user.userName, user.password))
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    signupViewModel.insert(user)
                    Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_myFrainerFragment)
                }
            }
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Cancel")
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (options[item] == "Cancel") {
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