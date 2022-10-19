package com.tcc.alif.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.api.CepService
import com.tcc.alif.data.datasource.CompanyDataSource
import com.tcc.alif.data.datasource.SignInDataSource
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
        signInDataSource: SignInDataSource
    ) : SigninRepository = SigninRepositoryImpl(signInDataSource)

    @Provides
    fun provideSignInDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ) : SignInDataSource = SignInDataSource(
        firebaseAuth = firebaseAuth,
        firebaseFirestore = firebaseFirestore
    )

    @Provides
    fun provideCompanyDataSource(
        firebaseFirestore: FirebaseFirestore,
        cepService: CepService
    ) : CompanyDataSource = CompanyDataSource(
        firebaseFirestore = firebaseFirestore,
        cepService = cepService
    )

    @Provides
    fun provideAdministratorRepository(
        alifService: AlifService
    ) : AdministratorRepository = AdministratorRepositoryImpl(alifService)

    @Provides
    fun provideCompanyRepository(
        companyDataSource: CompanyDataSource
    ) : CompanyRepository = CompanyRepository(
        dataSource = companyDataSource
    )
}