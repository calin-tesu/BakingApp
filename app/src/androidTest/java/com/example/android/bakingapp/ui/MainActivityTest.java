package com.example.android.bakingapp.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.android.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void mainActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_recipes),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.ingredients_header), withText("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.steps_rv),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.rv_recipes),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.ingredients_header), withText("Ingredients"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        pressBack();

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.steps_rv),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView4.perform(actionOnItemAtPosition(0, click()));
    }
}
