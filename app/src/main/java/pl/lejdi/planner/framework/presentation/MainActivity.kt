package pl.lejdi.planner.framework.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import pl.lejdi.planner.business.data.model.Task
import pl.lejdi.planner.business.utils.date.DateUtil
import pl.lejdi.planner.framework.presentation.common.navigation.FAB_EXPLODE_ANIMATION_KEY
import pl.lejdi.planner.framework.presentation.common.navigation.NO_ANIMATION
import pl.lejdi.planner.framework.presentation.common.navigation.NavTypes
import pl.lejdi.planner.framework.presentation.common.ui.styling.PlannerTheme
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreen
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenRoute
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreen
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenRoute
import javax.inject.Inject
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dateUtil: DateUtil

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlannerTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = DashboardScreenRoute,
                    ) {
                        composable<DashboardScreenRoute> {
                            DashboardScreen(
                                navigateToDetails = { task ->
                                    navController.navigate(
                                        EditTaskScreenRoute(task)
                                    )
                                },
                                animatedVisibilityScope = this
                            )
                        }
                        composable<EditTaskScreenRoute>(
                            typeMap = mapOf(typeOf<Task?>() to NavTypes.TaskType)
                        ) {
                            val args = it.toRoute<EditTaskScreenRoute>()
                            val animationKey = if(args.taskDetails == null) FAB_EXPLODE_ANIMATION_KEY else NO_ANIMATION
                            EditTaskScreen(
                                taskDetails = args.taskDetails,
                                navigateBack = {
                                    navController.navigateUp()
                                },
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(animationKey),
                                        animatedVisibilityScope = this,
                                        boundsTransform = { _, _ ->
                                            tween(500)
                                        }
                                    ),
                                dateUtil = dateUtil
                            )
                        }
                    }
                }
            }
        }
    }
}