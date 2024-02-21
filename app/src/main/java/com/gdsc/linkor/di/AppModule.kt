package com.gdsc.linkor.di

import com.gdsc.linkor.network.ApiService
import com.gdsc.linkor.repository.MessageRepository
import com.gdsc.linkor.repository.SignInRepository
import com.gdsc.linkor.repository.TutorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApiService():ApiService{
        return Retrofit.Builder()
            .baseUrl("***REMOVED***")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTutorRepository(api:ApiService) = TutorRepository(api)

    @Singleton
    @Provides
    fun provideMessageRepository(api:ApiService) = MessageRepository(api)

    @Singleton
    @Provides
    fun provideSignInRepository(api:ApiService) = SignInRepository(api)
}
