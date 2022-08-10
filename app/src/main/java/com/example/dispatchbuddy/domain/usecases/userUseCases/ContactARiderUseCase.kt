package com.example.dispatchbuddy.domain.usecases.userUseCases

import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.network.GenericResponse
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderModel
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.ContactRiderResponse
import com.example.dispatchbuddy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactARiderUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        contactRiderModel: ContactRiderModel
    ): Flow<Resource<GenericResponse<ContactRiderResponse>>> =
        repository.contactARider(contactRiderModel = contactRiderModel)
}