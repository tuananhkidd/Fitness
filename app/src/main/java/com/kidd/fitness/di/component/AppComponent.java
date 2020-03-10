package com.kidd.fitness.di.component;

import android.app.Application;

import com.kidd.fitness.BaseApplication;
import com.kidd.fitness.di.module.ActivityBindingModule;
import com.kidd.fitness.di.module.AppModule;
import com.kidd.fitness.di.module.FragmentBindingModule;
import com.kidd.fitness.di.module.NetworkModule;
import com.kidd.fitness.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        FragmentBindingModule.class,
        ViewModelModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(BaseApplication baseApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
