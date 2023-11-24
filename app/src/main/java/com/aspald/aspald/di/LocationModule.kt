package com.aspald.aspald.di

import android.content.Context
import com.aspald.aspald.utils.LocationService
import com.aspald.aspald.utils.LocationServiceInterface
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext context: Context
    ): LocationServiceInterface = LocationService(
        context,
        LocationServices.getFusedLocationProviderClient(context)
    )
}