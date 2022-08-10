package com.example.dispatchbuddy.data.remote.dto.models.allRequestModels


data class AllRequestSectionedResponse(
    var sectionHeaders: List<CharSequence>,
    var riderResponse: List<AllUserRequestResponseContent>
)
