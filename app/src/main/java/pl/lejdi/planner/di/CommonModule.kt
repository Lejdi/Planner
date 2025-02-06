package pl.lejdi.planner.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.utils.date.DateFormatProvider
import pl.lejdi.planner.business.utils.date.DateFormatter
import pl.lejdi.planner.business.utils.date.DateFormatterImpl
import pl.lejdi.planner.business.utils.date.DefaultDateFormatProvider


@Module
@InstallIn(SingletonComponent::class)
abstract class BindingCommonModule {

    @Binds
    abstract fun provideDateFormat(
        defaultDateFormatProvider: DefaultDateFormatProvider
    ) : DateFormatProvider
}

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideDateFormatter(
        formatProvider: DateFormatProvider
    ) : DateFormatter {
        return DateFormatterImpl(formatProvider)
    }
}