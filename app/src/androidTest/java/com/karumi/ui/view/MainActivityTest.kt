package com.karumi.ui.view

import com.github.salomonbrys.kodein.Kodein.Module
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.karumi.data.repository.SuperHeroRepository
import com.karumi.domain.model.SuperHero
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.mockito.Mock

class MainActivityTest : AcceptanceTest<MainActivity>(MainActivity::class.java) {

    @Mock
    private lateinit var repository: SuperHeroRepository

    @Test
    fun showsEmptyCaseIfThereAreNoSuperHeroes() {
        givenThereAreNoSuperHeroes()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsSuperHeroesIfThereAre() {
        givenThereAreSomeSuperHeroes(10)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showOnlyOneSuperHeroe() {
        givenThereAreSomeSuperHeroes(1)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsEmptySuperHeroeName() {
        givenThereAreSomeSuperHeroes()

        val activity = startActivity()

        compareScreenshot(activity)
    }

    @Test
    fun showsAvengersIfThereAre() {
        givenThereAreSomeAvengers(10)

        val activity = startActivity()

        compareScreenshot(activity)
    }

    private fun givenThereAreSomeSuperHeroes(
            numberOfSuperHeroes: Int = 1,
            avengers: Boolean = false
    ): List<SuperHero> {
        val superHeroes = IntRange(0, numberOfSuperHeroes - 1).map { id ->
            val superHeroName = "SuperHero - $id"
            val superHeroDescription = "Description Super Hero - $id"
            SuperHero(
                    superHeroName, null, avengers,
                    superHeroDescription
            )
        }

        whenever(repository.getAllSuperHeroes()).thenReturn(superHeroes)
        return superHeroes
    }

    private fun givenThereAreNoSuperHeroes() {
        whenever(repository.getAllSuperHeroes()).thenReturn(emptyList())
    }

    private fun givenThereAreSomeAvengers(numberOfAvengers: Int): List<SuperHero> =
            givenThereAreSomeSuperHeroes(numberOfAvengers, avengers = true)

    override val testDependencies = Module(allowSilentOverride = true) {
        bind<SuperHeroRepository>() with instance(repository)
    }
}