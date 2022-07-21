package com.example.dispatchbuddy.common

import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.RiderProfile
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse2

val ridersList = listOf(
    RiderProfile(
        R.drawable.profile_avatar,
        "Rachael Sampson",
        "08169271995",
        "Lagos",
        "Joined, June 25, 2022"
    ),
    RiderProfile(
        R.drawable.profile_avatar,
        "Rachael Sampson",
        "08169271995",
        "Lagos",
        "Joined, June 25, 2022"
    ),
    RiderProfile(
        R.drawable.profile_avatar,
        "Rachael Sampson",
        "08169271995",
        "Lagos",
        "Joined, June 25, 2022"
    ),
    RiderProfile(
        R.drawable.profile_avatar,
        "Rachael Sampson",
        "08169271995",
        "Lagos",
        "Joined, June 25, 2022"
    ),
    RiderProfile(
        R.drawable.profile_avatar,
        "Rachael Sampson",
        "08169271995",
        "Lagos",
        "Joined, June 25, 2022"
    )
)
val aWeekAgo = arrayListOf(
    RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2021-08-23T09:52:07 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-02-24T10:34:38 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2021-05-31T08:32:04 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2017-11-03T09:05:30 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2014-08-05T04:54:36 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2018-08-25T09:15:43 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2015-08-11T03:35:31 -01:00"
    ),
)
val today = arrayListOf(
    RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2015-08-11T03:35:31 -01:00"
    )
)
val yesterday = arrayListOf(
    RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2021-05-31T08:32:04 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2017-11-03T09:05:30 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2014-08-05T04:54:36 -01:00"
    )
)
//val todaySection = listOf(RiderSectionResponse2("Today", today))
//val yesterdaySection = listOf(RiderSectionResponse2("Yesterday", yesterday))
//val aWeekAgoSection = listOf(RiderSectionResponse2("A week ago", aWeekAgo))

val requestList = mutableListOf(
    RiderSectionResponse2("Today", today),
    RiderSectionResponse2("Yesterday", yesterday),
    RiderSectionResponse2("A week ago", aWeekAgo)
)
