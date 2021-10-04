package sanmi.labs.spotifyclone

import android.app.Activity
import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import sanmi.labs.spotifyclone.common.NavigationItem
import sanmi.labs.spotifyclone.savedtracks.SavedTracksScreenWithState
import sanmi.labs.spotifyclone.toptracks.TopTracksScreenWithState
import sanmi.labs.spotifyclone.ui.theme.SpotifyCloneTheme
import com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE

import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import com.spotify.sdk.android.auth.*
import sanmi.labs.spotifyclone.common.CLIENT_ID
import sanmi.labs.spotifyclone.common.REDIRECT_URL
import sanmi.labs.spotifyclone.common.TOKEN_KEY


class MainActivity : ComponentActivity() {

    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getPreferences(Context.MODE_PRIVATE)

        getToken()

        setContent {

            SpotifyCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(this)
                }
            }
        }
    }

    private fun getToken() {
        var token = sharedPref.getString(TOKEN_KEY, "")
        if (token.isNullOrEmpty()) {
            loginWithSpotify(this)
        }
    }

//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == REQUEST_CODE) {
//            val response = AuthorizationClient.getResponse(result.resultCode, intent)
//            when (response.type) {
//                AuthorizationResponse.Type.TOKEN -> {
//                }
//                AuthorizationResponse.Type.ERROR -> {
//                }
//                else -> {
//                }
//            }
//        }
//    }
//
//    fun loginWithSpotify() {
//        val REQUEST_CODE = 1337
//        val REDIRECT_URI = "http://example.com/callback/"
//
//        val builder: AuthorizationRequest.Builder =
//            AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
//
//        builder.setScopes(arrayOf("streaming"))
//        val request: AuthorizationRequest = builder.build()
//
//        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        Log.d("SpotifyLogin", "Something $requestCode & $REQUEST_CODE")
        super.onActivityResult(requestCode, resultCode, intent)
        Log.d("SpotifyLogin", "Something1 $requestCode & $REQUEST_CODE")

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            Log.d("SpotifyLogin", "Something2 $requestCode & $REQUEST_CODE")
            val response = AuthorizationClient.getResponse(resultCode, intent)
            Log.d("SpotifyLogin", "Something3 $requestCode & $REQUEST_CODE")
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    with (sharedPref.edit()) {
                        putString(TOKEN_KEY, response.accessToken)
                        apply()
                    }
                    Log.d("SpotifyLoginToken", response.accessToken)
                }
                AuthorizationResponse.Type.ERROR -> {
                    Log.d("SpotifyLoginError", response.toString())
                }
                else -> {
                    Log.d("SpotifyLoginElse", response.toString())
                }
            }
            Log.d("SpotifyLogin", "Something4 $requestCode & $REQUEST_CODE")
        }
    }
}

@Composable
fun MainScreen(activity: Activity) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController, activity)
        }
    }
}

fun loginWithSpotify(activity: Activity) {
    val REQUEST_CODE = 1138

    val builder: AuthorizationRequest.Builder =
        AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URL)

    builder.setScopes(arrayOf("streaming"))
    val request: AuthorizationRequest = builder.build()

    AuthorizationClient.openLoginActivity(activity, REQUEST_CODE, request)
}

@Composable
fun TopAppBar() {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name), fontSize = 18.sp) },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )

}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Saved
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title)},
                label = { Text(text = item.title) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, activity: Activity) {
    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
//            TopTracksScreenWithState() {
//                Log.d("MainActivity", it.name)
//            }
            IconButton(onClick = { loginWithSpotify(activity) }) {
                Icon(
                    Icons.Filled.Search,
                    "",
                    tint = Color.Black
                )
            }
        }
        composable(NavigationItem.Saved.route) {
            SavedTracksScreenWithState() {
                Log.d("MainActivity", it.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewLightPreview() {
    SpotifyCloneTheme {
//        MainScreen()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MainViewDarkPreview() {
    SpotifyCloneTheme {
//        MainScreen()
    }
}