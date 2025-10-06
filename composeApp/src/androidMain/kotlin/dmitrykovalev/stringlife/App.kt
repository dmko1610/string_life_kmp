package dmitrykovalev.stringlife

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dmitrykovalev.stringlife.models.InstrumentType
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onAddInstrumentClick = { navController.navigate("addInstrument") })
        }
        composable("addInstrument") {
            AddInstrumentScreen(
                onBack = { navController.popBackStack() },
                onSave = { name: String, type: InstrumentType, replacementDate: LocalDate? ->
                    navController.popBackStack()
                })
        }

    }
}