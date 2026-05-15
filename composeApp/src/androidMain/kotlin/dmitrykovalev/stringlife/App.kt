package dmitrykovalev.stringlife

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dmitrykovalev.stringlife.di.ServiceLocator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val viewModel = ServiceLocator.instrumentViewModel

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onAddInstrumentClick = { navController.navigate("addInstrument") })
        }
        composable("addInstrument") {
            AddInstrumentScreen(
                onBack = { navController.popBackStack() },
                onSave = { name, type, lastStringChangeDate ->
                    viewModel.addInstrument(name, type, lastStringChangeDate)
                    navController.popBackStack()
                })
        }

    }
}