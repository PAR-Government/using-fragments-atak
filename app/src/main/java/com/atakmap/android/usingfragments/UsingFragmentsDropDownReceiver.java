
package com.atakmap.android.usingfragments;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.atak.plugins.impl.PluginLayoutInflater;
import com.atakmap.android.maps.MapView;
import com.atakmap.android.usingfragments.plugin.R;
import com.atakmap.android.dropdown.DropDown.OnStateListener;
import com.atakmap.android.dropdown.DropDownReceiver;

import com.atakmap.android.usingfragments.ui.frag.ChoreographerFragment;
import com.atakmap.coremap.log.Log;

public class UsingFragmentsDropDownReceiver extends DropDownReceiver implements
        OnStateListener {

    public static final String TAG = UsingFragmentsDropDownReceiver.class
            .getSimpleName();

    public static final String SHOW_PLUGIN = "com.atakmap.android.usingfragments.SHOW_PLUGIN";
    private final Context pluginContext;

    private final ChoreographerFragment rootFragment;

    /**************************** CONSTRUCTOR *****************************/

    public UsingFragmentsDropDownReceiver(final MapView mapView,
            final Context context) {
        super(mapView);
        this.pluginContext = context;
        this.rootFragment = ChoreographerFragment.newInstance(pluginContext);
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

        if (action.equals(SHOW_PLUGIN)) {

            Log.d(TAG, "showing plugin drop down");
            showDropDown(
                    rootFragment,
                    HALF_WIDTH, FULL_HEIGHT,
                    FULL_WIDTH, HALF_HEIGHT,
                    false,
                    false
            );
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
        return rootFragment.onBackButtonPressed();
    }
}
