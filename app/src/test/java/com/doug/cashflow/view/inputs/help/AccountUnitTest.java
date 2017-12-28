package com.doug.cashflow.view.inputs.help;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
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

//import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, Context.class, MockContext.class, AppCompatActivity.class, Resources.class, LayoutInflater.class, View.class,
                 TextView.class, CheckBox.class})
@Config(constants = BuildConfig.class)
public class AccountUnitTest {
    private static final Double EPSILON = 0.00001;

    private Account account;

    private Context    mockContext = mock(MockContext.class);
    private AppCompatActivity mockActivity = mock(AppCompatActivity.class);
    private Resources  mockResources = mock(Resources.class);
    private LayoutInflater mockInflater = mock(LayoutInflater.class);

    private View mockView = mock(View.class);

    private ViewGroup mockParent = mock(ViewGroup.class);

    private TextView mockTitleView = mock(TextView.class);
    private TextView mockTextView = mock(TextView.class);

    private TestRoboActivity activity = new TestRoboActivity();

    @Before
    public void setUp() throws Exception {

        initMocks(this);

        mockStatic(LayoutInflater.class);
        mockStatic(View.class);
        mockStatic(Context.class);
        mockStatic(MockContext.class);
        mockStatic(AppCompatActivity.class);
        mockStatic(Resources.class);
        mockStatic(ViewGroup.class);
        mockStatic(LoginLab.class);
        mockStatic(TextView.class);
        mockStatic(CheckBox.class);

        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), (ViewGroup)isNull())).thenReturn(mockView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockTitleView);
        when(mockView.findViewById(R.id.text_area)).thenReturn(mockTextView);

        when(LoginLab.getRealData(anyInt(), anyString(), anyString())).thenReturn("2.0");
        when(LoginLab.getIntegerData(anyInt(), anyString(), anyString())).thenReturn("2");
        when(LoginLab.getDate(anyInt(), anyInt())).thenReturn(new Date());
        when(LoginLab.getRealData(anyInt(), anyString(), anyString())).thenReturn("2.0");
        when(LoginLab.getCurrentActivity()).thenReturn(mockActivity);
        when(mockActivity.getResources()).thenReturn(mockResources);

        String title = "Title";
        String text = "Text";

        when(mockContext.getString(R.id.title)).thenReturn(title.toString());
        when(mockContext.getString(R.id.text_area)).thenReturn(text.toString());

        when(mockTitleView.getText()).thenReturn(title.toString());
        when(mockTextView.getText()).thenReturn(text.toString());

        LoginLab.getInstance(mockContext, true);
        LoginLab.setCurrentActivity(activity);

        account = new Account(mockContext, R.id.title, R.id.text_area);
    }

    @After
    public void tearDown() throws Exception {
        mockTitleView.setText("");
        mockTextView.setText("");
    }

    @Test
    public void preCondition() throws Exception {

        assertNotNull(account);
    }

    @Test
    public void checkTitleText() throws Exception {

        assertThat("Title", is(account.getTitleText()));
    }

    @Test
    public void checkTextText() throws Exception {

        assertThat("Text", is(account.getTextText()));
    }

    @Test
    public void checkTitleView() throws Exception {
        account.createView(mockContext, account.getTitleText());

        assertThat("Title", is(account.getTitleView().getText()));
    }

    @Test
    public void checkTextView() throws Exception {
        account.createView(mockContext, account.getTextText());

        assertThat("Text", is(account.getTextView().getText()));
    }

    @Test
    public void getView() throws Exception {

        View view = account.createView(mockContext, account.getTextText());

        assertNotNull(view);
    }
}
