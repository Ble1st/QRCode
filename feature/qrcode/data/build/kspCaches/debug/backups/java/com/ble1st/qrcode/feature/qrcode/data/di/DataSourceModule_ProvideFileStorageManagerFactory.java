package com.ble1st.qrcode.feature.qrcode.data.di;

import android.content.Context;
import com.ble1st.qrcode.feature.qrcode.data.datasource.FileStorageManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DataSourceModule_ProvideFileStorageManagerFactory implements Factory<FileStorageManager> {
  private final Provider<Context> contextProvider;

  private DataSourceModule_ProvideFileStorageManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public FileStorageManager get() {
    return provideFileStorageManager(contextProvider.get());
  }

  public static DataSourceModule_ProvideFileStorageManagerFactory create(
      Provider<Context> contextProvider) {
    return new DataSourceModule_ProvideFileStorageManagerFactory(contextProvider);
  }

  public static FileStorageManager provideFileStorageManager(Context context) {
    return Preconditions.checkNotNullFromProvides(DataSourceModule.INSTANCE.provideFileStorageManager(context));
  }
}
