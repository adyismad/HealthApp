package edu.bu.metcs.myproject.editprofile

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
import edu.bu.metcs.myproject.*
import edu.bu.metcs.myproject.myprofile.MyProfileViewModel
import edu.bu.metcs.myproject.myprofile.MyProfileViewModelFactory
import edu.bu.metcs.myproject.data.User
import kotlinx.android.synthetic.main.edit_profile_fragment.*

class EditProfileFragment : Fragment() {

    private val profileViewModel: MyProfileViewModel by viewModels {
        MyProfileViewModelFactory((activity?.application as FrainerApplication).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_image.setOnClickListener {
            selectImage()
        }

        backButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        saveBtn.setOnClickListener {

            when {

                TextUtils.isEmpty(firstNameLastNameEt.text) -> {
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
                !maleBtn.isChecked && !femaleBtn.isChecked -> {
                    Toast.makeText(context, "Please select gender", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(gymLocationEt.text) -> {
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
                    profileViewModel.saveUser(User(usernameEt.text.toString(), firstNameLastNameEt.text.toString(), passwordEt.text.toString(), maleBtn.isChecked,
                            ageEt.text.toString(), gymLocationEt.text.toString(), gymDayTimeEt.text.toString(), partnerPrefEt.text.toString()))
                    Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment)
                }
            }
        }

        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)

        val bundle = arguments
        if (bundle != null) {
            val it: User = bundle.getSerializable("User") as User
            usernameEt.setText(it.userName)
            firstNameLastNameEt.setText(it.name)
            passwordEt.setText(it.password)
            confirmPasswordEt.setText(it.password)
            ageEt.setText(it.age)
            gymDayTimeEt.setText(it.daytime)
            gymLocationEt.setText(it.location)
            partnerPrefEt.setText(it.partnerPref)
            if (it.male) maleBtn.isChecked = true else femaleBtn.isChecked = true
        }
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
                    profile_image.setImageBitmap(selectedImage)
                }
            }
        }
    }
}