package com.steemapp.lokisveil.steemapp.HelperClasses

import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import com.steemapp.lokisveil.steemapp.R

/**
 * Created by boot on 2/5/2018.
 */
class swipecommonactionsclass (swipeRefreshLayoutf: SwipeRefreshLayout) {
    private var swipeRefreshLayout: SwipeRefreshLayout = swipeRefreshLayoutf
    private var activity: Activity? = null

   init {
        //this.swipeRefreshLayout = swipeRefreshLayoutf


        AttachColourAndEvents()
    }


    private fun AttachColourAndEvents() {


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimary)
    }

    fun makeswiperun() {
        if (!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
        }
    }

    fun makeswipestop() {
        //swipeRefreshLayout.setRefreshing(false);
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        }
    }
}