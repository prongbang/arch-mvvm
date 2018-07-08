package com.prongbang.archmvvm.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.prongbang.archmvvm.api.ApiService
import com.prongbang.archmvvm.db.AppDatabase
import com.prongbang.archmvvm.utils.AppExecutors
import com.prongbang.archmvvm.utils.Constant
import com.prongbang.archmvvm.utils.NoNetworkException
import com.prongbang.archmvvm.utils.Resource
import com.prongbang.archmvvm.vo.User
import retrofit2.Call
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(private val appExecutors: AppExecutors,
                                         private val appDatabase: AppDatabase,
                                         private val apiService: ApiService) {

    fun getUsers(): LiveData<Resource<List<User>>> {
        val data = MutableLiveData<Resource<List<User>>>()

        data.postValue(Resource.loading(null))

        NetworkBoundResource(appExecutors, object : NetworkBoundResource.Callback<List<User>, List<User>>() {

            override fun onUnauthorized() {
                data.postValue(Resource.unauthorized("", null))
            }

            override fun onCreateCall(): Call<List<User>> = apiService.getUser()

            override fun onResponse(code: Int, message: String, item: List<User>?) {
                when (code) {
                    in 200..201 -> {
                        if (item != null) {
                            appExecutors.diskIO().execute {
                                // create or update
                                for (u in item) {
                                    appDatabase.userDao().insert(u)
                                }
                                data.postValue(Resource.success(item))
                            }
                        } else {
                            data.postValue(Resource.error(message, null))
                        }
                    }
                    else -> {
                        data.postValue(Resource.error(message, null))
                    }
                }
            }

            override fun onError(t: Throwable) {
                Timber.e("onError: ${t.message}")
                data.postValue(Resource.error(t.message!!, null))
            }

            override fun onWarning(t: Throwable) {
                Timber.e("onWarning: ${t.message}")
                data.postValue(Resource.warning(t.message!!, null))
            }

            override fun onNetworkUnavailable(t: NoNetworkException) {
                Timber.e("onNetworkUnavailable: ${t.message}")
                data.postValue(Resource.network(t.message!!, null))
            }

            override fun enableLoadFromDb(): Boolean = NetworkBoundResource.LOAD_FROM_DB_ENABLE

            override fun onLoadFromDb(): LiveData<List<User>> = appDatabase.userDao().getUsers()

            override fun onDatabaseResponse(item: List<User>?) {
                item?.apply {
                    data.postValue(Resource.success(item))
                }
            }

        })

        return data
    }


}