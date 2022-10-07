package com.example.soundsapp


import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
//import com.example.cupcake.data.DataSource.flavors
//import com.example.cupcake.data.DataSource.quantityOptions
//import com.example.cupcake.data.OrderUiState
//import com.example.cupcake.ui.OrderSummaryScreen
//import com.example.cupcake.ui.OrderViewModel
//import com.example.cupcake.ui.SelectOptionScreen
//import com.example.cupcake.ui.StartOrderScreen
import androidx.navigation.compose.rememberNavController
import com.example.soundsapp.db.entity.Audio
import com.example.soundsapp.ui.SelectAudio
import com.example.soundsapp.ui.SoundApp


/**
 * enum values that represent the screens in the app
 */
enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.mainScreen),
    Select(title = R.string.selectAudio),
    Record(title = R.string.recordAudio),
    Details(title = R.string.audioDetails)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@Composable
fun CupcakeAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
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
fun AudioApp(soundsDBx: List<Audio>,
             addAudioBTN : () -> Unit,
             audioSearchBTN: () -> Unit,
             saveBTN: (String) -> Unit,
             goBackBTN: () -> Unit,
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
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
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
                SoundApp(soundsDBx,
                    showHidePopupBTN = {
                        navController.navigate(AppScreen.Select.name)
                    },
                    addAudioBTN, audioSearchBTN,saveBTN,goBackBTN, context
//                    quantityOptions = quantityOptions,
//                    onNextButtonClicked = {
//                        viewModel.setQuantity(it)
//                        navController.navigate(CupcakeScreen.Flavor.name)
//                    }
                )
            }
            composable(route = AppScreen.Select.name) {
                val context = LocalContext.current
//                audioSearchBTN: () -> Unit,
//                saveBTN: (String) -> Unit,
//                goBackBTN: () -> Unit,
//                showHidePopupBTN: () -> Unit,
//                context: Context,
//                modifier: Modifier = Modifier){
                val showHidePopupBTN = fun(){}
                SelectAudio(audioSearchBTN, saveBTN, goBackBTN, showHidePopupBTN, context)
//                    subtotal = uiState.price,
//                    onNextButtonClicked = { navController.navigate(AppScreen.Pickup.name) },
//                    onCancelButtonClicked = {
//                        cancelOrderAndNavigateToStart(viewModel, navController)
//                    },
//                    options = flavors.map { id -> context.resources.getString(id) },
//                    onSelectionChanged = { viewModel.setFlavor(it) }
//                )
            }
//            composable(route = AppScreen.Pickup.name) {
//                SelectOptionScreen(
//                    subtotal = uiState.price,
//                    onNextButtonClicked = { navController.navigate(AppScreen.Summary.name) },
//                    onCancelButtonClicked = {
//                        cancelOrderAndNavigateToStart(viewModel, navController)
//                    },
//                    options = uiState.pickupOptions,
//                    onSelectionChanged = { viewModel.setDate(it) }
//                )
//            }
//            composable(route = AppScreen.Summary.name) {
//                val context = LocalContext.current
//                OrderSummaryScreen(
//                    orderUiState = uiState,
//                    onCancelButtonClicked = {
//                        cancelOrderAndNavigateToStart(viewModel, navController)
//                    },
//                    onSendButtonClicked = { subject: String, summary: String ->
//                        shareOrder(context, subject = subject, summary = summary)
//                    }
//                )
//            }
        }
    }
}
//
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
