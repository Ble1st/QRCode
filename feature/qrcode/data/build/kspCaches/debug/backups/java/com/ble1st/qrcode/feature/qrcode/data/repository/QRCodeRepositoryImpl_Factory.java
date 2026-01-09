package com.ble1st.qrcode.feature.qrcode.data.repository;

import com.ble1st.qrcode.feature.qrcode.data.datasource.FileStorageManager;
import com.ble1st.qrcode.feature.qrcode.data.datasource.QRCodeGenerator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class QRCodeRepositoryImpl_Factory implements Factory<QRCodeRepositoryImpl> {
  private final Provider<QRCodeGenerator> qrCodeGeneratorProvider;

  private final Provider<FileStorageManager> fileStorageManagerProvider;

  private QRCodeRepositoryImpl_Factory(Provider<QRCodeGenerator> qrCodeGeneratorProvider,
      Provider<FileStorageManager> fileStorageManagerProvider) {
    this.qrCodeGeneratorProvider = qrCodeGeneratorProvider;
    this.fileStorageManagerProvider = fileStorageManagerProvider;
  }

  @Override
  public QRCodeRepositoryImpl get() {
    return newInstance(qrCodeGeneratorProvider.get(), fileStorageManagerProvider.get());
  }

  public static QRCodeRepositoryImpl_Factory create(
      Provider<QRCodeGenerator> qrCodeGeneratorProvider,
      Provider<FileStorageManager> fileStorageManagerProvider) {
    return new QRCodeRepositoryImpl_Factory(qrCodeGeneratorProvider, fileStorageManagerProvider);
  }

  public static QRCodeRepositoryImpl newInstance(QRCodeGenerator qrCodeGenerator,
      FileStorageManager fileStorageManager) {
    return new QRCodeRepositoryImpl(qrCodeGenerator, fileStorageManager);
  }
}
