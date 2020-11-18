
package com.atakmap.android.usingfragments;

import android.content.Context;
import android.content.Intent;

import com.atakmap.android.maps.MapView;
import com.atakmap.android.dropdown.DropDown.OnStateListener;
import com.atakmap.android.dropdown.DropDownReceiver;

import com.atakmap.android.usingfragments.ui.frag.ChoreographerFragment;
import com.atakmap.android.usingfragments.ui.frag.SettingsFragment;
import com.atakmap.coremap.log.Log;

import java.util.ArrayList;
import java.util.List;

public class UsingFragmentsDropDownReceiver extends DropDownReceiver implements
        OnStateListener {

    public static final String TAG = UsingFragmentsDropDownReceiver.class
            .getSimpleName();

    public static final String SHOW_PLUGIN = "com.atakmap.android.usingfragments.SHOW_PLUGIN";
    public static final String SHOW_SETTINGS_FRAGMENT = "com.atakmap.android.usingfragments.SHOW_SECOND_FRAGMENT";

    private final Context pluginContext;

    private final ChoreographerFragment choreographer;

    /**************************** CONSTRUCTOR *****************************/

    public UsingFragmentsDropDownReceiver(final MapView mapView,
            final Context context) {
        super(mapView);
        this.pluginContext = context;
        this.choreographer = ChoreographerFragment.newInstance(pluginContext);
    }

    /**************************** PUBLIC METHODS *****************************/

    public void disposeImpl() {
    }

    /**************************** INHERITED METHODS *****************************/

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        if (action == null)
            return;

        switch (action) {
            case SHOW_PLUGIN:
                Log.d(TAG, "showing plugin drop down");
                showDropDown(
                        choreographer,
                        HALF_WIDTH, FULL_HEIGHT,
                        FULL_WIDTH, HALF_HEIGHT,
                        false,
                        false
                );
                break;
            case SHOW_SETTINGS_FRAGMENT:
                if (choreographer == null) {
                    return;
                }
                SettingsFragment second = SettingsFragment.Factory.newInstance(pluginContext);
                choreographer.showFragment(second, null);
                break;
        }
    }

    @Override
    public void onDropDownSelectionRemoved() {
    }

    @Override
    public void onDropDownVisible(boolean v) {
    }

    @Override
    public void onDropDownSizeChanged(double width, double height) {
    }

    @Override
    public void onDropDownClose() {
    }

    @Override
    protected boolean onBackButtonPressed() {
        return choreographer.onBackButtonPressed();
    }

    List<String> getAllActions() {
        List<String> actions = new ArrayList<>();
        actions.add(SHOW_PLUGIN);
        actions.add(SHOW_SETTINGS_FRAGMENT);
        return actions;
    }
}
