package com.doug.cashflow.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.doug.cashflow.BuildConfig;
import com.doug.cashflow.R;
import com.doug.cashflow.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, MockContext.class, LayoutInflater.class, View.class, TextView.class,
        Bundle.class, AppCompatActivity.class, DatePickerFragment.class})
@Config(constants = BuildConfig.class)
public class DatePickerFragmentUnitTest {
    private FragmentManager fragmentManager;
    private Context mockContext = mock(MockContext.class);
    private LayoutInflater mockInflater = mock(LayoutInflater.class);
    private LayoutInflater mockActivityInflater = mock(LayoutInflater.class);
    private View mockErrorView = mock(View.class);
    private ViewGroup mockParent = mock(ViewGroup.class);
    private AppCompatActivity mockActivity = mock(AppCompatActivity.class);
    private String mockSerializable = mock(String.class);
    private DatePickerFragment mockDatePickerFragment = mock(DatePickerFragment.class);

    private final TestUtil testUtil = new TestUtil();

    // SUT
    private DatePickerFragment datePickerFragment;

    @Before
    public void setUp() throws InterruptedException {
        initMocks(this);

        mockStatic(LoginLab.class);
        mockStatic(MockContext.class);
        mockStatic(LayoutInflater.class);
        mockStatic(View.class);
        mockStatic(ViewGroup.class);
        mockStatic(Bundle.class);
        mockStatic(AppCompatActivity.class);
        mockStatic(DatePickerFragment.class);

        when (LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when (LayoutInflater.from(mockActivity)).thenReturn(mockActivityInflater);
        when (mockInflater.inflate(R.layout.error_string, null)).thenReturn(mockErrorView);
        when (mockErrorView.getId()).thenReturn(1);

        fragmentManager = mockActivity.getSupportFragmentManager();
        when (mockActivity.getSupportFragmentManager()).thenReturn(fragmentManager);

        when (LoginLab.getCurrentActivity()).thenReturn(mockActivity);
        when (DatePickerFragment.newInstance((Date) any(), anyString())).thenReturn(mockDatePickerFragment);

        datePickerFragment = new DatePickerFragment();
    }

    @Test
    public void preCondition() throws InterruptedException {
        assertNotNull(datePickerFragment);
    }

    @Test
    public void verifyNewInstance() throws InterruptedException {
        Date date = new Date();
        String title = "test";
        DatePickerFragment newDatePickerFragment = datePickerFragment.newInstance(date, title);

        assertNotNull(newDatePickerFragment);
    }
}
