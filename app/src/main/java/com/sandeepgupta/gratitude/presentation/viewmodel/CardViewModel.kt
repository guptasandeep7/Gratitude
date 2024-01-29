package com.sandeepgupta.gratitude.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeepgupta.gratitude.domain.model.CardModel
import com.sandeepgupta.gratitude.data.repository.ConnectivityRepository
import com.sandeepgupta.gratitude.domain.repo.Repository
import com.sandeepgupta.gratitude.util.ApiState
import com.sandeepgupta.gratitude.util.localDateToYYYYMMDDFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repo: Repository,
    private val connectivityRepository: ConnectivityRepository
) : ViewModel() {

    private val _cardList = MutableStateFlow<ApiState<List<CardModel>>>(ApiState.Loading())
    val cardList: StateFlow<ApiState<List<CardModel>>> = _cardList

    var date = MutableStateFlow<LocalDate>(LocalDate.now())

    val isOnline = connectivityRepository.isConnected


    init {
        viewModelScope.launch {
            if (repo.roomDbIsEmpty()) {
                fetchFromRemoteAndSave()
            } else {
                getCardListFromRoomDb()
            }
        }
    }

    //first check in room db if not present then fetch from remote
    fun getCardList() = viewModelScope.launch {
        if (date.value == repo.getDate()) { // list present in room db
            getCardListFromRoomDb()
        } else {
            fetchFromRemoteAndSave()
        }
    }

    private fun getCardListFromRoomDb() = viewModelScope.launch {
        repo.getCardListFromRoomDb().flowOn(Dispatchers.IO)
            .catch {
                _cardList.value = ApiState.Error(it.message.toString())
            }
            .collect {
                _cardList.value = ApiState.Success(it)
                date.value = repo.getDate()
            }
    }

    private fun fetchFromRemoteAndSave() = viewModelScope.launch {
        repo.fetchFromRemoteAndSave(localDateToYYYYMMDDFormat(date.value)).flowOn(Dispatchers.IO)
            .catch {
                _cardList.value = ApiState.Error(it.message.toString())
            }
            .collect {
                when(it){
                    is ApiState.Success -> {
                        if (it.data.isNullOrEmpty()){
                            _cardList.value = ApiState.Error("No data found !!! Please retry.")
                        }
                        else{
                            _cardList.value = it
                            repo.setDate(date.value)
                        }
                    }
                    else -> _cardList.value = it
                }
            }
    }

    fun previousDay() {
        val diff = ChronoUnit.DAYS.between(date.value, LocalDate.now())
        if (diff < 7) {
            date.value = date.value.minusDays(1)
            getCardList()
        }
    }

    fun nextDay() {
        if (date.value < LocalDate.now()) {
            date.value = date.value.plusDays(1)
            getCardList()
        }
    }

}