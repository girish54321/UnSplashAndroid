package com.example.unsplashandroid.modal

import com.google.gson.annotations.SerializedName

data class CategoryModal(
    @SerializedName("id") var id: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("published_at") var publishedAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("starts_at") var startsAt: String? = null,
    @SerializedName("ends_at") var endsAt: String? = null,
    @SerializedName("only_submissions_after") var onlySubmissionsAfter: String? = null,
    @SerializedName("visibility") var visibility: String? = null,
    @SerializedName("featured") var featured: Boolean? = null,
    @SerializedName("total_photos") var totalPhotos: Int? = null,
    @SerializedName("current_user_contributions") var currentUserContributions: ArrayList<String> = arrayListOf(),
    @SerializedName("total_current_user_submissions") var totalCurrentUserSubmissions: String? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("status") var status: String? = null,
    @SerializedName("owners") var owners: ArrayList<Owners> = arrayListOf(),
    @SerializedName("cover_photo") var coverPhoto: CoverPhoto? = CoverPhoto(),
    @SerializedName("preview_photos") var previewPhotos: ArrayList<PreviewPhotos> = arrayListOf()
)

data class Owners(
    @SerializedName("id") var id: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("twitter_username") var twitterUsername: String? = null,
    @SerializedName("portfolio_url") var portfolioUrl: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("profile_image") var profileImage: ProfileImage? = ProfileImage(),
    @SerializedName("instagram_username") var instagramUsername: String? = null,
    @SerializedName("total_collections") var totalCollections: Int? = null,
    @SerializedName("total_likes") var totalLikes: Int? = null,
    @SerializedName("total_photos") var totalPhotos: Int? = null,
    @SerializedName("accepted_tos") var acceptedTos: Boolean? = null,
    @SerializedName("for_hire") var forHire: Boolean? = null,
    @SerializedName("social") var social: Social? = Social()
)

data class CoverPhoto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("promoted_at") var promotedAt: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("blur_hash") var blurHash: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("alt_description") var altDescription: String? = null,
    @SerializedName("urls") var urls: Urls? = Urls(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("likes") var likes: Int? = null,
    @SerializedName("liked_by_user") var likedByUser: Boolean? = null,
    @SerializedName("current_user_collections") var currentUserCollections: ArrayList<String> = arrayListOf(),
    @SerializedName("sponsorship") var sponsorship: String? = null,
    @SerializedName("topic_submissions") var topicSubmissions: TopicSubmissions? = TopicSubmissions(),
    @SerializedName("user") var user: User? = User()
)

data class TopicSubmissions(
    @SerializedName("experimental") var experimental: Experimental? = Experimental()
)

data class Experimental(
    @SerializedName("status") var status: String? = null,
    @SerializedName("approved_on") var approvedOn: String? = null
)

data class PreviewPhotos(
    @SerializedName("id") var id: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("blur_hash") var blurHash: String? = null,
    @SerializedName("urls") var urls: Urls? = Urls()
)