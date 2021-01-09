package com.test.movieslist.ui.movies

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.test.movieslist.R
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesActivityTest : TestCase(){

    private var mActivity: MoviesActivity? = null
    private var mRecyclerView: RecyclerView? = null
    private var itemCount = 0

    @Rule
    var activityActivityTestRule: ActivityTestRule<MoviesActivity> =
        ActivityTestRule(MoviesActivity::class.java)

    /**
     * the Activity of the Target application
     */
    @Before
    @Throws(Exception::class)
    override fun setUp() {
        mActivity = activityActivityTestRule.getActivity()
    }

    @Test
    fun movieListRecyclerViewTest() {

        Assert.assertNotNull(mRecyclerView)
        if (itemCount > 0) {
            for (i in 0 until itemCount) {

                /* checking for the text of the first one item */
                if (i == 0) {
                    Espresso.onView(
                        RecyclerViewMatcher(R.id.moviesRecyclerView)
                            .atPositionOnView(i, R.id.movietitle)
                    )
                        .check(ViewAssertions.matches(ViewMatchers.withText("The Long Tail")))
                }
            }
        }
    }

    @Test
    fun checkTextDisplayTest() {

        if (itemCount > 0) {
            for (i in 0 until itemCount) {

                /* check if the ViewHolder is being displayed */
                Espresso.onView(
                    RecyclerViewMatcher(R.id.moviesRecyclerView)
                        .atPositionOnView(i, R.id.movietitle)
                )
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
        }
    }





}