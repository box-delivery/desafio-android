package com.jlmcdeveloper.githubjavapop.ui.main


import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.jlmcdeveloper.githubjavapop.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class PullRequestTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pullRequestTest() {

        val rV1 =
            mActivityTestRule.activity.findViewById(R.id.recyclerView) as RecyclerView

        val nameRepository = (rV1.findViewHolderForAdapterPosition(0)!!.itemView
            .findViewById(R.id.textNameRepository) as TextView).text


        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(700)

        val textView = onView(
            allOf(
                withText("$nameRepository"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar_pullRequest),
                        childAtPosition(
                            withId(R.id.appBar_pullRequest),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("$nameRepository")))

        val textView2 = onView(
            allOf(
                withText("${getRVcount()} opened "),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayoutPR),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("${getRVcount()} opened ")))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar_pullRequest),
                        childAtPosition(
                            withId(R.id.appBar_pullRequest),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }


    private fun getRVcount(): Int {
        return  getCurrentActivity()?.let {
            return (it.findViewById(R.id.recyclerView_pullRequest) as RecyclerView).adapter!!.itemCount
        }?: -1
    }

    private fun getCurrentActivity(): Activity? {
        val currentActivity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync(Runnable {
            val allActivities = ActivityLifecycleMonitorRegistry.getInstance()
                .getActivitiesInStage(Stage.RESUMED)
            if (!allActivities.isEmpty()) {
                currentActivity[0] = allActivities.iterator().next()
            }
        })
        return currentActivity[0]
    }
}
