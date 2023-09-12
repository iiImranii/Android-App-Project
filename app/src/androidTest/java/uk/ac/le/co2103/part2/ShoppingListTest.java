package uk.ac.le.co2103.part2;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import android.view.View;

import junit.framework.AssertionFailedError;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ShoppingListTest {
    @BeforeClass
    public static void clearDB(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("shoppinglist_db");
    }




    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddNewList() {

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.finalizeButton)).check(matches(isDisplayed()));
        onView(withId(R.id.shoppingListName)).perform(typeText("Birthday Party"),closeSoftKeyboard());
        onView(withId(R.id.finalizeButton)).perform(click());
        onView(withText("Birthday Party")).check(matches(isDisplayed()));

    }



    @Test
    public void testDeleteList() {


        try {
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withId(R.id.fab)).perform(click());
            onView(withId(R.id.finalizeButton)).check(matches(isDisplayed()));
            onView(withId(R.id.shoppingListName)).perform(typeText("Birthday Party"),closeSoftKeyboard());
            onView(withId(R.id.finalizeButton)).perform(click());
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        }

        onView(withText("Birthday Party")).perform(longClick());
        onView(withText("Yes, please")).check(matches(isDisplayed())).perform(click());
        onView(withText("Birthday Party")).check(doesNotExist());
    }

    @Test
    public void testAddProduct() {
        try {
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withId(R.id.fab)).perform(click());
            onView(withId(R.id.finalizeButton)).check(matches(isDisplayed()));
            onView(withId(R.id.shoppingListName)).perform(typeText("Birthday Party"),closeSoftKeyboard());
            onView(withId(R.id.finalizeButton)).perform(click());
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        }
        onView(withText("Birthday Party")).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.fabAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextName)).perform(typeText("Cake"),closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"),closeSoftKeyboard());

        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Unit"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Unit"))));
        onView(withId(R.id.finishAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.finishAddProduct)).perform(click());
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        onView(withText("Birthday Party")).perform(click());
        onView(withId(R.id.productsText)).check(matches(isDisplayed()));
        onView(withText("Cake")).check(matches(isDisplayed()));
    }
    @Test
    public void testAddDuplicateProduct() {

        try {
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withId(R.id.fab)).perform(click());
            onView(withId(R.id.finalizeButton)).check(matches(isDisplayed()));
            onView(withId(R.id.shoppingListName)).perform(typeText("Birthday Party"),closeSoftKeyboard());
            onView(withId(R.id.finalizeButton)).perform(click());
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        }
        onView(withText("Birthday Party")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fabAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextName)).perform(typeText("Cake"),closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"),closeSoftKeyboard());

        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Unit"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Unit"))));
        onView(withId(R.id.finishAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.finishAddProduct)).perform(click());
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        onView(withText("Birthday Party")).perform(click());
        onView(withId(R.id.productsText)).check(matches(isDisplayed()));

        onView(withId(R.id.fabAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextName)).perform(typeText("Cake"),closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"),closeSoftKeyboard());

        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Unit"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Unit"))));
        onView(withId(R.id.finishAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.finishAddProduct)).perform(click());
        onView(withText("Product already exists")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
        //onView(withText("Product already exists")).check(matches(isDisplayed()));


    }

    @Test
    public void testEditProduct() {

        try {
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withId(R.id.fab)).perform(click());
            onView(withId(R.id.finalizeButton)).check(matches(isDisplayed()));
            onView(withId(R.id.shoppingListName)).perform(typeText("Birthday Party"),closeSoftKeyboard());
            onView(withId(R.id.finalizeButton)).perform(click());
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        }
        onView(withText("Birthday Party")).perform(click());
        onView(withId(R.id.fabAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextName)).perform(typeText("Cake"),closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"),closeSoftKeyboard());

        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Unit"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Unit"))));
        onView(withId(R.id.finishAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.finishAddProduct)).perform(click());
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        onView(withText("Birthday Party")).perform(click());
        onView(withId(R.id.productsText)).check(matches(isDisplayed()));
        onView(withText("Cake")).check(matches(isDisplayed()));
        onView(withText("Cake")).perform(click());
        onView(withText("EDIT")).check(matches(isDisplayed()));
        onView(withText("EDIT")).perform(click());


        onView(withId(R.id.incQuantityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.incQuantityButton)).perform(click());
        onView(withId(R.id.incQuantityButton)).check(matches(isDisplayed()));
        onView(withId(R.id.incQuantityButton)).perform(click());

        onView(withId(R.id.finishUpdatedProductButton)).check(matches(isDisplayed()));
        onView(withId(R.id.finishUpdatedProductButton)).perform(click());




    }

    @Test
    public void testDeleteProduct() {
        try {
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withId(R.id.fab)).perform(click());
            onView(withId(R.id.finalizeButton)).check(matches(isDisplayed()));
            onView(withId(R.id.shoppingListName)).perform(typeText("Birthday Party"),closeSoftKeyboard());
            onView(withId(R.id.finalizeButton)).perform(click());
            onView(withText("Birthday Party")).check(matches(isDisplayed()));
        }
        onView(withText("Birthday Party")).perform(click());
        onView(withId(R.id.fabAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextName)).perform(typeText("Cake"),closeSoftKeyboard());
        onView(withId(R.id.editTextQuantity)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"),closeSoftKeyboard());

        onView(withId(R.id.spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Unit"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Unit"))));
        onView(withId(R.id.finishAddProduct)).check(matches(isDisplayed()));
        onView(withId(R.id.finishAddProduct)).perform(click());
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        onView(withText("Birthday Party")).perform(click());
        onView(withId(R.id.productsText)).check(matches(isDisplayed()));
        onView(withText("Cake")).check(matches(isDisplayed()));
        onView(withText("Cake")).perform(click());
        onView(withText("DELETE")).check(matches(isDisplayed()));
        onView(withText("DELETE")).perform(click());

       onView(withText("Item deleted")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
        //onView(withText("Cake")).check(doesNotExist());

    }
}