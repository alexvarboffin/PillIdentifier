package com.walhalla.pillfinder.fragment.api2

/**
 * Implemented by the drug-search screen ([RxNorm]) that hosts the 10 drug tabs.
 * Tab fragments are children of it (ViewPager2 FragmentStateAdapter(RxNorm) -> childFragmentManager),
 * so they reach it via `parentFragment as? ConceptNavigator`. When a tab is hosted elsewhere
 * (no navigator parent) the rows simply stay plain text.
 */
interface ConceptNavigator {
    /** Open [rxcui] exactly like a successful search; the current drug goes onto the shared back history. */
    fun openConcept(rxcui: String, name: String?)
}