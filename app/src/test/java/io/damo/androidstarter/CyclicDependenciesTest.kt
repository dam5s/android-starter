package io.damo.androidstarter

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import org.junit.Test

class CyclicDependenciesTest {

    @Test
    fun test() {
        val basePackage = StarterApp::class.java.`package`!!.name
        val importedClasses = ClassFileImporter().importPackages(basePackage)

        SlicesRuleDefinition.slices()
            .matching("$basePackage.(*)..")
            .should().beFreeOfCycles()
            .check(importedClasses)
    }
}
