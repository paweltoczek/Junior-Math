package com.amadev.juniormath.di

import com.amadev.juniormath.data.repository.FirebaseUserData
import com.amadev.juniormath.data.repository.RealtimeDatabaseRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {


    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseUserData(): FirebaseUserData {
        return FirebaseUserData(
            FirebaseAuth.getInstance(),
            FirebaseDatabase.getInstance()
        )
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(): RealtimeDatabaseRepositoryImpl {
        return RealtimeDatabaseRepositoryImpl(
            FirebaseDatabase.getInstance(),
            provideFirebaseUserData()
        )
    }
}