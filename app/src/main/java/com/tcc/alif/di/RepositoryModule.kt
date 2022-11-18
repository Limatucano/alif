package com.tcc.alif.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.api.CepService
import com.tcc.alif.data.datasource.*
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
    fun provideAdministratorDataSource(
        firebaseFirestore: FirebaseFirestore
    ) : AdministratorDataSource = AdministratorDataSource(
        firebaseFirestore = firebaseFirestore
    )

    @Provides
    fun providesConfigurationDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ) : ConfigurationDataSource = ConfigurationDataSource(
        firebaseFirestore = firebaseFirestore,
        firebaseAuth = firebaseAuth
    )

    @Provides
    fun providesConfigurationRepository(
        configurationDataSource: ConfigurationDataSource
    ) : ConfigurationRepository = ConfigurationRepository(
        configurationDataSource = configurationDataSource
    )

    @Provides
    fun provideAdministratorRepository(
        administratorDataSource: AdministratorDataSource
    ) : AdministratorRepository = AdministratorRepository(
        administratorDataSource = administratorDataSource
    )

    @Provides
    fun provideEmployeeDataSource(
        firebaseFirestore: FirebaseFirestore,
        administratorDataSource: AdministratorDataSource
    ) : EmployeeDataSource = EmployeeDataSource(
        firebaseFirestore = firebaseFirestore,
        administratorDataSource = administratorDataSource
    )

    @Provides
    fun provideEmployeeRepository(
        employeeDataSource: EmployeeDataSource
    ) : EmployeeRepository = EmployeeRepository(
        employeeDataSource = employeeDataSource
    )

    @Provides
    fun provideCompanyRepository(
        companyDataSource: CompanyDataSource
    ) : CompanyRepository = CompanyRepository(
        dataSource = companyDataSource
    )
}