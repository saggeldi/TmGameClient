package com.game.tm.features.auth.di

import com.game.tm.features.auth.data.repository.AuthRepositoryImpl
import com.game.tm.features.auth.domain.repository.AuthRepository
import com.game.tm.features.auth.domain.usecase.AuthUseCase
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.auth.presentation.viewmodel.AuthViewModel
import com.game.tm.features.category.data.repository.CategoryRepositoryImpl
import com.game.tm.features.category.domain.repository.CategoryRepository
import com.game.tm.features.category.domain.usecase.CategoryUseCase
import com.game.tm.features.category.presentation.viewmodel.CategoryViewModel
import com.game.tm.features.game.data.repository.GameRepositoryImpl
import com.game.tm.features.game.domain.repository.GameRepository
import com.game.tm.features.game.domain.usecase.GameUseCase
import com.game.tm.features.game.presentation.viewmodel.GameViewModel
import com.game.tm.features.profile.data.repository.ProfileRepositoryImpl
import com.game.tm.features.profile.domain.repository.ProfileRepository
import com.game.tm.features.profile.domain.usecase.ProfileUseCase
import com.game.tm.features.profile.presentation.viewmodel.AppSettingsStore
import com.game.tm.features.profile.presentation.viewmodel.ProfileViewModel
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.factory
import org.koin.dsl.module
import java.util.prefs.Preferences

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
        )
    }

val ktorModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = false
                        prettyPrint = true
                        isLenient = false
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("[API-CALL]: $message")
                    }

                }
                level = LogLevel.ALL
            }
        }
    }
}

val viewModelModule = module {
    factory { AuthViewModel(get(), get()) }
    factory { ProfileViewModel(get()) }
    factory { CategoryViewModel(get()) }
    factory { GameViewModel(get(), get()) }
}

val useCasesModule: Module = module {
    factory { AuthUseCase(get()) }
    factory { ProfileUseCase(get()) }
    factory { CategoryUseCase(get()) }
    factory { GameUseCase(get()) }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<Settings> { PreferencesSettings(Preferences.userRoot()) }
    single { AuthSettings(get()) }
    single { AppSettingsStore(get()) }

    // profile
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<GameRepository> { GameRepositoryImpl(get()) }
}

fun initKoin() = initKoin {}