plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.muedsa.tvbox.api"

    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    // 提供以下依赖的API给TvBox和插件使用
    val protobufVersion = "4.31.1"
    val jsoupVersion = "1.21.1"
    val ktxJsonVersion = "1.9.0"
    val retrofitVersion = "3.0.0"
    val retrofitKtxSerialization = "3.0.0"
    val retrofitProtobuf = "3.0.0"
    val okhttp3LoggingVersion = "4.12.0"
    val okhttp3BrotliVersion = "4.12.0"
    val timberVersion = "5.0.1"
    val junitVersion = "4.13.2"
    val kotlinxCoroutinesVersion = "1.10.2"

    api("com.google.protobuf:protobuf-javalite:$protobufVersion")
    api("com.google.protobuf:protobuf-kotlin-lite:$protobufVersion")
    api("org.jsoup:jsoup:$jsoupVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:$ktxJsonVersion")
    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    api("com.squareup.retrofit2:converter-kotlinx-serialization:$retrofitKtxSerialization")
    api("com.squareup.retrofit2:converter-protobuf:$retrofitProtobuf") {
        exclude("com.google.protobuf")
    }
    api("com.squareup.okhttp3:logging-interceptor:$okhttp3LoggingVersion")
    api("com.squareup.okhttp3:okhttp-brotli:$okhttp3BrotliVersion")
    api("com.jakewharton.timber:timber:$timberVersion")
    compileOnlyApi("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")
}