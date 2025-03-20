package com.walhalla.pillfinder.activity;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.walhalla.pillfinder.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HealthMainActivityTest {

    @Rule
    public ActivityScenarioRule<HealthMainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(HealthMainActivity.class);

    @Test
    public void healthMainActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.myList),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("��"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.databinding.library.baseAdapters.R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_color), withText("����"),
                        childAtPosition(
                                allOf(withId(R.id.top_menu),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction squareToggleButton = onView(
                allOf(withId(R.id.ib6), withContentDescription("Paint"),
                        childAtPosition(
                                allOf(withId(R.id.line2),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        squareToggleButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.yes11), withText("��"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btn_shape), withText("�����"),
                        childAtPosition(
                                allOf(withId(R.id.top_menu),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                1)),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withId(androidx.databinding.library.baseAdapters.R.id.select_dialog_listview),
                        childAtPosition(
                                withId(androidx.databinding.library.baseAdapters.R.id.contentPanel),
                                0)))
                .atPosition(5);
        appCompatCheckedTextView.perform(click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.myList),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("Pill Identifier"),
                        childAtPosition(
                                allOf(withId(R.id.main_content),
                                        childAtPosition(
                                                withId(R.id.container),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());
    }

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
}
