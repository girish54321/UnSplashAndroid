package com.example.unsplashandroid.modal

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UnPlashResponse (

    @SerializedName("id"                       ) var id                     : String?           = null,
    @SerializedName("created_at"               ) var createdAt              : String?           = null,
    @SerializedName("updated_at"               ) var updatedAt              : String?           = null,
    @SerializedName("promoted_at"              ) var promotedAt             : String?           = null,
    @SerializedName("width"                    ) var width                  : Int?              = null,
    @SerializedName("height"                   ) var height                 : Int?              = null,
    @SerializedName("color"                    ) var color                  : String?           = null,
    @SerializedName("blur_hash"                ) var blurHash               : String?           = null,
    @SerializedName("description"              ) var description            : String?           = null,
    @SerializedName("alt_description"          ) var altDescription         : String?           = null,
    @SerializedName("urls"                     ) var urls                   : Urls?             = Urls(),
    @SerializedName("links"                    ) var links                  : Links?            = Links(),
    @SerializedName("likes"                    ) var likes                  : Int?              = null,
    @SerializedName("liked_by_user"            ) var likedByUser            : Boolean?          = null,
    @SerializedName("current_user_collections" ) var currentUserCollections : ArrayList<String> = arrayListOf(),
    @SerializedName("sponsorship"              ) var sponsorship            : Sponsorship?      = Sponsorship(),
//    @SerializedName("topic_submissions"        ) var topicSubmissions       : TopicSubmissions? = TopicSubmissions(),
    @SerializedName("user"                     ) var user                   : User?             = User()

): Serializable

data class Urls (

    @SerializedName("raw"      ) var raw     : String? = null,
    @SerializedName("full"     ) var full    : String? = null,
    @SerializedName("regular"  ) var regular : String? = null,
    @SerializedName("small"    ) var small   : String? = null,
    @SerializedName("thumb"    ) var thumb   : String? = null,
    @SerializedName("small_s3" ) var smallS3 : String? = null

): Serializable

data class Links (

    @SerializedName("self"      ) var self      : String? = null,
    @SerializedName("html"      ) var html      : String? = null,
    @SerializedName("photos"    ) var photos    : String? = null,
    @SerializedName("likes"     ) var likes     : String? = null,
    @SerializedName("portfolio" ) var portfolio : String? = null,
    @SerializedName("following" ) var following : String? = null,
    @SerializedName("followers" ) var followers : String? = null

): Serializable

data class ProfileImage (

    @SerializedName("small"  ) var small  : String? = null,
    @SerializedName("medium" ) var medium : String? = null,
    @SerializedName("large"  ) var large  : String? = null

): Serializable

data class Social (

    @SerializedName("instagram_username" ) var instagramUsername : String? = null,
    @SerializedName("portfolio_url"      ) var portfolioUrl      : String? = null,
    @SerializedName("twitter_username"   ) var twitterUsername   : String? = null,
    @SerializedName("paypal_email"       ) var paypalEmail       : String? = null

): Serializable


data class Sponsor (

    @SerializedName("id"                 ) var id                : String?       = null,
    @SerializedName("updated_at"         ) var updatedAt         : String?       = null,
    @SerializedName("username"           ) var username          : String?       = null,
    @SerializedName("name"               ) var name              : String?       = null,
    @SerializedName("first_name"         ) var firstName         : String?       = null,
    @SerializedName("last_name"          ) var lastName          : String?       = null,
    @SerializedName("twitter_username"   ) var twitterUsername   : String?       = null,
    @SerializedName("portfolio_url"      ) var portfolioUrl      : String?       = null,
    @SerializedName("bio"                ) var bio               : String?       = null,
    @SerializedName("location"           ) var location          : String?       = null,
    @SerializedName("links"              ) var links             : Links?        = Links(),
    @SerializedName("profile_image"      ) var profileImage      : ProfileImage? = ProfileImage(),
    @SerializedName("instagram_username" ) var instagramUsername : String?       = null,
    @SerializedName("total_collections"  ) var totalCollections  : Int?          = null,
    @SerializedName("total_likes"        ) var totalLikes        : Int?          = null,
    @SerializedName("total_photos"       ) var totalPhotos       : Int?          = null,
    @SerializedName("accepted_tos"       ) var acceptedTos       : Boolean?      = null,
    @SerializedName("for_hire"           ) var forHire           : Boolean?      = null,
    @SerializedName("social"             ) var social            : Social?       = Social()

): Serializable

data class Sponsorship (

    @SerializedName("impression_urls" ) var impressionUrls : ArrayList<String> = arrayListOf(),
    @SerializedName("tagline"         ) var tagline        : String?           = null,
    @SerializedName("tagline_url"     ) var taglineUrl     : String?           = null,
    @SerializedName("sponsor"         ) var sponsor        : Sponsor?          = Sponsor()

): Serializable




data class User (

    @SerializedName("id"                 ) var id                : String?       = null,
    @SerializedName("updated_at"         ) var updatedAt         : String?       = null,
    @SerializedName("username"           ) var username          : String?       = null,
    @SerializedName("name"               ) var name              : String?       = null,
    @SerializedName("first_name"         ) var firstName         : String?       = null,
    @SerializedName("last_name"          ) var lastName          : String?       = null,
    @SerializedName("twitter_username"   ) var twitterUsername   : String?       = null,
    @SerializedName("portfolio_url"      ) var portfolioUrl      : String?       = null,
    @SerializedName("bio"                ) var bio               : String?       = null,
    @SerializedName("location"           ) var location          : String?       = null,
    @SerializedName("links"              ) var links             : Links?        = Links(),
    @SerializedName("profile_image"      ) var profileImage      : ProfileImage? = ProfileImage(),
    @SerializedName("instagram_username" ) var instagramUsername : String?       = null,
    @SerializedName("total_collections"  ) var totalCollections  : Int?          = null,
    @SerializedName("total_likes"        ) var totalLikes        : Int?          = null,
    @SerializedName("total_photos"       ) var totalPhotos       : Int?          = null,
    @SerializedName("accepted_tos"       ) var acceptedTos       : Boolean?      = null,
    @SerializedName("for_hire"           ) var forHire           : Boolean?      = null,
    @SerializedName("social"             ) var social            : Social?       = Social()

): Serializable