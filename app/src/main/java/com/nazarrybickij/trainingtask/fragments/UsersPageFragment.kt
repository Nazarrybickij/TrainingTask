package com.nazarrybickij.trainingtask.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.trainingtask.App
import com.nazarrybickij.trainingtask.R
import com.nazarrybickij.trainingtask.UsersAdapter
import com.nazarrybickij.trainingtask.entities.UserDataEntity
import com.nazarrybickij.trainingtask.entities.UsersPageEntity
import com.nazarrybickij.trainingtask.viewmodels.UsersPageViewModel


class UsersPageFragment:Fragment() {
    private lateinit var usersRecyclerView:RecyclerView
    private var adapter = UsersAdapter()
    private lateinit var viewModel: UsersPageViewModel
    private var listUserDataEntity:MutableList<UserDataEntity>? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var loaded = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UsersPageViewModel::class.java)
        activity?.title = App.getResources.getString(R.string.app_name)
        return inflater.inflate(R.layout.users_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callbackAdapter = object :
            UsersAdapter.AdapterCallback {
            override fun onUserClick(id: Int) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    view.findNavController().navigate(
                        R.id.action_usersPageFragment_to_userFragment,
                        bundle
                    )
                } catch (e: Exception) {
                }
            }
        }
        usersRecyclerView = view.findViewById(R.id.users_recyclerView)
        mLayoutManager = LinearLayoutManager(context)
        usersRecyclerView.layoutManager  = mLayoutManager
        adapter.callback = callbackAdapter
        usersRecyclerView.adapter = adapter
        viewModel.getPageUsers().observe(viewLifecycleOwner, {
            setData(it)
        })
        usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(mLayoutManager!!.findLastVisibleItemPosition() == (listUserDataEntity?.size)?.minus(1)){
                    if (loaded){
                        viewModel.page++
                        viewModel.getPageUsers()
                        loaded = false
                    }
                }
            }
        })
    }
    private fun setData(it: Pair<Boolean,UsersPageEntity>){
        if (!it.first){
            adapter.listUsers = App.database.userDao().getAll() as MutableList<UserDataEntity>
            viewModel.page = 1
            return
        }
        viewModel.totalPage = it.second.totalPages
        adapter.listUsers = it.second.data as MutableList<UserDataEntity>
        if (listUserDataEntity != null){
            if (it.second.data != listUserDataEntity!!){
                loaded = true
                viewModel.saveUsers(it.second.data)
            }
        }
        listUserDataEntity = it.second.data.toCollection(mutableListOf())
    }
}