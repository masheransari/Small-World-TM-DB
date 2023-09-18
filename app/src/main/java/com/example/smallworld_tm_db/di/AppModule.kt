package com.example.smallworld_tm_db.di

//import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.smallworld_tm_db.application.AppConstants
import com.example.smallworld_tm_db.data.IMovieRepositoryImpl
import com.example.smallworld_tm_db.data.local.LocalMovieDataSource
import com.example.smallworld_tm_db.data.remote.ApiService
import com.example.smallworld_tm_db.data.remote.RemoteMovieDataSource
import com.example.smallworld_tm_db.domain.IMovieRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(provideOkHttpClient())
        .build()


    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
//            .addInterceptor(ChuckerInterceptor.Builder(Application.applicationContext).build())
            .build()
    }


    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideProductRepository(
        dataSourceRemote: RemoteMovieDataSource,
        dataSourceLocal: LocalMovieDataSource,
    ): IMovieRepository {
        return IMovieRepositoryImpl(
            dataSourceRemote, dataSourceLocal
        )
    }



}

