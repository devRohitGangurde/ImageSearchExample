package com.example.imageSearchApp.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imageSearchApp.R
import com.example.imageSearchApp.adapter.ImageGridLayoutViewAdapter
import com.example.imageSearchApp.constants.AppConstants
import com.example.imageSearchApp.model.Data
import com.example.imageSearchApp.model.Images
import com.example.imageSearchApp.utility.AppComman
import com.example.imageSearchApp.viewmodel.ImagesViewModel
import kotlinx.android.synthetic.main.activity_image_list.*
import kotlinx.coroutines.*


/**
 * User List activity for showing user list
 */
private const val TAG = "ImagesListActivity"

class ImagesListActivity : AppCompatActivity(), ImageGridLayoutViewAdapter.UserItemClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private lateinit var userViewModel: ImagesViewModel
    private lateinit var adapter: ImageGridLayoutViewAdapter
    private var userList: ArrayList<Data> = ArrayList()
    private var loadMore = false
    private var pageCount: Int = 1
    private var searchString: String = ""
    var queryTextChangedJob: Job? = null
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)

        initUI()
        initSupportClass()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        queryTextChangedJob?.cancel()

        if(query.isNullOrBlank()){
            Log.d(TAG, "onQueryTextChange: ")
        }else{
            if (AppComman.isNetworkAvailable(this@ImagesListActivity)) {
                queryTextChangedJob = coroutineScope.launch {
                    delay(250)
                    searchString = query
                    pageCount = 1
                    loadDefaultList(query)
                }
            } else {
                AppComman.showSnackBar(root_view_movie_list, getString(R.string.check_internet_connection))
            }
        }

        return false
    }

    override fun onClose(): Boolean {
        AppComman.hideKeyboardFrom(this, root_view_movie_list)
        pageCount = 1
        return false
    }

    /**
     * On onBackPressed- {@link ImagesListActivity}
     */
    override fun onBackPressed() {
        AppComman.hideKeyboardFrom(this, root_view_movie_list)
        finishAffinity()
        super.onBackPressed()
    }


    override fun onResume() {
        super.onResume()
        AppComman.hideKeyboardFrom(this, root_view_movie_list)
        clearSearchViewFocus()
    }


    /**
     * Here initialise UI for recycler view
     */
    private fun initUI() {
        val textView = search_view.findViewById(R.id.search_src_text) as TextView
        textView.setTextColor(Color.WHITE)
        search_view.setOnQueryTextListener(this)
        search_view.setOnCloseListener(this)


        recycler_movie_list.layoutManager = GridLayoutManager(this, 2)
        recycler_movie_list.itemAnimator = DefaultItemAnimator()

        recycler_movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == recyclerView.adapter?.itemCount?.minus(1)) {
                    if (!loadMore) {
                        loadMore = true
                        pageCount = pageCount.plus(1)

                        loadDefaultList(searchString)
                    }
                }
            }
        })


    }

    /**
     * Here support classes initialise  which used in this activity
     */
    private fun initSupportClass() {
        userViewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        userViewModel.getImagesData().observe(this, Observer {
            setDataToRecyclerView(it)
            hideLoadingViews()
        })

    }

    /**
     * Method to set data set to recyclerview
     */
    private fun setDataToRecyclerView(it: Images) = if (loadMore) {
        val lastPos = adapter.itemCount
        userList.addAll(it.data)
        adapter.notifyItemInserted(adapter.itemCount)
        recycler_movie_list.smoothScrollToPosition(lastPos+2)
        this.loadMore = false

    } else {
        userList.clear()
        userList = it.data as ArrayList<Data>
        adapter = ImageGridLayoutViewAdapter(this, this, userList)
        recycler_movie_list.adapter = adapter
    }


    /**
     * Load Progress views
     */
    private fun showLoadingViews() {
        loading_view.visibility = VISIBLE
    }

    /**
     * Hide Progress Views
     */
    private fun hideLoadingViews() {
        if (recycler_movie_list.adapter?.itemCount == 0) {
            view_list_placeholder.visibility = VISIBLE
        } else {
            view_list_placeholder.visibility = GONE
        }
        loading_view.visibility = GONE
    }


    /**
     * Here load the users list  from API
     */
    private fun loadDefaultList(query: String) {
        showLoadingViews()
        userViewModel.loadImageList(pageCount, query)
    }


    /**
     * On User Item Click callback here
     */
    override fun onUserItemClicked(userModel: Data, position: Int) {
        AppComman.hideKeyboardFrom(this, root_view_movie_list)

        val intent = Intent(this, ImageDetailActivity::class.java)
        intent.putExtra(AppConstants.INTENT_POSITION, position)
        intent.putExtra(AppConstants.INTENT_VALUE, userModel)
        startActivity(intent)

    }

    /**
     * Clear the focus on search view
     */
    private fun clearSearchViewFocus() {
//        if (search_view != null) {
//            search_view.clearFocus()
//            search_view.onActionViewCollapsed()
//        }
    }


}
