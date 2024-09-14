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
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
    kotlinOptions {
        jvmTarget = "19"
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
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(libs.bundles.base)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
//            from(components["kotlin"]) // 根据你项目的实际组件进行替换
            groupId = "com.github.xiaoxiaoying"
            artifactId = "Page-RecyclerView-ArrayAdpter" // 替换为你的 artifact ID
            version = "1.5.6"
        }
    }
    repositories {
        mavenLocal() // 发布到本地 Maven 仓库
    }
}