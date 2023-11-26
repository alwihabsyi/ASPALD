package com.aspald.aspald.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
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
            // Assuming 'createDataStore' is a function that creates a DataStore<Preferences> instance
            val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_preferences")
            return UserPreferences.getInstance(dataStore)
        }
    }
}

private fun Context.createDataStore(name: String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = { this.dataStoreFile(name) }
    )
}
