package pl.lejdi.planner.framework.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import pl.lejdi.planner.framework.presentation.common.navigation.NavigationBarItems
import pl.lejdi.planner.framework.presentation.common.ui.styling.PlannerTheme
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreen
import pl.lejdi.planner.framework.presentation.edittask.ui.EditTaskScreenRoute
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreen
import pl.lejdi.planner.framework.presentation.dashboard.ui.DashboardScreenRoute
import pl.lejdi.planner.framework.presentation.grocery.ui.GroceryScreen
import pl.lejdi.planner.framework.presentation.grocery.ui.GroceryScreenRoute
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
            val navController = rememberNavController()
            PlannerTheme {
                Scaffold(
                    content = { contentPadding ->
                        SharedTransitionLayout {
                            NavHost(
                                navController = navController,
                                startDestination = DashboardScreenRoute,
                                modifier = Modifier
                                    .padding(bottom = contentPadding.calculateBottomPadding())
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
                                composable<GroceryScreenRoute> {
                                    GroceryScreen()
                                }
                            }
                        }
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            content = {
                                val selectedItem = remember { mutableStateOf(NavigationBarItems.TASKS) }
                                NavigationBarItems.entries.forEach { navBarItem ->
                                    NavigationBarItem(
                                        selected = navBarItem == selectedItem.value,
                                        icon = {
                                            Icon(
                                                imageVector = navBarItem.image,
                                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(28.dp)
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = Color.Gray.copy(alpha = 0.1f),
                                        ),
                                        onClick = {
                                            selectedItem.value = navBarItem
                                            when(navBarItem){
                                                NavigationBarItems.TASKS -> {
                                                    navController.navigateUp()
                                                }
                                                NavigationBarItems.GROCERIES -> {
                                                    navController.navigate(GroceryScreenRoute)
                                                }
                                            }
                                        }
                                    )
                                }
                            },
                        )
                    }
                )
            }
        }
    }
}