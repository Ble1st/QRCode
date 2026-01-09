package com.ble1st.qrcode.feature.qrcode.data.di;

import com.ble1st.qrcode.feature.qrcode.data.datasource.QRCodeGenerator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DataSourceModule_ProvideQRCodeGeneratorFactory implements Factory<QRCodeGenerator> {
  @Override
  public QRCodeGenerator get() {
    return provideQRCodeGenerator();
  }

  public static DataSourceModule_ProvideQRCodeGeneratorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static QRCodeGenerator provideQRCodeGenerator() {
    return Preconditions.checkNotNullFromProvides(DataSourceModule.INSTANCE.provideQRCodeGenerator());
  }

  private static final class InstanceHolder {
    static final DataSourceModule_ProvideQRCodeGeneratorFactory INSTANCE = new DataSourceModule_ProvideQRCodeGeneratorFactory();
  }
}
