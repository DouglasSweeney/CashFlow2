package com.doug.cashflow.view.outputs;

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
import com.doug.cashflow.model.system.ResultsDataNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isNull;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, Context.class, MockContext.class, AppCompatActivity.class, Resources.class, LayoutInflater.class, View.class,
        TextView.class, CheckBox.class})
@Config(constants = BuildConfig.class)
public class SavingsUnitTest {
    private Savings savings;
    private final ArrayList<ResultsDataNode> arrayList = new ArrayList<>();

    private final Context    mockContext = mock(MockContext.class);
    private final LayoutInflater mockInflater = mock(LayoutInflater.class);

    private final View mockView = mock(View.class);

    @SuppressWarnings("unused")
    private ViewGroup mockParent = mock(ViewGroup.class);

    private final TextView mockTitleView = mock(TextView.class);
    private final TextView mockTextView = mock(TextView.class);

    private final static TestRoboActivity activity = new TestRoboActivity();

    private String title;

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

        title = "Title";
        when(mockContext.getString(R.string.output_header_savings)).thenReturn(title);

        LoginLab.getInstance(mockContext, true);
        LoginLab.setCurrentActivity(activity);

        ResultsDataNode values = new ResultsDataNode();
        values.aSet1(2017, 11, 59, 100.0, 105.0);
        arrayList.add(values);

        savings = new Savings(mockContext, arrayList);
    }

    @After
    public void tearDown() throws Exception {
        savings = null;
    }

    @Test
    public void preCondition() throws Exception {

        assertNotNull(savings);
    }

    @Test
    public void getName() throws Exception {

        assertThat(title, is(savings.getName()));
    }
}
