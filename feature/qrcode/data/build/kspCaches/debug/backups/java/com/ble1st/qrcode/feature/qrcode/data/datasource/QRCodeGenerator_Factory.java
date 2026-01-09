package com.ble1st.qrcode.feature.qrcode.data.datasource;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class QRCodeGenerator_Factory implements Factory<QRCodeGenerator> {
  @Override
  public QRCodeGenerator get() {
    return newInstance();
  }

  public static QRCodeGenerator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static QRCodeGenerator newInstance() {
    return new QRCodeGenerator();
  }

  private static final class InstanceHolder {
    static final QRCodeGenerator_Factory INSTANCE = new QRCodeGenerator_Factory();
  }
}
