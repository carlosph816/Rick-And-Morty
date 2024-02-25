package com.kairos.caperezh.domain

import com.kairos.caperezh.common.DataState
import com.kairos.caperezh.data.model.LocationsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend fun getLocations(): Flow<DataState<LocationsModel?>> = homeRepository.getDataLocations()
}