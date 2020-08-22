package com.eatl.onlineattendance.di;

import android.app.Application;

import com.eatl.onlineattendance.di.component.AppComponent;
import com.eatl.onlineattendance.di.component.DaggerAppComponent;
import com.eatl.onlineattendance.di.module.AppModule;

public class App extends Application {
    private AppComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        //needs to run once to generate it
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
    public AppComponent getComponent() {
        return component;
    }
}
