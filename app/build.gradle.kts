plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}


android {
    namespace = "com.xiaoxiaoying.recyclerarrayadapter.demo"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.xiaoxiaoying.recyclerarrayadapter.demo"
        minSdk = libs.versions.minSdk.get().toInt()
        versionCode = 159
        versionName = "1.5.9"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
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
}

dependencies {
    implementation(libs.bundles.base)
    implementation(project(":recyclerarrayadapter"))
    implementation(libs.constraintlayout)
}
