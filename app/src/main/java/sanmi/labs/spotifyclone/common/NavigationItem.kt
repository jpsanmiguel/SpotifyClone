package sanmi.labs.spotifyclone.common

import sanmi.labs.spotifyclone.R

sealed class NavigationItem(val route: String, val icon: Int, val title: String) {
    object Home: NavigationItem("top", R.drawable.ic_baseline_home_24, "Home")
    object Saved: NavigationItem("saved", R.drawable.ic_baseline_favorite_24, "Saved")
}
