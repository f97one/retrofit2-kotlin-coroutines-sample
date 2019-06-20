package com.hossynohito.retrofit2kotlincoroutinessample.presentation.boardlist

import com.hossynohito.retrofit2kotlincoroutinessample.domain.entity.Board
import com.hossynohito.retrofit2kotlincoroutinessample.domain.usecase.BoardUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class BoardListPresenter @Inject constructor(
        private val view: BoardListActivity,
        private val useCase: BoardUseCase
) {

    private val job = Job()

    fun onViewCreated() {
        GlobalScope.launch(job + Dispatchers.Main) { loadPipelines() }
    }

    fun onDestroy() {
        job.cancel()
    }

    fun onBoardClick(board: Board) {
        view.navigateToBoardDetail(board)
    }

    private suspend fun loadPipelines() {
        try {
            useCase.getMyBoards().let(view::addBoards)
        } catch (t: Throwable) {
            t.message?.let(view::showErrorToast)
        }
    }
}