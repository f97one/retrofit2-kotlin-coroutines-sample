package com.hossynohito.retrofit2kotlincoroutinessample.presentation.boarddetail

import com.hossynohito.retrofit2kotlincoroutinessample.domain.entity.Card
import com.hossynohito.retrofit2kotlincoroutinessample.domain.usecase.BoardUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardListFragmentPresenter @Inject constructor(
        private val view: CardListFragment,
        private val pipelineId: String,
        private val useCase: BoardUseCase
) {

    private val job = Job()

    fun onViewCreated() {
        GlobalScope.launch(job + Dispatchers.Main) {
            loadCards()
        }
    }

    fun onDestroy() {
        job.cancel()
    }

    fun onAddButtonClick(text: String) {
        GlobalScope.launch(job + Dispatchers.Main) {
            addCard(text)
        }
    }

    fun  onCardLongClick(card: Card) {
        GlobalScope.launch(job + Dispatchers.Main) {
            deleteCard(card.id)
        }
    }

    private suspend fun loadCards() {
        try {
            useCase.getCards(pipelineId).let(view::addCards)
        } catch (t: Throwable) {
            t.message?.let(view::showErrorToast)
        }
    }

    private suspend fun addCard(text: String) {
        try {
            useCase.addCard(pipelineId, text).let(view::addCard)
        } catch (t: Throwable) {
            t.message?.let(view::showErrorToast)
        }
    }

    private suspend fun deleteCard(cardId: String) {
        try {
            useCase.deleteCard(cardId)
            view.removeCard(cardId)
        } catch (t: Throwable) {
            t.message?.let(view::showErrorToast)
        }
    }
}