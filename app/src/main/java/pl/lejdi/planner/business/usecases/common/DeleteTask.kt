package pl.lejdi.planner.business.usecases.common

import pl.lejdi.planner.business.usecases.UseCase
import pl.lejdi.planner.business.usecases.UseCaseResult

class DeleteTask : UseCase<UseCaseResult<Boolean>, String>() {
    override suspend fun execute(params: String): UseCaseResult<Boolean> {
        return UseCaseResult.Success(false)
    }
}