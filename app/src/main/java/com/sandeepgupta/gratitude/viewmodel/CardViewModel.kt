package com.sandeepgupta.gratitude.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeepgupta.gratitude.model.CardModel
import com.sandeepgupta.gratitude.repository.ConnectivityRepository
import com.sandeepgupta.gratitude.repository.Repository
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
                date.value = repo.getDate()
                getCardListFromRoomDb()
            }
        }
    }

    fun getCardListFromRoomDb() = viewModelScope.launch {
        repo.getCardListFromRoomDb().flowOn(Dispatchers.IO)
            .catch {
                _cardList.value = ApiState.Error(it.message.toString())
            }
            .collect {
                _cardList.value = ApiState.Success(it)
            }
    }

    fun fetchFromRemoteAndSave() = viewModelScope.launch {
        repo.setDate(date.value)
        repo.fetchFromRemoteAndSave(localDateToYYYYMMDDFormat(date.value)).flowOn(Dispatchers.IO)
            .catch {
                _cardList.value = ApiState.Error(it.message.toString())
            }
            .collect {
                _cardList.value = it
            }
    }

    fun previousDay() {
        val diff = ChronoUnit.DAYS.between(date.value, LocalDate.now())
        if (diff < 7) {
            date.value = date.value.minusDays(1)
            fetchFromRemoteAndSave()
        }
    }

    fun nextDay() {
        if (date.value < LocalDate.now()) {
            date.value = date.value.plusDays(1)
            fetchFromRemoteAndSave()
        }
    }

}