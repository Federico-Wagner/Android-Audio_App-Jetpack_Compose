package com.example.soundsapp


import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.helpers.MediaPlayerFW
import com.example.soundsapp.model.DataBase
import com.example.soundsapp.ui.*


/**
 * enum values that represent the screens in the app
 */
enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.mainScreen),
    Select(title = R.string.selectAudio),
    Record(title = R.string.recordAudio),
    Details(title = R.string.audioDetails),
    GroupManager(title = R.string.groupManager)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun NavigationAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(currentScreen != AppScreen.Start)
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.generic)
                    )
                }
            }
        }
    )
}

@Composable
fun AudioAppScreen(
    audioSearchBTN: () -> Unit,
    context: Context,
    modifier : Modifier = Modifier,
//    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Start.name
    )

    Scaffold(
        topBar = {
            NavigationAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    MediaPlayerFW.reset()
                    addNewAudioScreenObjectStatus.reset()
                    editAudioObjectStatus.reset()
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.Start.name) {
                MainScreen(
                    DataBase.getAllRecords(),
                    DataBase.getAllGroups(),
                    navigateToNewAudio = {
                        MediaPlayerFW.reset()
                        navController.navigate(AppScreen.Select.name)
                    },
                    navigateToAudioDetail = {
                        MediaPlayerFW.reset()
                        navController.navigate(AppScreen.Details.name)
                    },
                    search = { navController.navigate(AppScreen.GroupManager.name) },
                    context
                )
            }
            composable(route = AppScreen.Select.name) {
                SelectAudio(audioSearchBTN,
                    discardBTN = {
                        addNewAudioScreenObjectStatus.reset()
                        MediaPlayerFW.reset()
                        navController.navigate(AppScreen.Start.name)
                    },
                    saveBTN = {
                        if(addNewAudioScreenObjectStatus.isSavable()) {
                            DataBase.saveAudioinDB(context)
                            addNewAudioScreenObjectStatus.reset()
                            MediaPlayerFW.reset()
                            navController.navigate(AppScreen.Start.name)
                        }
                    },
                    context)
            }
            composable(route = AppScreen.Details.name) {
                editAudioObjectStatus.selectedAudio?.let { selectedAudio ->
                    EditAudio(
                        selectedAudio,
                        DataBase.getAllGroups(),
                        discardBTN = {
                            editAudioObjectStatus.reset()
                            MediaPlayerFW.reset()
                            navController.navigate(AppScreen.Start.name)
                        },
                        saveBTN = {
                            DataBase.updateAudioInDB(editAudioObjectStatus.selectedAudio!!)
                            editAudioObjectStatus.reset()
                            MediaPlayerFW.reset()
                            navController.navigate(AppScreen.Start.name)
                        },
                        deleteBTN = {
                            DataBase.deleteAudio(editAudioObjectStatus.selectedAudio!!)
                            editAudioObjectStatus.reset()
                            MediaPlayerFW.reset()
                            navController.navigate(AppScreen.Start.name)
                        },
                        navigateToGroupManagerScreen = {
                            editAudioObjectStatus.reset()
                            MediaPlayerFW.reset()
                            navController.navigate(AppScreen.GroupManager.name)
                        },
                        context)
                }
            }
            composable(route = AppScreen.GroupManager.name) {
                GroupManager()
            }
        }
    }
}



///**
// * Resets the [OrderUiState] and pops up to [CupcakeScreen.Start]
// */
//private fun cancelOrderAndNavigateToStart(
//    viewModel: OrderViewModel,
//    navController: NavHostController
//) {
//    viewModel.resetOrder()
//    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
//}
//
///**
// * Creates an intent to share order details
// */
//private fun shareOrder(context: Context, subject: String, summary: String) {
//    // Create an ACTION_SEND implicit intent with order details in the intent extras
//    val intent = Intent(Intent.ACTION_SEND).apply {
//        type = "text/plain"
//        putExtra(Intent.EXTRA_SUBJECT, subject)
//        putExtra(Intent.EXTRA_TEXT, summary)
//    }
//    context.startActivity(
//        Intent.createChooser(
//            intent,
//            context.getString(R.string.new_cupcake_order)
//        )
//    )
//}
