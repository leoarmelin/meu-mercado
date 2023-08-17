package com.leoarmelin.meumercado.dagger

import com.leoarmelin.meumercado.useCases.CreateCategoryUseCase
import com.leoarmelin.meumercado.useCases.DeleteCategoryUseCase
import com.leoarmelin.meumercado.useCases.GetDateIntervalUseCase
import com.leoarmelin.meumercado.useCases.ObserveAllCategoriesUseCase
import com.leoarmelin.meumercado.useCases.UpdateCategoryUseCase
import com.leoarmelin.meumercado.viewmodels.SharedViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModule {

    @Provides
    @ActivityRetainedScoped
    fun provideSharedViewModel(
        observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
        getDateIntervalUseCase: GetDateIntervalUseCase,
        createCategoryUseCase: CreateCategoryUseCase,
        updateCategoryUseCase: UpdateCategoryUseCase,
        deleteCategoryUseCase: DeleteCategoryUseCase
    ): SharedViewModel = SharedViewModel(
        observeAllCategoriesUseCase,
        getDateIntervalUseCase,
        createCategoryUseCase,
        updateCategoryUseCase,
        deleteCategoryUseCase
    )
}