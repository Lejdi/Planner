package pl.lejdi.planner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import pl.lejdi.planner.business.util.date.MockDateUtil
import pl.lejdi.planner.business.utils.date.DateUtil

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DateUtilModule::class]
)
object MockDateUtilModule {

    @Provides
    fun provideDateUtil(): DateUtil {
        return MockDateUtil
    }
}