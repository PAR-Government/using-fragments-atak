package com.atakmap.android.usingfragments.ui.frag

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atakmap.android.usingfragments.plugin.R

class SettingsFragment private constructor() : Fragment() {

    private lateinit var pluginContext: Context

    /**
     * onAttach lifecycle method.
     *
     * If you're using Dagger for DI, explicitly ask for member
     * injection here.
     *
     * @param context The context this Fragment is attached to.
     * I've never checked, but suspect this will be
     * the ATAK Context.
     */
    override fun onAttach(context: Context) = super.onAttach(context)

    override fun onCreate(savedInstanceState: Bundle?) = super.onCreate(savedInstanceState)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return LayoutInflater
            .from(pluginContext)
            .inflate(R.layout.settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        super.onViewCreated(view, savedInstanceState)

    /**
     * onStart lifecycle method.
     *
     * If you are using RxJava, here or onResume() are
     * good places to setup and connect to streams.
     */
    override fun onStart() = super.onStart()

    /**
     * onResume lifecycle method.
     *
     * Here is a good place to show either the plugin's main
     * layout if it's just being opened, or the most recently
     * visible fragment if it's this is being brought back to
     * the screen after brief navigation away from the drop down.
     */
    override fun onResume() = super.onResume()

    override fun onPause() = super.onPause()

    /**
     * If you are using RxJava here or onPause() are
     * good places to dispose data streams that shouldn't
     * stay alive when the user navigates away from this
     * Fragment.
     *
     * "View binding" streams should be disposed in
     * onDestroyView.
     */
    override fun onStop() = super.onStop()

    /**
     * onDestroyView lifecycle method.
     *
     * If you are using RxJava, here is a good place
     * to dispose of streams used for view binding.
     */
    override fun onDestroyView() = super.onDestroyView()

    override fun onDestroy() = super.onDestroy()

    /**
     * The final step in the lifecycle.
     *
     * After this, the Fragment is destroyed.
     */
    override fun onDetach() = super.onDetach()

    companion object Factory {
        /**
         * Factory method for the DropDownReceiver to "inject" the plugin
         * context.
         *
         * If Dagger is employed for DI, this isn't necessary.
         *
         * @param pluginContext The plugin Context
         * @return a new instance of this Fragment
         */
        fun newInstance(pluginContext: Context) = SettingsFragment().apply {
            this.pluginContext = pluginContext
        }
    }
}