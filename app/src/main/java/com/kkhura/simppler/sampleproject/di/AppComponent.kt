package com.kkhura.simppler.sampleproject.di

import android.app.Application
import com.kkhura.simppler.sampleproject.ui.home.repo.HomeActivityRepo
import com.kkhura.simppler.sampleproject.ui.home.view.HomeActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ContextModule::class]
//            AndroidInjectionModule::class,
//            FragmentBuildersModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun contextModule(contextModule: ContextModule): Builder

        fun build(): AppComponent
    }

    fun inject(repo: HomeActivityRepo)
}
