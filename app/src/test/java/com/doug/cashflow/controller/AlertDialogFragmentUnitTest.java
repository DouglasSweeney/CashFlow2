package com.doug.cashflow.controller;

import android.content.Context;
import android.content.res.Resources;
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
import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, MockContext.class, LayoutInflater.class, View.class, TextView.class,
        CheckBox.class, AppCompatActivity.class, AlertDialogFragment.class})
@Config(constants = BuildConfig.class)
public class AlertDialogFragmentUnitTest {
    private FragmentManager fragmentManager;
    private Context mockContext = mock(MockContext.class);
    private LayoutInflater mockInflater = mock(LayoutInflater.class);
    private LayoutInflater mockActivityInflater = mock(LayoutInflater.class);
    private View mockErrorView = mock(View.class);
    private ViewGroup mockParent = mock(ViewGroup.class);
    private AppCompatActivity mockActivity = mock(AppCompatActivity.class);
    private AlertDialogFragment mockAlertDialogFragment = mock(AlertDialogFragment.class);

    private final TestUtil testUtil = new TestUtil();

    // SUT
    private AlertDialogFragment alertDialogFragment;

    @Before
    public void setUp() throws InterruptedException {
        initMocks(this);

        mockStatic(LoginLab.class);
        mockStatic(MockContext.class);
        mockStatic(LayoutInflater.class);
        mockStatic(View.class);
        mockStatic(ViewGroup.class);
        mockStatic(AppCompatActivity.class);
        mockStatic(AlertDialogFragment.class);

        when (LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when (LayoutInflater.from(mockActivity)).thenReturn(mockActivityInflater);
        when (mockInflater.inflate(R.layout.error_string, null)).thenReturn(mockErrorView);
        when (mockErrorView.getId()).thenReturn(1);

        fragmentManager = mockActivity.getSupportFragmentManager();
        when (mockActivity.getSupportFragmentManager()).thenReturn(fragmentManager);

        when (LoginLab.getCurrentActivity()).thenReturn(mockActivity);
        when (AlertDialogFragment.newInstance(anyInt(), anyInt(), anyInt())).thenReturn(mockAlertDialogFragment);

        alertDialogFragment = new AlertDialogFragment();
    }

    @Test
    public void preCondition() throws InterruptedException {
        assertNotNull(alertDialogFragment);
    }

    @Test
    public void verifyNewInstance() throws InterruptedException {
        Integer category = 0;
        Integer field = 1;
        Integer extra = 2;
        AlertDialogFragment newAlertDialogFragment = alertDialogFragment.newInstance(category, field, extra);

        assertNotNull(newAlertDialogFragment);
    }
}