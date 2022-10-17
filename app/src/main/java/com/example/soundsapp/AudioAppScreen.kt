package com.example.soundsapp


import android.content.ContentResolver
import android.content.Context
import android.widget.Toast
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
import com.example.soundsapp.helpers.FileManger
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
    contentResolver : ContentResolver,
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
//                    navController.navigateUp()
                    navController.popBackStack()
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
                    context,
                    contentResolver )
            }
            composable(route = AppScreen.Select.name) {
                SelectAudio(
                    DataBase.getAllGroups(),
                    navigateToGroupManagerScreen = {
                        MediaPlayerFW.reset()
                        navController.navigate(AppScreen.GroupManager.name)
                    },
                    audioSearchBTN,
                    discardBTN = {
                        addNewAudioScreenObjectStatus.reset()
                        MediaPlayerFW.reset()
                        navController.popBackStack()
                        navController.navigate(AppScreen.Start.name)
                    },
                    saveBTN = {
                        if(addNewAudioScreenObjectStatus.isSavable()) {
                            //Copy file and Save Uri
                            FileManger.onSave(  context,
                                                addNewAudioScreenObjectStatus.selectedAudioUri!! ,
                                                addNewAudioScreenObjectStatus.selectedAudioFileName,
                                                contentResolver)
                            //Reset MediaPlayerFW
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
                            val text = "Audio updated"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(context, text, duration)
                            toast.show()
                        },
                        deleteBTN = {
                            DataBase.deleteAudio(editAudioObjectStatus.selectedAudio!!)
                            editAudioObjectStatus.reset()
                            MediaPlayerFW.reset()
                            navController.navigate(AppScreen.Start.name)
                            val text = "Audio deleted"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(context, text, duration)
                            toast.show()
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
                GroupManager(context)
            }
        }
    }
}
