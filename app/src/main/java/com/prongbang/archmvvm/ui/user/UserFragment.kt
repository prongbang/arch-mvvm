package com.prongbang.archmvvm.ui.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.prongbang.arch_mvvm.R
import com.prongbang.arch_mvvm.databinding.FragmentUserBinding
import com.prongbang.archmvvm.binding.FragmentDataBindingComponent
import com.prongbang.archmvvm.ui.base.BaseFragment
import com.prongbang.archmvvm.utils.Status
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : BaseFragment() {

    private lateinit var userViewModel: UserViewModel

    companion object {
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val mBinding = DataBindingUtil.inflate<FragmentUserBinding>(inflater, R.layout.fragment_user, container, false)

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        val adapter = UserAdapter(FragmentDataBindingComponent(this))
        mBinding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            setAdapter(adapter)
        }

        userViewModel.getUsers().observe(this, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    pbLoading.show()
                }
                Status.SUCCESS -> {
                    pbLoading.hide()
                    adapter.submitList(it.data)
                }
                Status.WARNING -> {
                    pbLoading.hide()
                    Toast.makeText(context, "Warning: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.UNAUTHORIZED -> {
                    pbLoading.hide()
                    Toast.makeText(context, "Unauthorized: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    pbLoading.hide()
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Status.NETWORK -> {
                    pbLoading.hide()
                    Toast.makeText(context, "Network: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    pbLoading.hide()
                    Toast.makeText(context, "Other: ${it?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return mBinding.root
    }

}
