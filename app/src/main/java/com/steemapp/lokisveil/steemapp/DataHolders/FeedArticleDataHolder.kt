package com.steemapp.lokisveil.steemapp.DataHolders

import com.google.gson.annotations.SerializedName
import com.steemapp.lokisveil.steemapp.Enums.FollowInternal
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Enums.MyOperationTypes
import com.steemapp.lokisveil.steemapp.jsonclasses.feed
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by boot on 2/5/2018.
 */
class FeedArticleDataHolder {
/*     class MainHolder(
              reblogBy: List<String>?,
              reblogOn: String?, //2018-02-04T13:44:21
              entryId: Int?, //2363
              id: Int, //30142261
              author: String, //doghaus
              permlink: String, //street-view
              category: String, //poetry
              title: String, //Street View
              body: String, //![15999858_5.jpg](https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg)
              summary : String?,
             //al jsonMetadata: JsonMetadata, //{"tags":["poetry","writing","life","deceit","mystery"],"users":["doghaus"],"image":["https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg"],"links":["https://goo.gl/images/Jr17QZ"],"app":"steemit/0.1","format":"markdown"}
              lastUpdate: String, //2018-02-03T13:58:18
              created: String, //2018-02-03T13:58:18
              createdcon : String,
              active: String, //2018-02-04T13:50:12
              lastPayout: String, //1970-01-01T00:00:00
              depth: Int, //0
              children: Int, //7
              cashoutTime: String,
              netVotes: Int, //21
              rootComment: Int,

              tags: List<String>?,
              users: List<String>?,
              image: List<String>?,
              links: List<String>?,
              app: String?, //steemit/0.1
              format: String?, //markdown

              pending_payout_value : String?,
              total_pending_payout_value : String?,
              uservoted : Boolean?,
              authorreputation :String?,
              promoted : String?,
              already_paid : String?,
              width : Int = 0
     ) {
         var reblogBy: List<String>? = reblogBy
         var reblogOn: String? = reblogOn //2018-02-04T13:44:21
         var entryId: Int? = entryId //2363
         var id: Int = id //30142261
         var author: String = author //doghaus
         var permlink: String = permlink //street-view
         var category: String = category//poetry
         var title: String = title //Street View
         var body: String = body //![15999858_5.jpg](https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg)
         //var summary: String?
         //varal jsonMetadata: JsonMetadata, //{"tags":["poetry","writing","life","deceit","mystery"],"users":["doghaus"],"image":["https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg"],"links":["https://goo.gl/images/Jr17QZ"],"app":"steemit/0.1","format":"markdown"}
         var lastUpdate: String = lastUpdate //2018-02-03T13:58:18
         var created: String = created //2018-02-03T13:58:18
         var createdcon: String = createdcon
         var active: String = active //2018-02-04T13:50:12
         var lastPayout: String = lastPayout //1970-01-01T00:00:00
         var depth: Int = depth //0
         var children: Int = children //7
         var cashoutTime: String = cashoutTime
         var netVotes: Int = netVotes //21
         var rootComment: Int = rootComment

         var tags: List<String>? = tags
         var users: List<String>? = users
         var image: List<String>? = image
         var links: List<String>? = links
         var app: String? = app //steemit/0.1
         var format: String? = format //markdown

         var pending_payout_value: String? = pending_payout_value
         var total_pending_payout_value: String? = total_pending_payout_value
         var uservoted: Boolean? = uservoted
         var authorreputation: String? = authorreputation
         var promoted: String? = promoted
         var already_paid: String? = already_paid
         var width: Int = 0


     }*/
    data class FeedArticleHolder (
        val reblogBy: List<String>?,
        val reblogOn: String?, //2018-02-04T13:44:21
        val entryId: Int?, //2363
        val id: Int, //30142261
        val author: String, //doghaus
        val permlink: String, //street-view
        val category: String, //poetry
        val title: String, //Street View
        val body: String, //![15999858_5.jpg](https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg)
        var summary : String?,
           //val jsonMetadata: JsonMetadata, //{"tags":["poetry","writing","life","deceit","mystery"],"users":["doghaus"],"image":["https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg"],"links":["https://goo.gl/images/Jr17QZ"],"app":"steemit/0.1","format":"markdown"}
        val lastUpdate: String, //2018-02-03T13:58:18
        val created: String, //2018-02-03T13:58:18
        val createdcon : String,
        var datespan:String,
        val active: String, //2018-02-04T13:50:12
        val lastPayout: String, //1970-01-01T00:00:00
        val depth: Int, //0
        val children: Int, //7
        val cashoutTime: String,
        var netVotes: Int, //21
        val rootComment: Int,

        val tags: List<String>?,
        val users: List<String>?,
        val image: List<String>?,
        val links: List<String>?,
        val app: String?, //steemit/0.1
        val format: String?, //markdown

        val pending_payout_value : String?,
        val total_pending_payout_value : String?,
        var uservoted : Boolean?,
        val authorreputation :String?,
        val promoted : String?,
        val already_paid : String?,
        var width : Int = 0,
        var useFollow : MyOperationTypes = MyOperationTypes.unfollow,
        var replies : JSONArray? = null,
        var activeVotes: JSONArray? = null,
        var displayName:String = "",
        var rootAuthor: String? = null,
        var rootPermlink: String? = null
        )


    data class CommentHolder(
            val reblogBy: List<String>?,
            val reblogOn: String?, //2018-02-04T13:44:21
            val entryId: Int?, //2363
            val id: Int, //30142261
            val author: String, //doghaus
            val permlink: String, //street-view
            val category: String, //poetry
            val title: String, //Street View
            val body: String, //![15999858_5.jpg](https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg)
            //val jsonMetadata: JsonMetadata, //{"tags":["poetry","writing","life","deceit","mystery"],"users":["doghaus"],"image":["https://steemitimages.com/DQmTfZ5h3DEUuwgC4763Z7SwjuxbZ7KpKc5RWkE9ZVDLQ4o/15999858_5.jpg"],"links":["https://goo.gl/images/Jr17QZ"],"app":"steemit/0.1","format":"markdown"}
            val lastUpdate: String, //2018-02-03T13:58:18
            val created: String, //2018-02-03T13:58:18
            val createdcon : String,
            var datespan:String,
            val active: String, //2018-02-04T13:50:12
            val lastPayout: String, //1970-01-01T00:00:00
            val depth: Int, //0
            val children: Int, //7
            val cashoutTime: String,
            var netVotes: Int, //21
            val rootComment: Int,

            val tags: List<String>?,
            val users: List<String>?,
            val image: List<String>?,
            val links: List<String>?,
            val app: String?, //steemit/0.1
            val format: String?, //markdown

            val pending_payout_value : String?,
            val total_pending_payout_value : String?,
            var uservoted : Boolean?,
            val authorreputation :String?,
            val promoted : String?,
            val already_paid : String?,
            var width : Int = 0,
            var useFollow : MyOperationTypes = MyOperationTypes.follow,
            var replies : JSONArray? = null,
            var activeVotes: JSONArray? = null,
            var reply_to_above : Boolean = false,
            var displayName:String = "",

            var parent_permlink:String? = "",
            var parent_tag:String? ="",
            var paretn_author:String? = "",
            var rootAuthor: String?,
            var rootPermlink: String?,
            var highlightThis:Boolean = false
    )
}