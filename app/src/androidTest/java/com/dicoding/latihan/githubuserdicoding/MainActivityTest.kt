package com.dicoding.latihan.githubuserdicoding

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    private val dummyName = "Tom"
    private val dummyText = "ikky"

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingTestResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingTestResource)
    }

    @Test
    fun loadSearchAndDetailWithFavorite() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
        onView(withId(R.id.edQuery)).perform(typeText(dummyText), closeSoftKeyboard())
        onView(withId(R.id.btn_search)).perform(click())
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.tvName)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(withText(dummyName)))
        onView(withId(R.id.tvFollowers)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_fav)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))
        onView(withId(R.id.fav_button)).perform(click())
        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_fav)).perform(click())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadUserMain() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))
        onView(withId(R.id.edQuery)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_main)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(30))
    }

    @Test
    fun settingDarkTheme() {
        onView(withId(R.id.rv_main)).check(matches(isDisplayed()))
        onView(withId(R.id.setting_button)).perform(click())
        onView(withId(R.id.switch_dark)).perform(click())
        onView(isRoot()).perform(pressBack())
    }

}