package com.rui.mvvm.repository

import com.rui.mvvm.bean.TopArticleBean
import com.rui.mvvm.retrofit.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val service: Service) {

    suspend fun getTopArticle(): List<TopArticleBean>? =
        withContext(Dispatchers.IO) {
            service.getTopArticle().data
        }

     fun getTopArticleByFlow(): Flow<List<TopArticleBean>?> =
        flow {
            emit(service.getTopArticle().data)
        }
            .flowOn(Dispatchers.IO)

//    fun getTopArticleByFlow(): Flow<UiState> =
//        flow {
//            emit(UiState.Success(service.getTopArticle().data))
//        }
//            .flowOn(Dispatchers.IO)


}