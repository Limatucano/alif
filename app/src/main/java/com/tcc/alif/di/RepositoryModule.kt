package com.tcc.alif.di


import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideSigninRepository(
        alifService: AlifService
    ) : SigninRepository = SigninRepositoryImpl(alifService)

    @Provides
    fun provideAdministratorRepository(
        alifService: AlifService
    ) : AdministratorRepository = AdministratorRepositoryImpl(alifService)
}