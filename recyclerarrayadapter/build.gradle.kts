plugins {
    alias(libs.plugins.androidLib)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("maven-publish")
}


android {
    namespace = "com.xiaoxiaoying.recyclerarrayadapter"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
//            versionCode = 154
//            versionName = "1.5.4"

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        abortOnError = false

    }
    buildFeatures {
        viewBinding = true
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
}

publishing {
    publications {
        create<MavenPublication>("maven") {
//            from(components["kotlin"]) // 根据你项目的实际组件进行替换
            groupId = "com.github.xiaoxiaoying"
            artifactId = "Page-RecyclerView-ArrayAdpter" // 替换为你的 artifact ID
            version = "1.5.9"
        }
    }

}