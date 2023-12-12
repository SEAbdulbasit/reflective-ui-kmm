plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

//val copyJsResources = tasks.create("copyJsResourcesWorkaround", Copy::class.java) {
//    from(project(":shared").file("src/commonMain/resources"))
//    into("build/processedResources/js/main")
//}
//
//val copyWasmResources = tasks.create("copyWasmResourcesWorkaround", Copy::class.java) {
//    from(project(":shared").file("src/commonMain/resources"))
//    into("build/processedResources/wasmJs/main")
//}

//afterEvaluate {
//    project.tasks.getByName("jsProcessResources").finalizedBy(copyJsResources)
//    project.tasks.getByName("wasmJsProcessResources").finalizedBy(copyWasmResources)
//
//    project.tasks.getByName("jsBrowserProductionExecutableDistributeResources").mustRunAfter(copyJsResources)
//    project.tasks.getByName("jsDevelopmentExecutableCompileSync").mustRunAfter(copyJsResources)
//    //project.tasks.getByName("wasmJsDevelopmentExecutableCompileSync").mustRunAfter(copyWasmResources)
//    project.tasks.getByName("jsProductionExecutableCompileSync").mustRunAfter(copyJsResources)
//    project.tasks.getByName("wasmJsProductionExecutableCompileSync").mustRunAfter(copyWasmResources)
//    //project.tasks.getByName("wasmDevelopmentExecutableCompileSync").mustRunAfter(copyWasmResources)
//}


kotlin {
    js(IR) {
        moduleName = "recipeapp"
        browser {
            commonWebpackConfig {
                outputFileName = "recipeapp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsWasmMain by creating {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val jsMain by getting {
            dependsOn(jsWasmMain)
        }
    }
}

compose.experimental {
    web.application {}
}
