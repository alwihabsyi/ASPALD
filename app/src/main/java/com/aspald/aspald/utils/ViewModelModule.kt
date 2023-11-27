package com.aspald.aspald.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.aspald.aspald.data.AuthRepository
import com.aspald.aspald.data.api.ApiConfig
import com.aspald.aspald.data.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore("app_preferences")
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideAuthRepository(
        apiServices: ApiServices,
        userPreferences: UserPreferences
    ) : AuthRepository {
        return AuthRepository(apiServices, userPreferences)
    }


    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Singleton
        @Provides
        fun provideApiServices(): ApiServices {
            return ApiConfig.getApiService()
        }

        @Singleton
        @Provides
        fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
            return UserPreferences.getInstance(context.dataStore)
        }
    }
}

