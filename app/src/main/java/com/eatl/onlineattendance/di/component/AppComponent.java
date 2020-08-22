package com.eatl.onlineattendance.di.component;

import com.eatl.onlineattendance.di.module.AppModule;
import com.eatl.onlineattendance.di.module.NetModule;
import com.eatl.onlineattendance.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
