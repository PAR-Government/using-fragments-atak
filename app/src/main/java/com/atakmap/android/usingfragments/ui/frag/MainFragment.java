package com.atakmap.android.usingfragments.ui.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.atakmap.android.ipc.AtakBroadcast;
import com.atakmap.android.usingfragments.UsingFragmentsDropDownReceiver;
import com.atakmap.android.usingfragments.plugin.R;

import org.jetbrains.annotations.NotNull;

public class MainFragment extends Fragment {

    Context pluginContext;

    /**
     * Factory method for the DropDownReceiver to "inject" the plugin
     * context.
     *
     * @param pluginContext
     * @return
     */
    public static MainFragment newInstance(Context pluginContext) {
        MainFragment frag = new MainFragment();
        frag.pluginContext = pluginContext;
        return frag;
    }

    /**
     * onAttach lifecycle method.
     * <p>
     * If you're using Dagger for DI, explicitly ask for member
     * injection here.
     *
     * @param context
     */
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        LayoutInflater pluginInflater = LayoutInflater.from(pluginContext);
        return pluginInflater.inflate(R.layout.main_layout, parent, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton ribbonSettingsButton = view.findViewById(R.id.ribbon_settings_button);
        ribbonSettingsButton.setOnClickListener(ignored -> {
            Intent i = new Intent(UsingFragmentsDropDownReceiver.SHOW_SETTINGS_FRAGMENT);
            AtakBroadcast.getInstance().sendBroadcast(i);
        });
    }

    /**
     * onStart lifecycle method.
     * <p>
     * If you are using RxJava, here (or onResume) are
     * good places to setup or connect to streams.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * If you are using RxJava here or onPause are
     * good places to dispose data streams that shouldn't
     * stay alive when the used navigates away from this
     * fragment.
     * <p>
     * "View binding" streams should be disposed in
     * onDestroyView.
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * onDestroyView lifecycle method.
     * <p>
     * If you are using RxJava, here is a good place
     * to dispose of streams used for view binding.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * The final step in the lifecycle.
     * <p>
     * After this, the Fragment is destroyed.
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
