package com.ble1st.qrcode.feature.qrcode.data.datasource;

import android.content.Context;
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
public final class FileStorageManager_Factory implements Factory<FileStorageManager> {
  private final Provider<Context> contextProvider;

  private FileStorageManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public FileStorageManager get() {
    return newInstance(contextProvider.get());
  }

  public static FileStorageManager_Factory create(Provider<Context> contextProvider) {
    return new FileStorageManager_Factory(contextProvider);
  }

  public static FileStorageManager newInstance(Context context) {
    return new FileStorageManager(context);
  }
}
