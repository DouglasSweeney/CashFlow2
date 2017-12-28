package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.doug.cashflow.BuildConfig;
import com.doug.cashflow.R;
import com.doug.cashflow.TestRoboActivity;
import com.doug.cashflow.controller.LoginLab;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, Context.class, MockContext.class, LayoutInflater.class, View.class, TextView.class, CheckBox.class})
@Config(constants = BuildConfig.class)
public class UtilUnitTest {
    private Util util;

    private Context mockContext = mock(MockContext.class);
    private LayoutInflater mockInflater = mock(LayoutInflater.class);

    private ViewGroup mockParent = mock(ViewGroup.class);

    private View mockView = mock(View.class);

    private TextView mockUtilErrorView = mock(TextView.class);

    private TestRoboActivity activity = new TestRoboActivity();

    @Before
    public void setUp() throws Exception {

        initMocks(this);

        mockStatic(LayoutInflater.class);

        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        when(mockView.findViewById(R.layout.error_string)).thenReturn(mockUtilErrorView);

        when(mockUtilErrorView.getText()).thenReturn("0.0");

        LoginLab.getInstance(mockContext, true);
        LoginLab.setCurrentActivity(activity);

        util = new Util();
        util.setTesting();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkValidVerifyIntegerInput() throws Exception {
        boolean answer;

        answer = util.verifyIntegerInput("12", null, 0, 0, 0);
        assertThat(true, is(answer));
    }

    @Test
    public void checkInvalidVerifyIntegerInput() throws Exception {
        boolean answer;

        answer = util.verifyIntegerInput("zzz", null, 0, 0, 0);
        assertThat(false, is(answer));
    }

    @Test
    public void checkValidVerifyFloatInput() throws Exception {
        boolean answer;

        answer = util.verifyFloatInput("12.0", null, 0, 0, 0);
        assertThat(true, is(answer));
    }

    @Test
    public void checkInvalidVerifyFloatInput() throws Exception {
        boolean answer;

        answer = util.verifyFloatInput("zzz", null, 0, 0, 0);
        assertThat(false, is(answer));
    }

    @Test
    public void convertValidIntegerInput() throws Exception {
        Integer answer;

        answer = util.convertInteger("12");
        assertThat(12, is(answer));
    }

    @Test
    public void convertValidFloatInput() throws Exception {
        Float answer;

        answer = util.convertFloat("12.0");
        assertThat(12.0f, is(answer));
    }

    @Test
    public void convertTrueBooleanInput() throws Exception {
        boolean answer;

        answer = util.convertBoolean("true");
        assertThat(true, is(answer));
    }

    @Test
    public void convertFalseBooleanInput() throws Exception {
        boolean answer;

        answer = util.convertBoolean("false");
        assertThat(false, is(answer));
    }

    @Test
    public void convertFalseBooleanInput2() throws Exception {
        boolean answer;

        answer = util.convertBoolean("zzz");
        assertThat(false, is(answer));
    }

    @Test
    public void stripCommasValid() throws Exception {
        String answer;

        answer = util.stripCommas("1,200");
        assertThat("1200", is(answer));
    }
}
