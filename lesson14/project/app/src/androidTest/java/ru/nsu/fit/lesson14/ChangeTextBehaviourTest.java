package ru.nsu.fit.lesson14;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ChangeTextBehaviourTest {
    private static final String PACKAGE_NAME = "ru.nsu.fit.lesson14";

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final int UI_TIMEOUT = 500;

    private static final String TEST_STRING = "Hello World!!!";

    private UiDevice mDevice;

    @Before
    public void startActivity() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        //! Go Home
        mDevice.pressHome();

        //! Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        final Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        // Clear out any previous instances
        assert intent != null;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        //! Wait for app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void testChangeText_sameActivity() {
        // Type text and then press the button.
        mDevice.findObject(By.res(PACKAGE_NAME, "edit_text_user_input"))
                .setText(TEST_STRING);
        mDevice.findObject(By.res(PACKAGE_NAME, "change_text_button"))
                .click();

        // Verify the test is displayed in the Ui
        UiObject2 changedText = mDevice
                .wait(Until.findObject(By.res(PACKAGE_NAME, "text_to_be_changed")), UI_TIMEOUT );
        assertThat(changedText.getText(), is(equalTo(TEST_STRING)));
    }

    @Test
    public void testChangeText_newActivity() {
        // Type text and then press the button.
        mDevice.findObject(By.res(PACKAGE_NAME, "edit_text_user_input"))
                .setText(TEST_STRING);
        mDevice.findObject(By.res(PACKAGE_NAME, "activity_change_text_button"))
                .click();

        // Verify the test is displayed in the Ui
        UiObject2 changedText = mDevice
                .wait(Until.findObject(By.res(PACKAGE_NAME, "show_text_view")), UI_TIMEOUT);
        assertThat(changedText.getText(), is(equalTo(TEST_STRING)));
    }
}
