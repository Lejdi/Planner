package pl.lejdi.planner.framework.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.framework.presentation.common.navigation.NavTypes
import pl.lejdi.planner.framework.presentation.common.navigation.REFRESH_DASHBOARD_KEY
import pl.lejdi.planner.framework.presentation.common.ui.styling.PlannerTheme
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreen
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenRoute
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreen
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenRoute
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlannerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = DashboardScreenRoute,
                ) {
                    composable<DashboardScreenRoute> { backStackEntry ->
                        DashboardScreen(
                            navigateToDetails = { task ->
                                navController.navigate(
                                    EditTaskScreenRoute(task)
                                )
                            },
                            refreshScreen = backStackEntry.savedStateHandle.get<Boolean>(REFRESH_DASHBOARD_KEY) ?: false
                        )
                    }
                    composable<EditTaskScreenRoute>(
                        typeMap = mapOf(typeOf<Task?>() to NavTypes.TaskType)
                    ) {
                        val args = it.toRoute<EditTaskScreenRoute>()
                        EditTaskScreen(
                            taskDetails = args.taskDetails,
                            navigateBack = { refresh ->
                                navController.previousBackStackEntry
                                    ?.savedStateHandle
                                    ?.set(REFRESH_DASHBOARD_KEY, refresh)
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}