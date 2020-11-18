package com.atakmap.android.usingfragments.ui.frag;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.atakmap.android.usingfragments.plugin.R;
import com.atakmap.android.usingfragments.ui.IBackButtonHandler;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Stack;

public class ChoreographerFragment extends Fragment implements IBackButtonHandler {
    private static final String LOG_TAG = "ChoreographerFragment";

    private ViewGroup contentContainer;

    private final Stack<Fragment> backStack = new Stack<>();
    private Fragment currentlyVisible = null;

    Context pluginContext;

    private ChoreographerFragment() {
        super();
    }

    /**
     * Factory method for the DropDownReceiver to "inject" the plugin
     * context.
     * @param pluginContext
     * @return
     */
    public static ChoreographerFragment newInstance(Context pluginContext) {
        ChoreographerFragment frag = new ChoreographerFragment();
        frag.pluginContext = pluginContext;
        return frag;
    }

    /**
     * onAttach lifecycle method.
     *
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
        return pluginInflater.inflate(R.layout.choreographer, parent, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentContainer = view.findViewById(R.id.content_container);
    }

    /**
     * onStart lifecycle method.
     *
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
        Fragment frag;
        if (currentlyVisible != null) {
            frag = currentlyVisible;
        } else {
            // show the main layout
            frag = MainFragment.newInstance(pluginContext);
        }

        showFragment(frag, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        backStack.clear();
        currentlyVisible = null;
    }

    /**
     * If you are using RxJava here or onPause are
     * good places to dispose data streams that shouldn't
     * stay alive when the used navigates away from this
     * fragment.
     *
     * "View binding" streams should be disposed in
     * onDestroyView.
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * onDestroyView lifecycle method.
     *
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
     *
     * After this, the Fragment is destroyed.
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
    
    @Override
    public boolean onBackButtonPressed() {
        if (currentlyVisible instanceof IBackButtonHandler) {
            boolean handled = ((IBackButtonHandler) currentlyVisible).onBackButtonPressed();
            // if the child fragment handled it on their own,
            // then they must not want the choreographer to handle it
            if (handled) {
                return true;
            }
        }

        Fragment previous = popBackStack();

        // if there's nowhere to go, signal to close the drop down
        if (previous == null) {
            currentlyVisible = null;
            return false;
        }

        showFragmentActual(previous);

        return true;
    }

    /**
     * Instantiate and show the Fragment of the given type.
     *
     * @param type The fragment's type. Used to instantiate through reflection.
     * @param args Arguments to be passed to the fragment.
     */
    public void showFragment(Class<?> type, Bundle args) {
        Type superType = type.getGenericSuperclass();
        if (superType != null && !superType.equals(Fragment.class)) {
            Log.w(LOG_TAG, "showFragment called with a non-Fragment type: " + type.getName());
            return;
        }

        Fragment frag;
        try {
            frag = (Fragment) type.newInstance();
        } catch (java.lang.InstantiationException ie) {
            Log.e(LOG_TAG, "Could not instantiate " + type.getName(), ie);
            return;
        } catch (IllegalAccessException iae) {
            Log.e(LOG_TAG, "Cannot access constructor for type " + type.getName(), iae);
            return;
        } catch (ClassCastException cce) {
            Log.e(LOG_TAG, "Cannot cast " + type.getName() + " to a Fragment", cce);
            return;
        }

        showFragment(frag, args);
    }

    public void showFragment(@NonNull Fragment fragment, @Nullable Bundle additionalArgs) {
        if (additionalArgs != null) {
            Bundle args = fragment.getArguments();
            if (args == null) {
                args = new Bundle();
            }
            args.putAll(additionalArgs);
            fragment.setArguments(args);
        }

        if (currentlyVisible != null) {
            pushBackStack(currentlyVisible);
        }

        showFragmentActual(fragment);
    }

    /**
     * Actually show the given fragment by using this Fragment's
     * FragmentManager.
     * @param fragment The fragment to be shown next
     */
    private void showFragmentActual(Fragment fragment) {
        FragmentManager mgr = getChildFragmentManager();
        FragmentTransaction tx = mgr.beginTransaction();
        tx.replace(contentContainer.getId(), fragment);
        tx.commit();
        currentlyVisible = fragment;
    }

    /**
     * Push the given fragment on to our custom back stack.
     * @param fragment The fragment to be cached for back navigation
     */
    private void pushBackStack(@NonNull Fragment fragment) {
        backStack.push(fragment);
    }

    /**
     * Pop the previous fragment off of the back stack.
     * @return The fragment to be shown for back navigation,
     *          or null if there are no more fragments on the stack.
     */
    @Nullable
    private Fragment popBackStack() {
        return !backStack.isEmpty() ? backStack.pop() : null;
    }
}
