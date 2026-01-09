package com.ble1st.qrcode.feature.qrcode.data.di

import android.content.Context
import com.ble1st.qrcode.feature.qrcode.data.datasource.FileStorageManager
import com.ble1st.qrcode.feature.qrcode.data.datasource.QRCodeGenerator
import com.ble1st.qrcode.feature.qrcode.data.repository.QRCodeRepositoryImpl
import com.ble1st.qrcode.feature.qrcode.domain.repository.QRCodeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    
    @Binds
    @Singleton
    fun bindQRCodeRepository(
        qrCodeRepositoryImpl: QRCodeRepositoryImpl
    ): QRCodeRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    
    @Provides
    @Singleton
    fun provideQRCodeGenerator(): QRCodeGenerator {
        return QRCodeGenerator()
    }
    
    @Provides
    @Singleton
    fun provideFileStorageManager(
        @ApplicationContext context: Context
    ): FileStorageManager {
        return FileStorageManager(context)
    }
}
