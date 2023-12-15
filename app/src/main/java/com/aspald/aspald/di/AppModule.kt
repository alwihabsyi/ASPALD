package com.aspald.aspald.di

import android.content.Context
import com.aspald.aspald.data.api.ApiServices
import com.aspald.aspald.data.repository.AuthRepository
import com.aspald.aspald.utils.Constants.BASE_URL
import com.aspald.aspald.utils.Constants.client
import com.aspald.aspald.utils.LocationService
import com.aspald.aspald.utils.LocationServiceInterface
import com.aspald.aspald.utils.UserPreferences
import com.google.android.gms.location.LocationServices
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
    fun provideApiService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiServices: ApiServices,
        userPreferences: UserPreferences
    ): AuthRepository = AuthRepository(apiServices, userPreferences)
}