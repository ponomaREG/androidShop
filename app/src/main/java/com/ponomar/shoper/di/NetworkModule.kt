package com.ponomar.shoper.di

import com.ponomar.shoper.model.entities.Address
import com.ponomar.shoper.model.entities.User
import com.ponomar.shoper.network.*
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
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
object NetworkModule{


    @Provides
    @Singleton
    fun provideOkHttpClass():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .client(okHttpClient)
            .baseUrl("https://apiforandroidshop.herokuapp.com")
            .build()
    }


    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit):UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit):ProductService = retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideAddressService(retrofit: Retrofit):AddressService = retrofit.create(AddressService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit):AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit):OrderService = retrofit.create(OrderService::class.java)

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit):NewsService = retrofit.create(NewsService::class.java)


    @Provides
    @Singleton
    fun provideClient(userService: UserService,
                      productService: ProductService,
                      authService: AuthService,
                      orderService: OrderService,
                      newsService: NewsService
                      ):Client = Client(userService, productService,authService,orderService,newsService)

}