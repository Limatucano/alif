package com.tcc.alif.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tcc.alif.data.api.CepService
import com.tcc.alif.data.api.OneSignalService
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
        firebaseFirestore: FirebaseFirestore,
        oneSignalService: OneSignalService
    ) : AdministratorDataSource = AdministratorDataSource(
        firebaseFirestore = firebaseFirestore,
        oneSignalService = oneSignalService
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
        administratorDataSource: AdministratorDataSource,
        companyDataSource: CompanyDataSource
    ) : EmployeeDataSource = EmployeeDataSource(
        firebaseFirestore = firebaseFirestore,
        administratorDataSource = administratorDataSource,
        companyDataSource = companyDataSource
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

    @Provides
    fun provideHomeDataSource(
        firebaseFirestore: FirebaseFirestore
    ) : HomeDataSource = HomeDataSource(
        firestore = firebaseFirestore
    )

    @Provides
    fun provideHomeRepository(
        homeDataSource: HomeDataSource
    ): HomeRepository = HomeRepository(
        homeDataSource = homeDataSource
    )

    @Provides
    fun provideConsumerDataSource(
        firebaseFirestore: FirebaseFirestore,
        companyDataSource: CompanyDataSource
    ) : ConsumerDataSource = ConsumerDataSource(
        firebaseFirestore = firebaseFirestore,
        companyDataSource = companyDataSource
    )

    @Provides
    fun provideConsumerRepository(
        consumerDataSource: ConsumerDataSource
    ) : ConsumerRepository = ConsumerRepository(
        consumerDataSource = consumerDataSource
    )
}