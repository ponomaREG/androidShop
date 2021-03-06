package com.ponomar.shoper.repository

import com.ponomar.shoper.db.AppDB
import com.ponomar.shoper.db.DaoHolder
import com.ponomar.shoper.network.Client
import com.skydoves.sandwich.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.onFailure

class AuthRepository @Inject constructor(
        private val client: Client,
        private val daoHolder: DaoHolder
) {
    suspend fun sendUserDataToGenerateAuthCode(
            phone:String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        client.sendUserDataToGenerateCode(phone)
                .suspendOnSuccess {
                    if(data != null) {
                        when {
                            data!!.status == 22 -> {
                                onError("На этот номер уже зарегистрирован аккаунт")
                            }
                            data!!.status != 0 -> {
                                onError("ERROR.STATUS:${data!!.status}")
                            }
                            else -> {
                                emit(data!!.code)
                            }
                        }
                    }else onError("ERROR")
                    onSuccess()
                }.onError { onError(message())  }
                .onException { onError(message()) }
                .onFailure { onSuccess() }

    }

    suspend fun sendUserDataToGenerateAuthCodeWhenUserTryToLogin(
            phone: String,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow{
        client.sendUserDataToGenerateCodeWhenUserTryToLogin(phone)
                .suspendOnSuccess {
                    if(data!=null){
                        when(data!!.status){
                            61 ->{onError("Вы незарегистрированны")}
                            11 -> {onError("Произошла ошибка на сервере")}
                            else -> {
                                emit(data!!.code)
                            }
                        }
                        onComplete()
                    }else {onError("Попробуйте позже")}
                }.onError { onError(message()) }
                .onException { onError(message()) }
                .onFailure { onComplete() }
    }



    suspend fun verifyCode(
            phone:String,
            firstName:String? = null,
            code:Int,
            onComplete: () -> Unit,
            onError: (String) -> Unit
    ) = flow {
        client.verifyCode(code, phone, firstName)
                .suspendOnSuccess {
                    if(data != null) {
                        if (data!!.status != 30) {
                            onError("ERROR.STATUS:${data!!.status}")
                        }else {
                            val userFromDao = daoHolder.userDAO.getUserById(data!!.uid!!)
                            if(userFromDao == null) daoHolder.userDAO.nukeTable()
                            else daoHolder.userDAO.nukeTableExceptById(userFromDao.id)
                            daoHolder.productDAO.nukeTable()
                            daoHolder.cartDAO.nukeTable()
                            emit(data!!.token!!)
                        }
                    }else onError("ERROR")
                    onComplete()
                }.onError { onError(message()) }
                .onException { onError(message()) }
                .onFailure { onComplete() }}

}