package com.steemapp.lokisveil.steemapp.Fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.commonsware.cwac.anddown.AndDown
import com.google.gson.Gson
import com.steemapp.lokisveil.steemapp.AllRecyclerViewAdapter
import com.steemapp.lokisveil.steemapp.CentralConstants
import com.steemapp.lokisveil.steemapp.DataHolders.FeedArticleDataHolder
import com.steemapp.lokisveil.steemapp.Enums.AdapterToUseFor
import com.steemapp.lokisveil.steemapp.HelperClasses.StaticMethodsMisc
import com.steemapp.lokisveil.steemapp.HelperClasses.calendarcalculations
import com.steemapp.lokisveil.steemapp.HelperClasses.swipecommonactionsclass
import com.steemapp.lokisveil.steemapp.Interfaces.ArticleActivityInterface
import com.steemapp.lokisveil.steemapp.Interfaces.GlobalInterface

import com.steemapp.lokisveil.steemapp.R
import com.steemapp.lokisveil.steemapp.jsonclasses.feed
import java.text.SimpleDateFormat
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CommentsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment : Fragment(),GlobalInterface {
    override fun notifyRequestMadeSuccess() {
        articleActivityInterface?.ReloadData()
    }

    override fun notifyRequestMadeError() {

    }

    override fun getObjectMine(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContextMine(): Context {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getActivityMine(): Activity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    internal var swipecommonactionsclass: swipecommonactionsclass? = null

    private var fragmentActivity: android.support.v4.app.FragmentActivity? = null

    private var adapter: AllRecyclerViewAdapter? = null
    private var activity: Context? = null
    internal var view: View? = null
    internal var swipeRefreshLayout: SwipeRefreshLayout? = null
    internal var recyclerView: RecyclerView? = null
    private var loading = false
    internal var pastVisiblesItems: Int = 0
    internal var visibleItemCount:Int = 0
    internal var totalItemCount:Int = 0
    internal var tokenisrefreshingholdon = false
    internal var startwasjustrun = false
    private var articleActivityInterface : ArticleActivityInterface? = null
    var username : String? = null
    var key : String? = null
    val and = AndDown()
    internal var permlinkToFind = ""
    internal var scrollToPosition : Int? = 0

    //private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onStop() {
        super.onStop()
        //webview = null
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        if(view == null){
            view = inflater!!.inflate(R.layout.fragment_comments, container, false)
            recyclerView = view?.findViewById(R.id.feedFragment)
            adapter = AllRecyclerViewAdapter(getActivity() as FragmentActivity, ArrayList(), recyclerView as RecyclerView, view as View, AdapterToUseFor.comments,this)
            //adapter?.setEmptyView(view?.findViewById(R.id.toDoEmptyView))

            recyclerView?.setItemAnimator(DefaultItemAnimator())
            recyclerView?.setAdapter(adapter)
        }

        activity = getActivity()?.applicationContext
        swipeRefreshLayout = view?.findViewById<SwipeRefreshLayout>(R.id.activity_feed_swipe_refresh_layout) as SwipeRefreshLayout

        swipeRefreshLayout?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            articleActivityInterface?.ReloadData()
        })

        swipecommonactionsclass = swipecommonactionsclass(swipeRefreshLayout as SwipeRefreshLayout)
        fragmentActivity = getActivity()

        swipecommonactionsclass?.makeswiperun()
        val sharedPreferences = context?.getSharedPreferences(CentralConstants.sharedprefname, 0)
        username = sharedPreferences?.getString(CentralConstants.username, null)
        key = sharedPreferences?.getString(CentralConstants.key, null)

        return view
    }



/*    fun displayMessage(result: feed.Comment) {
        //swipecommonactionsclass?.makeswipestop()
        //adapter.questionListFunctions.add(message)

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


        var d = calendarcalculations() //2018-02-03T13:58:18
        d.setDateOfTheData((SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(result.created) ))
        var bod = StaticMethodsMisc.CorrectMarkDown(result.body,result.jsonMetadata?.image)
        //and = AndDown()
        var du = DateUtils.getRelativeDateTimeString(context,(SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(result.created)).time, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS,0)

        val s : String = and.markdownToHtml(bod, AndDown.HOEDOWN_EXT_AUTOLINK, 0)
        var fd : FeedArticleDataHolder.CommentHolder = FeedArticleDataHolder.CommentHolder(
                reblogBy = result.reblogBy,
                reblogOn = result.reblogOn,
                entryId = result.entryId,
                active =  result.active,
                author = result.author,
                body = s,
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
                width = result.width,
                datespan = du.toString()
        )
        adapter?.add(fd)

    }*/

    /*fun displayMessage(result: List<feed.Comment>) {


        loading = false

        for (a in result){

            displayMessage(a)
        }
        swipecommonactionsclass?.makeswipestop()
        //adapter.questionListFunctions.add(questionsList)

    }*/

    fun setPermlinkToFind(perm:String){
        permlinkToFind = perm
    }
    fun clear(){
        adapter?.clear()
    }

    fun displayMessage(result: FeedArticleDataHolder.CommentHolder) {
        var bo = false
        if(permlinkToFind != null){
            if(result.permlink == permlinkToFind){

                result.highlightThis = true
                scrollToPosition = adapter?.getSize()
                scrollToPosition?.plus(1)
                bo = true
                adapter?.commentHelperFunctions?.selectedPos = if(scrollToPosition != null) scrollToPosition as Int else -1

            }
        }
        adapter?.add(result)
        if( bo ){
            recyclerView?.smoothScrollToPosition(if(scrollToPosition != null) scrollToPosition as Int else 0)
        }



    }
    fun displayMessage(result: List<FeedArticleDataHolder.CommentHolder>) {


        loading = false

        for (a in result){

            displayMessage(a)
        }
        swipecommonactionsclass?.makeswipestop()
        //adapter.questionListFunctions.add(questionsList)

    }















    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (articleActivityInterface != null) {
            //mListener!!.onFragmentInteraction(uri)
        }
    }

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
         * @return A new instance of fragment CommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CommentsFragment {
            val fragment = CommentsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
