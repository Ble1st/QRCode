package com.ble1st.qrcode.feature.qrcode.ui.viewmodel;

import com.ble1st.qrcode.feature.qrcode.domain.usecase.GenerateQRCodeUseCase;
import com.ble1st.qrcode.feature.qrcode.domain.usecase.SaveQRCodeUseCase;
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
public final class QRCodeViewModel_Factory implements Factory<QRCodeViewModel> {
  private final Provider<GenerateQRCodeUseCase> generateQRCodeUseCaseProvider;

  private final Provider<SaveQRCodeUseCase> saveQRCodeUseCaseProvider;

  private QRCodeViewModel_Factory(Provider<GenerateQRCodeUseCase> generateQRCodeUseCaseProvider,
      Provider<SaveQRCodeUseCase> saveQRCodeUseCaseProvider) {
    this.generateQRCodeUseCaseProvider = generateQRCodeUseCaseProvider;
    this.saveQRCodeUseCaseProvider = saveQRCodeUseCaseProvider;
  }

  @Override
  public QRCodeViewModel get() {
    return newInstance(generateQRCodeUseCaseProvider.get(), saveQRCodeUseCaseProvider.get());
  }

  public static QRCodeViewModel_Factory create(
      Provider<GenerateQRCodeUseCase> generateQRCodeUseCaseProvider,
      Provider<SaveQRCodeUseCase> saveQRCodeUseCaseProvider) {
    return new QRCodeViewModel_Factory(generateQRCodeUseCaseProvider, saveQRCodeUseCaseProvider);
  }

  public static QRCodeViewModel newInstance(GenerateQRCodeUseCase generateQRCodeUseCase,
      SaveQRCodeUseCase saveQRCodeUseCase) {
    return new QRCodeViewModel(generateQRCodeUseCase, saveQRCodeUseCase);
  }
}
