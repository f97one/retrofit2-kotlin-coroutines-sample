package com.hossynohito.retrofit2kotlincoroutinessample.domain.usecase

import com.hossynohito.retrofit2kotlincoroutinessample.data.network.BoardsApi
import com.hossynohito.retrofit2kotlincoroutinessample.data.network.CardsApi
import com.hossynohito.retrofit2kotlincoroutinessample.data.network.ListsApi
import com.hossynohito.retrofit2kotlincoroutinessample.di.NetworkModule.Companion.ACCESS_TOKEN
import com.hossynohito.retrofit2kotlincoroutinessample.di.NetworkModule.Companion.API_KEY
import com.hossynohito.retrofit2kotlincoroutinessample.domain.entity.Board
import com.hossynohito.retrofit2kotlincoroutinessample.domain.entity.Card
import com.hossynohito.retrofit2kotlincoroutinessample.domain.entity.Pipeline
import com.hossynohito.trello.data.network.MembersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BoardUseCase @Inject constructor(
        private val membersApi: MembersApi,
        private val boardsApi: BoardsApi,
        private val listsApi: ListsApi,
        private val cardsApi: CardsApi
) {

    suspend fun getMyBoards(): List<Board> = withContext(Dispatchers.Default) {
        membersApi.getBoards("me", API_KEY, ACCESS_TOKEN).await()
    }

    suspend fun getPipelines(boardId: String): List<Pipeline> = withContext(Dispatchers.Default) {
        boardsApi.getPipelines(boardId, API_KEY, ACCESS_TOKEN).await()
    }

    suspend fun getCards(pipelineId: String): List<Card> = withContext(Dispatchers.Default) {
        listsApi.getCards(pipelineId, API_KEY, ACCESS_TOKEN).await()
    }

    suspend fun addCard(pipelineId: String, cardName: String): Card = withContext(Dispatchers.Default) {
        cardsApi.addCard(pipelineId, cardName, API_KEY, ACCESS_TOKEN).await()
    }

    suspend fun deleteCard(cardId: String) = withContext(Dispatchers.Default) {
        cardsApi.deleteCard(cardId, API_KEY, ACCESS_TOKEN).await()
    }
}