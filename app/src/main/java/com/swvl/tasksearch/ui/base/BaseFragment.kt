package com.swvl.tasksearch.ui.base


import com.swvl.tasksearch.utils.NetworkUtil
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.andrognito.flashbar.Flashbar
import com.swvl.tasksearch.R

open class BaseFragment : Fragment() {

    companion object {

    }

    lateinit var contentContainer: FrameLayout
    lateinit var loadingLayout: ProgressBar
    lateinit var mContext: Context
    private var networkFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parentView = inflater.inflate(R.layout.fragment_base, container, false)
        // content layout to show child fragment's layout
        contentContainer = parentView.findViewById(R.id.layout_container)
        loadingLayout = parentView.findViewById(R.id.loadingScreen)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = view.context
    }

    override fun onResume() {
        super.onResume()
        registerBroadcasts()
        internetCheckProcess()
    }

    override fun onPause() {
        unregisterBroadcasts()
        super.onPause()
    }

    fun playLoadingAnimation() {
        loadingLayout.visibility = View.VISIBLE
    }

    fun pauseLoadingAnimation() {
        loadingLayout.visibility = View.INVISIBLE
    }

    open fun internetCheckProcess() {
        if (NetworkUtil.isNetworkAvailable(mContext) && !networkFlag) {
            //Toast.makeText(mContext, "Network is Available", Toast.LENGTH_SHORT).show()
            activity?.let {
                Flashbar.Builder(it).gravity(Flashbar.Gravity.TOP).duration(1000)
                    .backgroundColorRes(R.color.colorAccent)
                    .message("Network is Available").build().show()
            }
            networkFlag = true
        }
        if (!NetworkUtil.isNetworkAvailable(mContext) && networkFlag) {
            //Toast.makeText(mContext, "Network is not Available", Toast.LENGTH_SHORT).show()
            activity?.let {
                Flashbar.Builder(it).gravity(Flashbar.Gravity.TOP).duration(1000)
                    .backgroundColorRes(R.color.colorPrimary)
                    .message("Network is not Available").build().show()
            }
            networkFlag = false
        }
    }

    private fun registerBroadcasts() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        mContext.registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun unregisterBroadcasts() {
        mContext.unregisterReceiver(broadcastReceiver)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            when (intent?.action) {
                ConnectivityManager.CONNECTIVITY_ACTION -> internetCheckProcess()
            }
        }
    }
}