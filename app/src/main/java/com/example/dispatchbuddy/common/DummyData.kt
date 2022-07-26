package com.example.dispatchbuddy.common

import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.*

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
        "2022-04-11T12:02:26 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-04-12T12:02:26 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-04-11T12:02:26 -01:00"
    )
    ,RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2015-10-05T12:09:21 -01:00"
    )
    ,RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2015-10-05T12:09:21 -01:00"
    ),RiderResponse(
        "cancelled",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2016-08-13T11:31:18 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2016-08-15T11:31:18 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2016-08-16T11:31:18 -01:00"
    ),RiderResponse(
        "cancelled",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2021-04-09T03:57:44 -01:00"
    ),RiderResponse(
        "pending",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2021-04-09T03:57:44 -01:00"
    )
)
val today = arrayListOf(
    RiderResponse(
        "ongoing",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2015-08-11"
    )
)
val yesterday = arrayListOf(
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2021-05-31T08:32:04 -01:00"
    ),RiderResponse(
        "cancelled",
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
        "2020-08-07T04:54:36 -01:00"
    )
    ,RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2015-10-05T12:09:21 -01:00"
    )
)
val todaySection = listOf(RiderSectionResponse2("Today", today))
val yesterdaySection = listOf(RiderSectionResponse2("Yesterday", yesterday))
val aWeekAgoSection = listOf(RiderSectionResponse2("A week ago", aWeekAgo))

val requestList = mutableListOf(
    RiderSectionResponse2("Today", today),
    RiderSectionResponse2("Yesterday", yesterday),
    RiderSectionResponse2("A week ago", aWeekAgo)
)
/**
 * DELIVERY DUMMY DATA SECTION
 * */
val dAWeekAgo = arrayListOf(
    DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    )
)
val dToday = arrayListOf(
    DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),
    DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),
)
val dYesterday = arrayListOf(
    DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    ),DeliveriesResponse(
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
    )
)
//val todaySection = listOf(RiderSectionResponse2("Today", today))
//val yesterdaySection = listOf(RiderSectionResponse2("Yesterday", yesterday))
//val aWeekAgoSection = listOf(RiderSectionResponse2("A week ago", aWeekAgo))

val deliveriesList = mutableListOf(
    DeliverySectionResponse("Today", dToday),
    DeliverySectionResponse("Yesterday", dYesterday),
    DeliverySectionResponse("A week ago", dAWeekAgo)
)
val dummyData = arrayListOf(
    // todaySection
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-07-26T08:32:04 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-07-26T08:32:04 -01:00"
    ),
    // yesterday
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-07-25T08:32:04 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-07-25T08:32:04 -01:00"
    ),
    // A week ago
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-07-19T08:32:04 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-07-19T08:32:04 -01:00"
    ),
    // a month ago
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-06-19T08:32:04 -01:00"
    ),
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-06-19T08:32:04 -01:00"
    ),
    RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-06-19T08:32:04 -01:00"
    ),RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-06-19T08:32:04 -01:00"
    ), RiderResponse(
        "completed",
        R.drawable.profile_avatar,
        "Emmanuel Alakere",
        "yenagoa, Bayelsa",
        "2.56kg",
        "N 4000",
        "2022-06-19T08:32:04 -01:00"
    ),

)
