package com.steemapp.lokisveil.steemapp.BindHelpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.commonsware.cwac.anddown.AndDown
import com.google.gson.Gson
import com.steemapp.lokisveil.steemapp.*

import com.steemapp.lokisveil.steemapp.DataHolders.FeedArticleDataHolder
import com.steemapp.lokisveil.steemapp.Enums.FollowInternal
import com.steemapp.lokisveil.steemapp.HelperClasses.*
import com.steemapp.lokisveil.steemapp.Interfaces.arvdinterface
import com.steemapp.lokisveil.steemapp.MyViewHolders.CommentViewHolder
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Enums.MyOperationTypes
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Models.AccountName
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Models.Permlink
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.CustomJsonOperation
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.Operation
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.ReblogOperation
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.VoteOperation
import com.steemapp.lokisveil.steemapp.jsonclasses.Block
import org.json.JSONObject

/**
 * Created by boot on 2/16/2018.
 */
class CommentsHelperFunctions(context : Context,username:String?,adapter: AllRecyclerViewAdapter ,scale: Float,metrics: DisplayMetrics)  {

    val con: Context = context
    var name:String? = username
    val adaptedcomms: arvdinterface = adapter
    val and = AndDown()
    internal var scale: Float = scale
    internal var metrics: DisplayMetrics = metrics
    var selectedPos = -1
    init {
        if(name == null){
            val sharedpref : SharedPreferences = context.getSharedPreferences(CentralConstants.sharedprefname,0)
            name = sharedpref.getString(CentralConstants.username,null)
        }
    }

    fun GetPx(dp : Float) : Int{

        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp + 5,
                metrics
        ).toInt()
    }

    fun GetLayourParamsMargin(mar : Int) : LinearLayout.LayoutParams{
        var param : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.leftMargin = mar
        //param.setMargins(mar, 0, 5, 0)

        return param
    }





    public fun add(article : FeedArticleDataHolder.CommentHolder){
        adaptedcomms.add(article)
        //adapter.notifyDataSetChanged();
        adaptedcomms.notifyitemcinserted(adaptedcomms.getSize())
    }

    public fun add(articles:List<FeedArticleDataHolder.CommentHolder>){
        for (x in articles){
            add(x)
        }
    }




    fun Bind(mholder: RecyclerView.ViewHolder, position:Int){
        mholder.itemView.setSelected(selectedPos == position)
        var holder : CommentViewHolder = mholder as CommentViewHolder
        holder.article = adaptedcomms.getObject(position) as FeedArticleDataHolder.CommentHolder
        /*if(holder.article?.highlightThis as Boolean){
            mholder.itemView.isSelected = true
            adaptedcomms.notifyitemcchanged(position)
        }
        else {
            mholder.itemView.isSelected = false
        }*/

        var pay = holder.article?.pending_payout_value
        if((holder.article?.already_paid.orEmpty() != "0.000 SBD")){
            pay = holder.article?.already_paid
        }

        /*if(holder.article?.width != 0){

        }*/
        holder.cardviewchat?.layoutParams = GetLayourParamsMargin(GetPx(holder.article?.width?.toFloat() as Float))

        holder.article_comments?.text = holder.article?.children.toString()

        if(holder.article?.uservoted != null && holder.article?.uservoted as Boolean == true){
            holder.article_likes?.setTextColor(ContextCompat.getColor(con, R.color.colorAccent))
        }
        holder.article_likes?.text = holder.article?.netVotes.toString()
        //holder.article_name?.text = holder.article?.author
        //holder.article_name?.text = "${holder.article?.author} (${StaticMethodsMisc.CalculateRepScore(holder.article?.authorreputation)})"
        //holder.article_name?.text = "${holder.article?.author} (${holder.article?.authorreputation})"
        holder.article_name?.text = holder.article?.displayName
        holder.article_payout?.text = pay
        //val d = Calendar.getInstance()
        //d.time = holder.article?.createdcon
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_person_white_24px)
                //.error(R.drawable.error)
                .priority(Priority.HIGH)
                .circleCrop()
        //holder.article_date?.text = holder.article?.createdcon
        holder.article_date?.text = holder.article?.datespan
        Glide.with(con).load(CentralConstants.GetFeedImageUrl(holder.article?.author)).apply(options)
                //.placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.article_pfp as ImageView)


        if(holder.article?.reblogBy == null || holder.article?.reblogBy?.isEmpty() as Boolean){
            holder.article_resteemed_by?.visibility = View.GONE
        }
        else{
            holder.article_resteemed_by?.visibility = View.VISIBLE
            holder.article_resteemed_by?.text = "by "+ holder.article?.reblogBy?.get(0)
        }
        holder.article_tag?.text = "in "+ holder.article?.category
        holder.article_tag?.setOnClickListener(View.OnClickListener {
            var it = Intent(con,MainTags::class.java)
            it.putExtra(CentralConstants.MainRequest,"get_discussions_by_trending")
            it.putExtra(CentralConstants.MainTag,holder.article?.category)
            it.putExtra(CentralConstants.OriginalRequest,"trending")
            con.startActivity(it)

        })
        //holder.article_title?.text = holder.article?.title
        /*Glide.with(con).load(holder.article?.image?.get(0))
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.article_image)*/

        //var by : in.uncod.android.bypass =

        holder.article_name?.setOnClickListener(View.OnClickListener {
            val i = Intent(con, OpenOtherGuyBlog::class.java)
            i.putExtra(CentralConstants.OtherGuyNamePasser,holder.article?.author)
            con.startActivity(i)
        })

        mholder.mView.setOnClickListener(View.OnClickListener {
            //ForReturningQuestionsLite q = item;

            /*val myIntent = Intent(con, ArticleActivity::class.java)
            myIntent.putExtra("username", holder.article?.author)
            myIntent.putExtra("tag", holder.article?.category)
            myIntent.putExtra("permlink", holder.article?.permlink)
            con.startActivity(myIntent)*/
        })


        mholder.article_like?.setOnClickListener(View.OnClickListener {
            //Toast.makeText(con,"Processing. Please wait....", Toast.LENGTH_LONG).show()
            //ForReturningQuestionsLite q = item;
            var articles = mholder.article as FeedArticleDataHolder.CommentHolder
            //var s : SteemJ = SteemJ()
            //s.vote(AccountName(holder.article?.author), Permlink(holder.article?.permlink),10000)
            var vop : VoteOperation = VoteOperation(AccountName(name), AccountName(articles.author), Permlink(articles.permlink))
            vop.weight = 10000
            /*var obs = Response.Listener<JSONObject> { response ->
                //var articles = mholder.article as FeedArticleDataHolder.FeedArticleHolder
                val gson = Gson()
                var ress = gson.fromJson<Block.BlockAdded>(response.toString(), Block.BlockAdded::class.java)
                if(ress != null && ress.result != null ){
                    //con.run { Toast.makeText(con,"Upvoted ${vop.permlink}",Toast.LENGTH_LONG).show() }
                    Toast.makeText(con,"Upvoted ${vop.permlink}", Toast.LENGTH_LONG).show()
                    holder.article?.uservoted.to(true)
                    holder.article_likes?.setTextColor(ContextCompat.getColor(con, R.color.colorAccent))
                    adaptedcomms.notifyitemcchanged(mholder.adapterPosition)
                    //Runnable { run {  } }
                    //Toast.makeText(con,"$name has upvoted ${vop.author.name}",Toast.LENGTH_LONG).show()
                }
            }
            //var bloc = GetDynamicAndBlock(con ,adaptedcomms,position,mholder,null,vop,obs,"Upvoted ${vop.permlink.link}",MyOperationTypes.vote)
            var list  = ArrayList<Operation>()
            list.add(vop)*/
            /*var bloc = GetDynamicAndBlock(con ,adaptedcomms,position,list,"Upvoted ${vop.permlink.link}", MyOperationTypes.vote)
            bloc.GetDynamicGlobalProperties()*/
            //globallist.add(bloc)
            var we = VoteWeightThenVote(con,adaptedcomms.getActivity(),vop,adaptedcomms,position,holder.progressbar,null)
            we.makeDialog()
            //Log.d("buttonclick",globallist.toString())
            //GetDynamicGlobalProperties(mholder.article as FeedArticleDataHolder.FeedArticleHolder)

        })

        if(name == mholder.article?.author){
            mholder.article_edit?.visibility = View.VISIBLE
            mholder.article_edit?.setOnClickListener(View.OnClickListener {

                var mod = ModalBottomSheetMy()
                var hs = adaptedcomms.getActivity() as android.support.v4.app.FragmentActivity
                mod.show( hs.supportFragmentManager, "answer dialog")
                mod.showsDialog = true
                mod.setUsername(name)
                mod.setCommentViewHolder(holder?.article)
                mod.context = con
                mod.setInterface(adaptedcomms.GetGlobalInterface())
                mod.setMyOperationTypes(MyOperationTypes.edit_comment)
                mod.setEditStuff(mholder.article?.title,holder.article?.paretn_author,holder.article?.parent_tag,holder.article?.parent_permlink,holder.article?.body)

            })
        }
        else{
            mholder.article_edit?.visibility = View.GONE
        }

        mholder.article_reblog_now?.setOnClickListener(View.OnClickListener {

            var mod = ModalBottomSheetMy()
            var hs = adaptedcomms.getActivity() as android.support.v4.app.FragmentActivity
            mod.show( hs.supportFragmentManager, "answer dialog")
            mod.showsDialog = true
            mod.setUsername(name)
            mod.setCommentViewHolder(holder?.article)
            mod.context = con
            mod.setInterface(adaptedcomms.GetGlobalInterface())
            mod.setMyOperationTypes(MyOperationTypes.comment)
        })

        val articlepop = ArticlePopUpMenu(con,mholder.shareTextView,"${CentralConstants.baseUrlView}@${holder?.article?.author}","${CentralConstants.baseUrl}${holder?.article?.category}/@${holder?.article?.author}/${holder?.article?.permlink}",holder?.article?.useFollow,name,holder?.article?.author,adaptedcomms,position,holder?.progressbar,null,holder?.article?.activeVotes)


        var bod = holder.article?.body //StaticMethodsMisc.CorrectMarkDown(holder.article?.body,holder.article?.image)

        var s : String = ""
        if(holder.article?.format == "html"){
            s = if(bod != null) bod.toString() else ""
        }
        else{
            s  = and.markdownToHtml(bod, AndDown.HOEDOWN_EXT_AUTOLINK, AndDown.HOEDOWN_HTML_SKIP_HTML)
        }
        s = StaticMethodsMisc.CorrectAfterMainImages(s)
        s = StaticMethodsMisc.CorrectMarkDownUsers(s,holder.article?.users)
        s = StaticMethodsMisc.CorrectNewLine(s)
        //GenerateCommentViews(s,holder)
        holder?.openarticle?.removeAllViews()
        var objects = StaticMethodsMisc.ConvertTextToList(s,holder?.article?.image)
        if(objects.size > 0){
            val lparams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            for(x in objects){
                if(x is String){
                    var tx = TextView(con)
                    //tx.setTextColor(ContextCompat.getColor(con,R.color.black))
                    lparams.setMargins(GetPx(5f),0,GetPx(5f),0)
                    tx.layoutParams = lparams
                    tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0f)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        // tvDocument.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY))
                        tx.text = Html.fromHtml(x,Html.FROM_HTML_MODE_LEGACY,null, MyLiTagHandler())
                        //webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
                    } else {
                        //tvDocument.setText(Html.fromHtml(bodyData))
                        tx.text = Html.fromHtml(x,null,MyLiTagHandler())
                        //webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
                    }
                    holder?.openarticle?.addView(tx)
                }
                else if(x is Int){
                    var iamge = ImageView(con)
                    iamge.layoutParams = lparams
                    iamge.adjustViewBounds = true
                    val options = RequestOptions()

                            .placeholder(R.drawable.ic_all_inclusive_black_24px)
                            //.error(R.drawable.error)
                            .priority(Priority.HIGH)
                    holder?.openarticle?.addView(iamge)
                    var url = holder?.article?.image!![x]
                    Glide.with(con).load(url).apply(options)
                            // .placeholder(R.drawable.common_full_open_on_phone)
                            .into(iamge)

                }
            }
        }
        /*s += "<style>*{max-width:100%}</style>"
        s += "<script type=\"text/javascript\">\n" +
                "    function UserClicked(user) {\n" +
                "        Android.UserClicked(user);\n" +
                "    }\n" +
                "</script>"*/
        //and = AndDown()
        //val s : String = and.markdownToHtml(bod, AndDown.HOEDOWN_EXT_AUTOLINK, 0)
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // tvDocument.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY))
            //holder.article_summary?.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
            holder.article_webview?.loadDataWithBaseURL("",holder.article?.body,null,"UTF-8",null)
        } else {
            //tvDocument.setText(Html.fromHtml(bodyData))
           // holder.article_summary?.text = Html.fromHtml(s)
            holder.article_webview?.loadDataWithBaseURL("",holder.article?.body,null,"UTF-8",null)
        }*/
    }


    /*fun GenerateCommentViews(s : String,holder : CommentViewHolder){
        var objects = StaticMethodsMisc.ConvertTextToList(s,holder?.article?.image)
        if(objects != null){
            val lparams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            for(x in objects){
                if(x is String){
                    var tx = TextView(con)
                    tx.layoutParams = lparams
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        // tvDocument.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY))
                        tx.text = Html.fromHtml(x,Html.FROM_HTML_MODE_LEGACY)
                        //webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
                    } else {
                        //tvDocument.setText(Html.fromHtml(bodyData))
                        tx.text = Html.fromHtml(s)
                        //webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
                    }
                    holder?.openarticle?.addView(tx)
                }
                else if(x is Int){
                    var iamge = ImageView(con)
                    iamge.layoutParams = lparams
                    iamge.adjustViewBounds = true
                    val options = RequestOptions()

                            .placeholder(R.drawable.ic_all_inclusive_black_24px)
                            //.error(R.drawable.error)
                            .priority(Priority.HIGH)
                    holder?.openarticle?.addView(iamge)
                    var url = holder?.article?.image!![x]
                    Glide.with(con).load(CentralConstants.GetFeedImageUrl(url)).apply(options)
                            // .placeholder(R.drawable.common_full_open_on_phone)
                            .into(iamge)

                }
            }
        }
    }*/

}