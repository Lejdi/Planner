package pl.lejdi.planner.framework.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import pl.lejdi.planner.framework.presentation.common.ui.PlannerTheme
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreen
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenRoute
import pl.lejdi.planner.framework.presentation.taskslist.ui.TasksListScreen
import pl.lejdi.planner.framework.presentation.taskslist.ui.TasksListScreenRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlannerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = TasksListScreenRoute,
                ) {
                    composable<TasksListScreenRoute> {
                        TasksListScreen(
                            navigateToDetails = { task ->
                                navController.navigate(
                                    EditTaskScreenRoute(task)
                                )
                            }
                        )
                    }
                    composable<EditTaskScreenRoute> {
                        val args = it.toRoute<EditTaskScreenRoute>()
                        EditTaskScreen(
                            taskDetails = args.taskDetails,
                            navigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}