package com.yxl.student_guide.core.di

import android.content.Context
import androidx.room.Room
import com.yxl.student_guide.core.data.Api
import com.yxl.student_guide.core.db.InstituteDatabase
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
object MainModule {

    @Provides
    @Singleton
    fun provideApi(): Api = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    @Singleton
    @Provides
    fun provideInstituteDatabase(@ApplicationContext context: Context): InstituteDatabase {
        return Room.databaseBuilder(
            context,
            InstituteDatabase::class.java,
            InstituteDatabase.DATABASE_NAME
        ).createFromAsset("database/student_guide.db").build()
    }

    @Singleton
    @Provides
    fun provideInstituteDao(db: InstituteDatabase) = db.InstituteDao()

    @Singleton
    @Provides
    fun provideScoreDao(db: InstituteDatabase) = db.ScoreDao()

    @Singleton
    @Provides
    fun provideSubjectDao(db: InstituteDatabase) = db.SubjectDao()

}
