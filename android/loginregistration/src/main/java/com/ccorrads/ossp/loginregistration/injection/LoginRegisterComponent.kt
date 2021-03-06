package com.ccorrads.ossp.loginregistration.injection

import android.content.Context
import com.ccorrads.ossp.core.injection.DatabaseModule
import com.ccorrads.ossp.core.injection.NetworkModule
import com.ccorrads.ossp.loginregistration.LoginRegisterActivity
import com.ccorrads.ossp.loginregistration.LoginRegisterFragment
import com.ccorrads.ossp.loginregistration.registration.RegistrationFragment
import com.ccorrads.ossp.loginregistration.registration.injection.RegisterModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class, LoginRegisterModule::class, RegisterModule::class])
interface LoginRegisterComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): LoginRegisterComponent
    }

    fun inject(activity: LoginRegisterActivity)

    fun plus(fragment: LoginRegisterFragment)

    fun plus(fragment: RegistrationFragment)
}