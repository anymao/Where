package com.anymore.where.gradle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.hasPlugin

/**
 * Created by anymore on 2021/4/23.
 */
class WherePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val logger = Logger(target)
        val isApp = target.plugins.hasPlugin(AppPlugin::class)
        target.extensions.create("where", WhereExtension::class.java)
        if (isApp) {
            val android = target.extensions.getByType(AppExtension::class)
            val enabled = target.extensions.getByType(WhereExtension::class).enabled
            if (enabled) {
                target.dependencies {
                    add("debugImplementation", "com.github.anymao:where:master-SNAPSHOT")
                }
                android.registerTransform(WhereTransform(target, logger))
            } else {
                logger.tell("the where plugin is disabled,skip registerTransform")
            }
        }
    }
}