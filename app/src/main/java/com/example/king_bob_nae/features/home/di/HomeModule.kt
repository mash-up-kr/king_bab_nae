package com.example.king_bob_nae.features.home.di

import com.example.king_bob_nae.features.home.data.HomeApi
import com.example.king_bob_nae.features.home.data.freindlist.RemoteGetFriendListImpl
import com.example.king_bob_nae.features.home.data.userstate.RemoteGetHomeUserStatusImpl
import com.example.king_bob_nae.features.home.domain.freindlist.RemoteGetFriendList
import com.example.king_bob_nae.features.home.domain.userstate.RemoteGetHomeUserStatus
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    companion object {
        @Provides
        @Singleton
        fun providesHomeApi(retrofit: Retrofit): HomeApi = retrofit.create()
    }

    @Binds
    @Singleton
    abstract fun bindRemoteGetHomeStatus(remoteGetHomeStatus: RemoteGetHomeUserStatusImpl): RemoteGetHomeUserStatus

    @Binds
    @Singleton
    abstract fun bindRemoteGetFriendList(remoteGetFriendList: RemoteGetFriendListImpl): RemoteGetFriendList
}