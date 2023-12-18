package com.aspald.aspald.di

import android.content.Context
import com.aspald.aspald.data.Preferences
import com.aspald.aspald.data.remote.ApiServices
import com.aspald.aspald.data.repository.AuthRepository
import com.aspald.aspald.data.repository.ReportRepository
import com.aspald.aspald.utils.Constants
import com.aspald.aspald.utils.Constants.BASE_URL
import com.aspald.aspald.utils.LocationService
import com.aspald.aspald.utils.LocationServiceInterface
import com.aspald.aspald.utils.getInterceptor
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationClient(
        @ApplicationContext context: Context
    ): LocationServiceInterface = LocationService(
        context,
        LocationServices.getFusedLocationProviderClient(context)
    )

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context
    ): ApiServices {
        val sharedPref = Preferences.init(context, "session")
        val token = sharedPref.getString(Constants.TOKEN, "")

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor(token))
            .build()
            .create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideReportRepository(
        apiServices: ApiServices
    ): ReportRepository = ReportRepository(apiServices)

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiServices: ApiServices,
        @ApplicationContext context: Context
    ): AuthRepository = AuthRepository(apiServices, context)
}