package com.tcc.alif.data.repository

import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.model.Signin
import com.tcc.alif.data.model.SigninResponse
import retrofit2.Response
import javax.inject.Inject

interface SigninRepository {
    suspend fun signin(signin : Signin) : Response<SigninResponse>
}

class SigninRepositoryImpl @Inject constructor(
    private val alifService: AlifService
) : SigninRepository{


    override suspend fun signin(signin: Signin) : Response<SigninResponse> = alifService.signin(signin)

}