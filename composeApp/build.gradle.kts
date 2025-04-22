import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import kotlin.io.path.pathString

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
}


kotlin {
    jvm {
        withJava()
    }


    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.coil)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.moko.mvvm)
            implementation(libs.ktor.core)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.koin.core)
            implementation(libs.kstore)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.client.logging)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.json)
            implementation(kotlin("reflect"))
            api("io.github.kevinnzou:compose-webview-multiplatform:1.8.0")

            // Required
            implementation(libs.lyricist)

            // toast
            implementation(libs.toast)

            implementation(libs.ktor.client.cio)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

        }

        appleMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation("uk.co.caprica:vlcj:4.8.2")

        }

    }
}


dependencies {
    add("kspCommonMainMetadata", libs.lyricist.processor)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
    jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
}

//tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
//    if (name != "kspCommonMainKotlinMetadata") {
//        dependsOn("kspCommonMainKotlinMetadata")
//    }
//    kotlinOptions.freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
//}
tasks.withType<com.google.devtools.ksp.gradle.KspTaskJvm> {
    enabled = false
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(tasks.withType<com.google.devtools.ksp.gradle.KspTaskMetadata>())
}
kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

ksp {
    arg("lyricist.internalVisibility", "true")
    arg("lyricist.generateStringsProperty", "true")
}



compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Elektron Sport"
            packageVersion = "1.0.0"
            appResourcesRootDir.dir(project.rootDir.toPath().pathString + "assets")


            macOS {
                iconFile.set(project.file("icons/logo.icns"))
            }
            windows {
                shortcut = true
                dirChooser = true
                perUserInstall = true
                iconFile.set(project.file("icons/logo.ico"))
            }
            linux {
                iconFile.set(project.file("icons/logo.png"))
            }
        }

        buildTypes.release.proguard {
            isEnabled.set(false)
        }
    }
}
