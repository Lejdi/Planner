package pl.lejdi.planner.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.lejdi.planner.business.utils.date.DateUtil
import pl.lejdi.planner.business.utils.date.DefaultDateUtil
import pl.lejdi.planner.business.utils.date.format.DateFormatProvider
import pl.lejdi.planner.business.utils.date.format.DateFormatter
import pl.lejdi.planner.business.utils.date.format.DateFormatterImpl
import pl.lejdi.planner.business.utils.date.format.DefaultDateFormatProvider


@Module
@InstallIn(SingletonComponent::class)
abstract class BindingDateModule {

    @Binds
    abstract fun provideDateFormat(
        defaultDateFormatProvider: DefaultDateFormatProvider,
    ): DateFormatProvider
}

@Module
@InstallIn(SingletonComponent::class)
object DateModule {

    @Provides
    fun provideDateFormatter(
        formatProvider: DateFormatProvider,
    ): DateFormatter {
        return DateFormatterImpl(formatProvider)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DateUtilModule {

    @Provides
    fun provideDateUtil(): DateUtil {
        return DefaultDateUtil()
    }
}