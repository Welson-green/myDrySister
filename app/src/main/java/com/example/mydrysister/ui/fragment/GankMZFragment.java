package com.example.mydrysister.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.example.mydrysister.R;
import com.example.mydrysister.data.dto.GankMeizi;
import com.example.mydrysister.net.APIService;
import com.example.mydrysister.ui.adapter.GankMZAdapter;
import com.example.mydrysister.utils.ResUtils;
import com.example.mydrysister.utils.RxSchedulers;
import com.example.mydrysister.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GankMZFragment extends Fragment {

    private static final String TAG = "GankMZFragment";
    private SwipeRefreshLayout srl_refresh;
    private FloatingActionButton fab_top;
    private RecyclerView rec_mz;
    private CompositeDisposable mSubscriptions;
    private GankMZAdapter mAdapter;
    private static final int PRELOAD_SIZE = 6;
    private int mCurPage = 1;
    private ArrayList<GankMeizi> mData;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public static GankMZFragment newInstance(){
        return new GankMZFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mz_content,
                container,false);
        srl_refresh = view.findViewById(R.id.srl_refresh);
        rec_mz = view.findViewById(R.id.rec_mz);
        fab_top = view.findViewById(R.id.fab_top);
        srl_refresh.setOnRefreshListener(() -> {
            mCurPage = 1;
            fetchGankMZ(true);
        });

        final GridLayoutManager layoutManager
                = new GridLayoutManager(getActivity(),2);
        rec_mz.setLayoutManager(layoutManager);
        rec_mz.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                if(newState == RecyclerView.SCROLL_STATE_IDLE){//加载更多
                    if(layoutManager.getItemCount() - recyclerView.getChildCount()
                            <= layoutManager.findFirstVisibleItemPosition()){
                        ++mCurPage;
                        fetchGankMZ(false);
                    }
                }

                if(layoutManager.findFirstVisibleItemPosition() != 0){
                    fabInAnim();
                }else{
                    fabOutAnim();
                }
            }
        });
        fab_top.setOnClickListener(v -> {
            LinearLayoutManager manager = (LinearLayoutManager)rec_mz.getLayoutManager();
            //如果超过50项则直接跳到开头，不然要滚好久
            if(manager.findFirstVisibleItemPosition()<50){
                rec_mz.smoothScrollToPosition(0);
            }else{
                rec_mz.scrollToPosition(0);
                fabOutAnim();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscriptions = new CompositeDisposable();
        mData = new ArrayList<>();
        mAdapter = new GankMZAdapter(getActivity(), mData);
        rec_mz.setAdapter(mAdapter);
        srl_refresh.setRefreshing(true);
        fetchGankMZ(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    //拉取妹子数据
    private void fetchGankMZ(boolean isRefresh){
        Disposable subscribe = APIService.getInstance()
                .apis.fetchGankMZ(20,mCurPage)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> srl_refresh.setRefreshing(true))
                .doFinally(() -> srl_refresh.setRefreshing(false))
                .subscribe(data -> {
                    if(data != null && data.getResults() != null && data.getResults().size()>0){
                        ArrayList<GankMeizi> results = data.getResults();
                        if(isRefresh){
                            mAdapter.addAll(results);
                            ToastUtils.shortToast(ResUtils.getString(R.string.refresh_success));
                        }else{
                            mAdapter.loadMore(results);
                            String msg = String.format(ResUtils
                                    .getString(R.string.load_more_num),results.size(),"妹子");
                            ToastUtils.shortToast(msg);
                        }
                    }
                }, RxSchedulers::processRequestException);
        mSubscriptions.add(subscribe);
    }

    //悬浮按钮显示动画
    private void fabInAnim(){
        if(fab_top.getVisibility() == View.GONE){
            fab_top.setVisibility(View.VISIBLE);
            ViewCompat.animate(fab_top).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null).start();

        }
    }

    //悬浮图标隐藏动画
    private void fabOutAnim(){
        if(fab_top.getVisibility() == View.VISIBLE){
            ViewCompat.animate(fab_top).scaleX(0.0F).scaleY(0.0F).alpha(0.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                    fab_top.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }).start();
        }
    }


}
