package com.nazarrybickij.trainingtask.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialContainerTransform
import com.nazarrybickij.trainingtask.App
import com.nazarrybickij.trainingtask.R
import com.nazarrybickij.trainingtask.entities.UserDataEntity
import com.nazarrybickij.trainingtask.entities.UserEntity
import com.nazarrybickij.trainingtask.viewmodels.UserViewModel
import com.squareup.picasso.Picasso


class UserFragment : Fragment() {
    private var idUser = 0
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        try {
            idUser = arguments?.getInt("id")!!
            activity?.title = ""
        } catch (e: Exception) {
        }
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailTextView = view.findViewById<TextView>(R.id.email_textView)
        viewModel.getUser(idUser).observe(viewLifecycleOwner, {
            setData(view,it)
        })
        emailTextView.setOnClickListener {
            sendMail(emailTextView.text as String)
        }
    }

    fun setData(view: View,it:Pair<Boolean,UserEntity>){
        val avatarImage = view.findViewById<ImageView>(R.id.avatar_imageView)
        val nameTextView = view.findViewById<TextView>(R.id.name_textView)
        val emailTextView = view.findViewById<TextView>(R.id.email_textView)
        var firstName:String
        var lastName:String
        var avatar:String
        var email:String
        if (it.first){
            firstName = it.second.data.firstName
            lastName = it.second.data.lastName
            avatar = it.second.data.avatar
            email = it.second.data.email
        }else{
            var user = App.database.userDao().getUserById(idUser)[0]
            firstName = user.firstName
            lastName = user.lastName
            avatar = user.avatar
            email = user.email
        }
        Picasso.with(context)
            .load(avatar)
            .into(avatarImage)
        nameTextView.text = "${firstName} ${lastName}"
        emailTextView.text = email
        view.findViewById<TextView>(R.id.textView).visibility = LinearLayout.VISIBLE
        activity?.title = "${firstName} ${lastName}"
    }

    fun sendMail(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        val recipients = arrayOf(email)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.type = "text/html"
        intent.setPackage("com.google.android.gm")
        startActivity(Intent.createChooser(intent, "Send mail"))
    }

}