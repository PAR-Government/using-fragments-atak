package com.atakmap.android.usingfragments.ui

interface IBackButtonHandler {
    /**
     * For the drop down receiver to delegate handling the
     * back button. Return true if handled, false if not.
     *
     * As a side effect, returning false will signal the
     * drop down manager to close the drop down.
     */
    fun onBackButtonPressed(): Boolean {
        return false
    }
}