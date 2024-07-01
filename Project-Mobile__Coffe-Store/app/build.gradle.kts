plugins {
    id("com.android.application")
}

android {
    namespace = "com.nlu.packages"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nlu.packages"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("androidx.activity:activity:1.8.0")

    implementation("jp.wasabeef:picasso-transformations:2.4.0")
    // If you want to use the GPU Filters
    implementation("jp.co.cyberagent.android:gpuimage:2.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.picasso:picasso:2.8")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.google.android.material:material:1.5.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.10.1")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.10.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.1")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
    
        implementation("androidx.room:room-rxjava2:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
}