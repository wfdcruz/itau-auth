package com.itau.auth

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class LayerTest {
    @Test
    fun `layers have correct dependencies`() {
        Konsist.scopeFromProduction()
            .assertArchitecture {
                val adapter = Layer("Adapter", "com.itau.auth.adapter..")
                val application = Layer("Application", "com.itau.auth.application..")

                adapter.dependsOn(application)
                application.dependsOnNothing()
            }
    }

    @Test
    fun `classes with 'Controller' suffix should reside in 'adapter' 'in' 'controller' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("Controller")
            .assertTrue { it.resideInPackage("..adapter..in..controller..") }
    }

    @Test
    fun `classes with 'Dto' suffix should reside in 'dto' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("DTO")
            .assertTrue { it.resideInPackage("..dto..") }
    }

    @Test
    fun `classes with 'Exception' suffix should reside in 'exceptions' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("Exception")
            .assertTrue { it.resideInPackage("..exceptions..") }
    }

    @Test
    fun `classes with 'Config' suffix should reside in 'adapter' in 'config' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("Config")
            .assertTrue { it.resideInPackage("..config..") }
    }

    @Test
    fun `classes with 'Port' suffix should reside in 'application' in 'port' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("Port")
            .assertTrue { it.resideInPackage("..port..") }
    }
}