package com.steemapp.lokisveil.steemapp.Fragments


/*import `in`.uncod.android.bypass.Bypass*/
import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spannable
import android.text.format.DateUtils
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.text.format.DateUtils.WEEK_IN_MILLIS
import android.text.method.LinkMovementMethod
import android.text.method.Touch.onTouchEvent
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewFragment
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import br.tiagohm.markdownview.css.styles.Bootstrap
import br.tiagohm.markdownview.css.styles.Github
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.commonsware.cwac.anddown.AndDown
import com.commonsware.cwac.anddown.AndDown.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.steemapp.lokisveil.steemapp.*
import com.steemapp.lokisveil.steemapp.DataHolders.FeedArticleDataHolder
import com.steemapp.lokisveil.steemapp.Enums.AdapterToUseFor
import com.steemapp.lokisveil.steemapp.Enums.FollowInternal
import com.steemapp.lokisveil.steemapp.Enums.TypeOfRequest
import com.steemapp.lokisveil.steemapp.HelperClasses.*
import com.steemapp.lokisveil.steemapp.Interfaces.ArticleActivityInterface
import com.steemapp.lokisveil.steemapp.Interfaces.GlobalInterface
import com.steemapp.lokisveil.steemapp.Interfaces.WebAppInterface
import com.steemapp.lokisveil.steemapp.MyViewHolders.ArticleViewHolder

import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Enums.MyOperationTypes
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Models.AccountName
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Models.Permlink
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.CustomJsonOperation
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.Operation
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.ReblogOperation
import com.steemapp.lokisveil.steemapp.SteemBackend.Config.Operations.VoteOperation
import com.steemapp.lokisveil.steemapp.jsonclasses.Block
import com.steemapp.lokisveil.steemapp.jsonclasses.feed
import com.steemapp.lokisveil.steemapp.jsonclasses.prof
import kotlinx.android.synthetic.main.article_preview.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.Period
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ArticleFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() , GlobalInterface {
    override fun notifyRequestMadeSuccess() {

    }

    override fun notifyRequestMadeError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getObjectMine(): Any {
        return holder?.article!!
    }

    override fun getContextMine(): Context {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getActivityMine(): Activity {
        return getActivity() as Activity
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var activity: Context? = null
    internal var view: View? = null
    private var loading = false
    var username : String? = null
    var key : String? = null
    var holder : ArticleViewHolder? = null
    //var webview : WebView? = null
    var swipe : swipecommonactionsclass? = null
    private var articleActivityInterface : ArticleActivityInterface? = null
    var univBody:String?=null
    var metrics : DisplayMetrics = DisplayMetrics()
    var texcolormine : Int? = 0
    var cardbackground : Int? = 0

    private var adapter: AllRecyclerViewAdapter? = null

    //internal var swipeRefreshLayout: SwipeRefreshLayout? = null
    internal var recyclerView: RecyclerView? = null

    //private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if(savedInstanceState?.getString("body") != null){
            univBody = savedInstanceState?.getString("body")
            setWebView(univBody as String)
        }*/
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        //view = inflater!!.inflate(R.layout.fragment_article, container, false)
        view = inflater!!.inflate(R.layout.fragment_article, container, false)
        
        /*if(view == null){
            view = inflater!!.inflate(R.layout.fragment_article, container, false)
            recyclerView = view?.findViewById(R.id.list)
            adapter = AllRecyclerViewAdapter(getActivity() as FragmentActivity, ArrayList(), recyclerView as RecyclerView, view as View, AdapterToUseFor.article,this)
            //adapter?.setEmptyView(view?.findViewById(R.id.toDoEmptyView))

            recyclerView?.setItemAnimator(DefaultItemAnimator())
            recyclerView?.setAdapter(adapter)
        }*/
        var attrs  = intArrayOf(R.attr.textColorMine,R.attr.cardBackgroundColor)
        var ta = context?.obtainStyledAttributes(attrs)
        texcolormine  = ta?.getResourceId(0, android.R.color.black)
        cardbackground = ta?.getResourceId(attrs.size - 1,android.R.color.white)

        ta?.recycle()
        texcolormine = ContextCompat.getColor(context!!,texcolormine!!)
        cardbackground = ContextCompat.getColor(context!!,cardbackground!!)
        //val hexColor = String.format("#%06X", 0xFFFFFF and texcol)
        activity = getActivity()?.applicationContext
        val sharedPreferences = context?.getSharedPreferences(CentralConstants.sharedprefname, 0)
        username = sharedPreferences?.getString(CentralConstants.username, null)
        key = sharedPreferences?.getString(CentralConstants.key, null)
        holder = ArticleViewHolder(view as View)
        //webview = view?.findViewById(R.id.article_webview)
        //holder.shareTextView = view?.findViewById(R.id.shareTextView) as TextView
        //GetView()
        //var swiplay =

        var sl = view?.findViewById<SwipeRefreshLayout>(R.id.activity_feed_swipe_refresh_layout) as SwipeRefreshLayout
        swipe = swipecommonactionsclass(sl)

        swipe?.makeswiperun()
        sl?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            articleActivityInterface?.ReloadData()
        })
        metrics = DisplayMetrics()
        getActivity()?.windowManager?.defaultDisplay?.getMetrics(metrics)
        return view
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (articleActivityInterface != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }*/

    override fun onStop() {
        super.onStop()
        //webview = null
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //webview = view?.findViewById(R.id.article_webview)
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("body",univBody)
    }*/



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ArticleActivityInterface) {
            articleActivityInterface = context
        } else {
            //throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        articleActivityInterface = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArticleFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ArticleFragment {
            val fragment = ArticleFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    fun clear(){
        adapter?.clear()
    }

    fun displayMessage(result: FeedArticleDataHolder.CommentHolder) {

        adapter?.add(result)

    }
    fun displayMessage(result: List<FeedArticleDataHolder.CommentHolder>) {


        loading = false

        for (a in result){

            displayMessage(a)
        }
        swipe?.makeswipestop()
        //adapter.questionListFunctions.add(questionsList)

    }


    fun fabclicked(fragmentManager: android.support.v4.app.FragmentManager){
        var mod = ModalBottomSheetMy()
        mod.show(fragmentManager, "answer dialog")
        mod.showsDialog = true
        mod.setUsername(username)
        mod.setArticleViewHolder(holder?.article)
        mod.context = context
        mod.setMyOperationTypes(MyOperationTypes.comment)
        //mod.makeCommentRequest()
    }

    fun sameasbind(holder : ArticleViewHolder){
        //val holder : ArticleViewHolder = mholder as ArticleViewHolder
        //holder.article = adaptedcomms.getObject(position) as FeedArticleDataHolder.FeedArticleHolder
        var pay = holder.article?.pending_payout_value
        if((holder.article?.already_paid.orEmpty() != "0.000 SBD")){
            pay = holder.article?.already_paid
        }


        holder.article_comments?.text = holder.article?.children.toString()

        if(holder.article?.uservoted != null && holder.article?.uservoted as Boolean == true){
            holder.article_likes?.setTextColor(ContextCompat.getColor(activity as Context, R.color.colorAccent))
            holder.article_likes?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_up_likeint_24px,0,0,0)
        }

        holder.article_likes?.text = holder.article?.netVotes.toString()
        //holder.article_name?.text = holder.article?.author
        holder.article_name?.text = "${holder.article?.author} (${StaticMethodsMisc.CalculateRepScore(holder.article?.authorreputation)})"
        holder.article_payout?.text = pay
        //val d = Calendar.getInstance()
        //d.time = holder.article?.createdcon
        holder.article_date?.text = holder.article?.datespan
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.common_full_open_on_phone)
                //.error(R.drawable.error)
                .priority(Priority.HIGH)
                .circleCrop()
        Glide.with(activity as Context).load(CentralConstants.GetFeedImageUrl(holder.article?.author)).apply(options)
               // .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.article_pfp as ImageView)


        if(holder.article?.reblogBy == null || holder.article?.reblogBy?.isEmpty() as Boolean){
            holder.article_resteemed_by?.visibility = View.GONE
        }
        else{
            holder.article_resteemed_by?.visibility = View.VISIBLE
            holder.article_resteemed_by?.text = "by "+ holder.article?.reblogBy?.get(0)
            holder.article_resteemed_by?.setOnClickListener(View.OnClickListener {
                val i = Intent(context, OpenOtherGuyBlog::class.java)
                i.putExtra(CentralConstants.OtherGuyNamePasser,holder.article?.reblogBy?.get(0))
                context?.startActivity(i)
            })
        }
        holder.article_tag?.text = "in "+ holder.article?.category
        holder.article_tag?.setOnClickListener(View.OnClickListener {
            var it = Intent(context,MainTags::class.java)
            it.putExtra(CentralConstants.MainRequest,"get_discussions_by_trending")
            it.putExtra(CentralConstants.MainTag,holder.article?.category)
            it.putExtra(CentralConstants.OriginalRequest,"trending")
            context?.startActivity(it)

        })
        holder.article_title?.text = holder.article?.title
        val and = AndDown()
        //val s : String = and.markdownToHtml(holder.article?.body, AndDown.HOEDOWN_EXT_QUOTE, 0)
        var bod = StaticMethodsMisc.CorrectMarkDown(holder.article?.body,holder.article?.image)
        /*bod = StaticMethodsMisc.CorrectBeforeMainLinks(bod,holder.article?.links)
        bod = StaticMethodsMisc.CorrectMarkDownUsers(bod,holder.article?.users)*/
        //bod = StaticMethodsMisc.CorrectMarkDownUsers(bod,holder.article?.users)
        /*if(holder.article?.image != null){
            for(img in holder.article?.image as List<String>){
                val check = "($img)"
                if(bod != null && !(bod?.contains(check))){
                    val reps = "![name]$check"
                    bod = bod?.replace(img,reps)
                }
            }
        }*/

        //var s : String = and.markdownToHtml(holder.article?.body, AndDown.HOEDOWN_EXT_AUTOLINK, 0)
        var s : String = ""
        //var s : String = bod
        if(holder.article?.format == "html"){
            s = bod
        }
        else{
            /*val flavour = CommonMarkFlavourDescriptor()
            val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(bod)
            s = HtmlGenerator(bod, parsedTree, flavour).generateHtml()*/
            s  = and.markdownToHtml(bod, AndDown.HOEDOWN_EXT_AUTOLINK, 0)
        }

        //s = StaticMethodsMisc.CorrectAfterMainImages(s)
        //s = StaticMethodsMisc.CorrectBeforeMainLinks(s,holder.article?.links)
        s = StaticMethodsMisc.CorrectMarkDownUsers(s,holder.article?.users)
        s = StaticMethodsMisc.CorrectBr(s)

        //s = StaticMethodsMisc.CorrectNewLine(s)
        /*s += "<style>*{max-width:100%}</style>"
        s += "<script type=\"text/javascript\">\n" +
                "    function UserClicked(user) {\n" +
                "        Android.UserClicked(user);\n" +
                "    }\n" +
                "</script>"*/
        /*if(holder.article?.image != null){
            for(img in holder.article?.image as List<String>){
                val check = "src=\"$img\""
                if(!s.contains(check)){
                    val reps = "<img $check />"
                    s.replace(img,reps)
                }
            }
        }*/


        holder.article_name?.setOnClickListener(View.OnClickListener {
            val i = Intent(context, OpenOtherGuyBlog::class.java)
            i.putExtra(CentralConstants.OtherGuyNamePasser,holder.article?.author)
            context?.startActivity(i)
        })

        holder.article_like?.setOnClickListener(View.OnClickListener {
            // Toast.makeText(con,"Processing. Please wait....",Toast.LENGTH_LONG).show()
            //ForReturningQuestionsLite q = item;
            var articles = holder.article as FeedArticleDataHolder.FeedArticleHolder
            //var s : SteemJ = SteemJ()
            //s.vote(AccountName(holder.article?.author), Permlink(holder.article?.permlink),10000)
            var vop : VoteOperation = VoteOperation(AccountName(username), AccountName(articles.author), Permlink(articles.permlink))
            vop.weight = 100

            val weight = VoteWeightThenVote(context as Context,getActivity() as FragmentActivity,vop,null, 0,holder.progressbar,null)
            weight.makeDialog()
            /*globallist.add(bloc)

            Log.d("buttonclick",globallist.toString())*/
            //GetDynamicGlobalProperties(mholder.article as FeedArticleDataHolder.FeedArticleHolder)

        })

        holder.article_reblog_now?.setOnClickListener(View.OnClickListener {
            //Toast.makeText(con,"Processing. Please wait....",Toast.LENGTH_LONG).show()
            //ForReturningQuestionsLite q = item;
            var articles = holder.article as FeedArticleDataHolder.FeedArticleHolder
            var vop = ReblogOperation(AccountName(username), AccountName(articles.author), Permlink(articles.permlink))
            var al = ArrayList<AccountName>()
            al.add(AccountName(username))
            var cus = CustomJsonOperation(null,al,"follow",vop.toJson())
            var obs = Response.Listener<JSONObject> { response ->
                //var articles = mholder.article as FeedArticleDataHolder.FeedArticleHolder
                val gson = Gson()
                var ress = gson.fromJson<Block.BlockAdded>(response.toString(), Block.BlockAdded::class.java)
                if(ress != null && ress.result != null ){
                    /*Runnable { run { Toast.makeText(con,"$name has upvoted ${vop.author.name}",Toast.LENGTH_LONG).show() } }*/
                    Toast.makeText(context,"Reblogged ${vop.permlink.link}", Toast.LENGTH_LONG).show()

                }
            }
            var list  = ArrayList<Operation>()
            list.add(cus)
            //var bloc = GetDynamicAndBlock(con ,adaptedcomms,position,mholder,null,cus,obs,"Reblogged ${vop.permlink.link}",MyOperationTypes.reblog)
            var bloc = GetDynamicAndBlock(context as Context ,null, 0,list,"Reblogged ${vop.permlink.link}",MyOperationTypes.reblog,holder.progressbar,null)
            bloc.GetDynamicGlobalProperties()
            /*globallist.add(bloc)
            Log.d("buttonclick",globallist.toString())*/
            /*val myIntent = Intent(con, ArticleActivity::class.java)
            myIntent.putExtra("username", holder.article?.author)
            myIntent.putExtra("tag", holder.article?.category)
            myIntent.putExtra("permlink", holder.article?.permlink)
            con.startActivity(myIntent)*/
        })

        //setWebView(s)

        s += "<script type=\"text/javascript\">\n" +
                "var contents = document.getElementsByClassName(\"mylink\");\n" +
                "for(var i = 0; i < contents.length; i++){\n"+
                "    contents[i].addEventListener(\"click\", ContentClick, false);}\n" +
                "function ContentClick(event) {\n"+
                "console.log(event.defaultPrevented);\n" +
                "      if(event.target.href.search(\"steemer\") != -1){\n" +
                "       Android.UserClicked(event.target.href.split(\"@\")[1]);\n event.preventDefault(); }\n" +
                "      if(event.target.href.search(\"https://steemit.com\") != -1 || event.target.href.search(\"https://busy.org\") != -1){\n"+
                "            event.preventDefault();Android.LinkClicked(event.target.href);}}\n" +
                /*" \$(document).ready(function(){\n" +
                "    \$(\".mylink\").click(function(event){\n" +
                "        console.log(this.href);\n" +
                "        console.log(this.href);\n" +
                "console.log(this.href.toString().search(\"steemer\"))\n" +
                "        if(this.href.toString().search(\"steemer\" != -1)){\n" +
                //"console.log(Android);\n" +
                "                  event.preventDefault();  console.log(this.href);\n Android.UserClicked(this.href.split(\"@\")[1]);\n    }\n" +
                "         if(this.href.toString().includes(\"https://steemit.com\") || this.href.toString().includes(\"https://busy.org\")){\n" +
                "                            event.preventDefault();Android.LinkClicked(user);}\n" +

                "    });\n" +
                "});" +*/
                "    function UserClickedK(user) {\n" +
                "         Android.UserClicked(user);\n" +
                "    }\n" +
                "</script>"
        //s += "<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/MathJax.js?config=TeX-MML-AM_CHTML' async></script>"
        //var css =  Github()
        var css =  Github()
        //css.addFontFace("MyFont", "condensed", "italic", "bold", "url('myfont.ttf')");
        /*css.addMedia("screen and (min-width: 1281px)");
        css.addRule("h1", "color: orange");
        css.endMedia();*/
        var texcolin = String.format("#%06X", 0xFFFFFF and texcolormine!!)
        var cardcolin = String.format("#%06X", 0xFFFFFF and cardbackground!!)
        css.addRule("body", "padding: 0 !important")
        css.addRule("*", "color:$texcolin","background:$cardcolin")
        //"sub", "position: relative", "font-size: 75%", "line-height: 0", "vertical-align: baseline", "bottom: -0.25em"
        //css.addRule("sub","vertical-align: sub","font-size: smaller","bottom:-0.5em")
        css.addRule("sub","bottom:-1em","line-height: 1")
        holder.markdownView.addStyleSheet( css)
        holder.markdownView.addJavascriptInterface(WebAppInterface(articleActivityInterface), "Android")
        holder.markdownView.loadMarkdown(s)


        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
           // tvDocument.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY))
            //holder.article_summary?.text = Html.fromHtml(s,Html.FROM_HTML_MODE_LEGACY)
            webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
        } else {
            //tvDocument.setText(Html.fromHtml(bodyData))
            //holder.article_summary?.text = Html.fromHtml(s)
            webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
        }
        webview?.addJavascriptInterface( WebAppInterface(articleActivityInterface), "Android")

        //webview?.settings?.setUseWideViewPort(true)
        //webview?.settings?.setLoadWithOverviewMode(true)
        webview?.settings?.javaScriptEnabled = true*/
        //webview?.addJavascriptInterface()

        /*val bypess = Bypass(context)

        val str = bypess.markdownToSpannable(holder.article?.body)
        holder.article_summary?.text = str
        holder.article_summary?.movementMethod = LinkMovementMethod.getInstance()*/

        //holder.article_summary?.text = and.markdownToHtml(holder.article?.body, AndDown.HOEDOWN_EXT_QUOTE, 0)
        //holder.article_summary_markdown.showMarkdown(holder.article?.body)
        /*Glide.with(activity).load(holder.article?.image?.get(0))
                .placeholder(R.drawable.common_full_open_on_phone)
                .into(holder.article_image)*/
    }

    fun setWebView(s:String){
        holder?.openarticle?.removeAllViews()
        univBody = s
        var objects = StaticMethodsMisc.ConvertTextToList(s,holder?.article?.image)
        if(objects != null){
            val lparams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            //var bypass =  Bypass(context)
            for(x in objects){
                if(x is String && context != null){
                    var tx = TextView(context)
                    tx.setTextColor(ContextCompat.getColor(context as Context,R.color.black))
                    lparams.setMargins(GetPx(5f),0,GetPx(5f),0)
                    tx.layoutParams = lparams
                    /*tx.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.result_font))*/
                    tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
                    //tx.setSingleLine(false)
                    /*var webview = WebView(context)
                    webview.layoutParams = lparams*/

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                        //var string = bypass.markdownToSpannable(x)
                        // tvDocument.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY))
                        //tx.text = string
                        tx.text = Html.fromHtml(x,Html.FROM_HTML_MODE_LEGACY,null,MyLiTagHandler())

                        //webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
                    } else {
                        //var string = bypass.markdownToSpannable(x)
                        //tvDocument.setText(Html.fromHtml(bodyData))
                        //tx.text = string
                        tx.text = Html.fromHtml(x,null,MyLiTagHandler())

                        //webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
                    }
                    tx.movementMethod = LinkMovementMethod()
                    holder?.openarticle?.addView(tx)
                    //holder?.openarticle?.addView(webview)
                }
                else if(x is Int && context != null){

                    var iamge = ImageView(context)
                    iamge.layoutParams = lparams
                    iamge.adjustViewBounds = true
                    val options = RequestOptions()

                            .placeholder(R.drawable.ic_all_inclusive_black_24px)
                            //.error(R.drawable.error)
                            .priority(Priority.HIGH)
                    holder?.openarticle?.addView(iamge)
                    var url = holder?.article?.image!![x]
                    Glide.with(context as Context).load(url).apply(options)
                            // .placeholder(R.drawable.common_full_open_on_phone)
                            .into(iamge)

                }
            }
        }
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // tvDocument.setText(Html.fromHtml(bodyData,Html.FROM_HTML_MODE_LEGACY))
            //holder.article_summary?.text = Html.fromHtml(s,Html.FROM_HTML_MODE_LEGACY)
            webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
        } else {
            //tvDocument.setText(Html.fromHtml(bodyData))
            //holder.article_summary?.text = Html.fromHtml(s)
            webview?.loadDataWithBaseURL("",s,null,"UTF-8",null)
        }
        webview?.addJavascriptInterface( WebAppInterface(articleActivityInterface), "Android")*/

        //webview?.settings?.setUseWideViewPort(true)
        //webview?.settings?.setLoadWithOverviewMode(true)
        //webview?.settings?.javaScriptEnabled = true
    }


    fun SetDisplayMetrics(displayMetrics: DisplayMetrics){

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

    /*fun displayMessage(result: feed.Comment) {
        //swipecommonactionsclass?.makeswipestop()
        //adapter.questionListFunctions.add(message)
        swipe?.makeswipestop()
        val gson = Gson()

        var voted = false

        if(result.active_voted != null){

            for(x in result.active_voted){

                if(x.voter.equals(username)) voted = true
            }
        }


        if(result.jsonMetadataString != null && result.jsonMetadataString != ""){
            result.jsonMetadata = gson.fromJson<feed.JsonMetadataInner>(result.jsonMetadataString, feed.JsonMetadataInner::class.java)
        }
        //result.jsonMetadata = gson.fromJson<feed.JsonMetadataInner>(result.jsonMetadataString, feed.JsonMetadataInner::class.java)
        var d = calendarcalculations() //2018-02-03T13:58:18
        //var du = DateUtils.getRelativeDateTimeString(context,Date().time,(SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(result.created) ).time,)
        var du = DateUtils.getRelativeDateTimeString(context,(SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(result.created)).time,SECOND_IN_MILLIS,WEEK_IN_MILLIS,0)


        d.setDateOfTheData((SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(result.created) ))

        var fd : FeedArticleDataHolder.FeedArticleHolder = FeedArticleDataHolder.FeedArticleHolder(
                reblogBy = result.reblogBy,
                reblogOn = result.reblogOn,
                entryId = result.entryId,
                active =  result.active,
                author = result.author,
                body = result.body,
                cashoutTime = result.cashoutTime,
                category = result.category,
                children = result.children,
                created = result.created,
                createdcon = d.getDateTimeString(),
                depth = result.depth,
                id = result.id,
                lastPayout = result.lastPayout,
                lastUpdate = result.lastUpdate,
                netVotes = result.netVotes,
                permlink = result.permlink,
                rootComment = result.rootComment,
                title = result.title,
                format = result.jsonMetadata?.format,
                app = result.jsonMetadata?.app,
                image = result.jsonMetadata?.image,
                links = result.jsonMetadata?.links,
                tags = result.jsonMetadata?.tags,
                users = result.jsonMetadata?.users,
                authorreputation = result.authorreputation,
                pending_payout_value = result.pending_payout_value,
                promoted = result.promoted,
                total_pending_payout_value = result.total_pending_payout_value,
                uservoted = voted,
                already_paid = result.totalPayoutValue,
                summary = null,
                datespan = du.toString()
        )
        //adapter?.feedHelperFunctions?.add(fd)

        if(holder != null){
            holder?.article = fd
            sameasbind(holder as ArticleViewHolder)
            val articlepop = ArticlePopUpMenu(context as Context,holder?.shareTextView as View,"${CentralConstants.baseUrlView}@${holder?.article?.author}","${CentralConstants.baseUrl}${holder?.article?.category}/@${holder?.article?.author}/${holder?.article?.permlink}",holder?.article?.useFollow,username,holder?.article?.author,null,0,holder?.progressbar,this)

        }


    }*/
    fun displayMessage(result: FeedArticleDataHolder.FeedArticleHolder) {
        //swipecommonactionsclass?.makeswipestop()
        //adapter.questionListFunctions.add(message)
        swipe?.makeswipestop()

        var con = FollowApiConstants.getInstance()


        if(holder != null && context != null){
            holder?.article = result
            if(!con.following.isEmpty() && con.following.any { p -> p.following == holder?.article?.author }){
                holder?.article?.useFollow = MyOperationTypes.unfollow
            }
            else {
                holder?.article?.useFollow = MyOperationTypes.follow
            }
            sameasbind(holder as ArticleViewHolder)
            val articlepop = ArticlePopUpMenu(context as Context,holder?.shareTextView as View,"${CentralConstants.baseUrlView}@${holder?.article?.author}","${CentralConstants.baseUrlView}${holder?.article?.category}/@${holder?.article?.author}/${holder?.article?.permlink}",holder?.article?.useFollow,username,holder?.article?.author,null,0,holder?.progressbar,this,holder?.article?.activeVotes,true)

        }


    }










}// Required empty public constructor
