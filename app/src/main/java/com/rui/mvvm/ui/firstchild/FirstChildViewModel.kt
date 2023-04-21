package com.rui.mvvm.ui.firstchild

import androidx.lifecycle.*
import com.rui.mvvm.bean.TopArticleBean
import com.rui.mvvm.repository.ArticleRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

/**
 * This file shows how to implement livedata and stateflow in viewModel
 */
class FirstChildViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _topArticlesLiveData = MutableLiveData<List<TopArticleBean>>()
    //if use liveData no.1
    val topArticlesLiveData: LiveData<List<TopArticleBean>> = _topArticlesLiveData
    //if use liveData no.2
//    var topArticlesLiveData = articleRepository.getTopArticleByFlow().asLiveData()


    private var _topArticlesStateFlow: MutableStateFlow<List<TopArticleBean>?> =
        MutableStateFlow(emptyList())
    //if use stateFlow no.1
//    val topArticlesStateFlow: StateFlow<List<TopArticleBean>?> = _topArticlesStateFlow

    //if use stateFlow no.2
//    val topArticlesStateFlow: StateFlow<List<TopArticleBean>?> =
//        articleRepository.getTopArticleByFlow()
//            .stateIn(
//                scope = viewModelScope,
//                started = WhileSubscribed(5000),
//                initialValue = emptyList()
//            )

    val topArticlesStateFlow: StateFlow<UiState<List<TopArticleBean>?>> =
        articleRepository.getTopArticleByFlow()
            .map {
                if (null == it || it.isEmpty())
                    UiState.Empty
                else
                    UiState.Success(it)
            }
            .catch { UiState.Error(it) }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000),
                initialValue = UiState.Loading
            )


    init {
        //if use liveData no.1
//        loadData()
        //if use stateFlow no.1
//        loadDataByFlow()
    }

    private fun loadData() {
        viewModelScope.launch {
            val topArticles = try {
                articleRepository.getTopArticle()
            } catch (ex: Exception) {
                null
            }
            topArticles?.let { _topArticlesLiveData.postValue(it) }
        }
    }

    //if use stateFlow no.1
//    private fun loadDataByFlow() {
//        viewModelScope.launch {
//            articleRepository.getTopArticleByFlow()
//                .collect {
//                    _topArticlesStateFlow.value = it
//                }
//        }
//    }
}