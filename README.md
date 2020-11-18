Fragments
=========
Below I summarize some thoughts/recommendations/opinions for working with [Fragments][Fragments] in an ATAK plugin, which are reflective of my [implementation][impl] in this example plugin:

* Manage navigating between different fragments with one main fragment that has a single view element: a `FrameLayout`, which is used to host other Fragments' views. _In this example I refer to this as the [ChoreographerFragment][Choreographer]_.

* When your plugin's `DropDownReceiver` receives the intent to actually launch your plugin, instantiate an instance (or use a previously initialized instance) of your `Choreographer` fragment and pass it to one of the (many) overloaded `DropDownReceiver::showDropDown(Fragment, ...)` implementations.

* Have your `Choreographer` fragment handle showing the _true_ main layout for your plugin. One decent place to do this is in the `ChroeographerFragment::onResume` lifecycle method. _Combining fragment puppeteering logic with your main layout view management in one fragment can  be done, but risks an unmaintainable mess._

* Use intents to trigger forward navigation to other fragments, and override your `DropDownReceiver::onBackButtonPressed` method to handle backward navigation by processing click events on the device's back button. _Both forward and backward navigation should, ideally, be delegated to your `Choreographer`._

* For forward navigation, your `DropDownReceiver` can either handle instantiating the target fragment to submit to the `Choreographer` for showing, or call a more specific method on the `Choreographer` to do it. The former prevents the `DropDownReceiver` from worrying as much about view logic, but the latter prevents easy re-use of the `Choreographer` since it will inevitably contain logic specific to each of your `Fragment` implementations. The choice is personal preference.

* For backward navigation consider an interface that each Fragment can implement. This can allow the visible fragment to perform cleanup logic when backward navigation is triggered (but before the view is changed out), or consume the back button press instead of propagating it.

Additional Thoughts
-------------------
1. In this implementation, I experimented with having a persistent ribbon that is present above whichever fragment is showing. But managing the state of anything in that ribbon quickly becomes a nightmare.  
I also figure that providing an API for child fragments to supplement or otherwise modify that ribbon would be ugly.  
If you want a ribbon/title bar to consistently show on each screen, consider the following:
    - A reusable layout resource. Consuming layout resources would utilize Android's `<include>` or `<merge>` tags their XML.
    - A reusable control with a nice API (preferably this one). This is achievable through subclassing a layout widget like `LinearLayout` or `ConstraintLayout`.
2. I have to admit: I hate intents. I might hate them more than  Android's prefrences! In this case, intents are a fine mechanism, but if you come up with something that doesn't rely on them then :thumbs_up:. Please feel free to contribute it here to help others!

Contribute
----------
Because of quirks like 

1. ATAK Context vs Plugin Context
2. Using the Drop Down for views vs owning the Activity

design using Fragments doesn't follow directly from examples in the Android Developer Documentation or any non-TAK-related blog you'll probably find.

The overall pattern of managing fragment navigation for ATAK plugins by using a single "root" fragment was pioneered by the Combat Swim plugin (RIP). Many of the thoughts and recommendations presented here are the result of a lot of experimentation and iterating on that example for the [TacticalRoute plugin][TR].

But I don't know everything! 

The implementation here is _a_ way it can be accomplished. If experimentation yields more canonical, more efficient, or more maintainable ways of accomplishing this then please contribute to this example!

[Fragments]: https://developer.android.com/guide/components/fragments
[Choreographer]: ./app/src/main/java/com/atakmap/android/usingfragments/ui/frag/ChoreographerFragment.java
[TR]: https://git.takmaps.com/SOMPE-Plugin-Developers/tacticalroute
[impl]: ./app/src/main/java/com/atakmap/android/usingfragments/ui/frag