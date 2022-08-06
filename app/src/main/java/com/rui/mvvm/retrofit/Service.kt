package com.rui.mvvm.retrofit

import com.rui.mvvm.bean.*
import retrofit2.http.*

interface Service {

    /**
     * 首页Banner
     * @return
     */
    @GET("/banner/json")
    suspend fun getBanner(): ResponseWrapper<BannerBean>

    /**
     *  置顶文章
     * @return
     */
    @GET("/article/top/json")
    suspend fun getTopArticle(): ResponseWrapper<TopArticleBean>


//    /**
//     * 首页数据
//     * http://www.wanandroid.com/article/list/0/json
//     * @param page page
//     */
//    @GET("/article/list/{page}/json")
//    suspend fun getHomeList(
//        @Path("page") page: Int
//    ): HomeListResponse
//
//    /**
//     * 知识体系
//     * http://www.wanandroid.com/tree/json
//     */
//    @GET("/tree/json")
//    suspend fun getTypeTreeList(): TreeListResponse
//
//    /**
//     * 知识体系下的文章
//     * http://www.wanandroid.com/article/list/0/json?cid=168
//     * @param page page
//     * @param cid cid
//     */
//    @GET("/article/list/{page}/json")
//    suspend fun getArticleList(
//        @Path("page") page: Int,
//        @Query("cid") cid: Int
//    ): ArticleListResponse
//
//    /**
//     * 常用网站
//     * http://www.wanandroid.com/friend/json
//     */
//    @GET("/friend/json")
//    suspend fun getFriendList(): FriendListResponse
//
//    /**
//     * 大家都在搜
//     * http://www.wanandroid.com/hotkey/json
//     */
//    @GET("/hotkey/json")
//    suspend fun getHotKeyList(): HotKeyResponse
//
//    /**
//     * 搜索
//     * http://www.wanandroid.com/article/query/0/json
//     * @param page page
//     * @param k POST search key
//     */
//    @POST("/article/query/{page}/json")
//    @FormUrlEncoded
//    suspend fun getSearchList(
//        @Path("page") page: Int,
//        @Field("k") k: String
//    ): HomeListResponse
//
//    /**
//     * 登录
//     * @param username username
//     * @param password password
//     * @return Deferred<LoginResponse>
//     */
//    @POST("/user/login")
//    @FormUrlEncoded
//    suspend fun loginWanAndroid(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): LoginResponse
//
//    /**
//     * 注册
//     * @param username username
//     * @param password password
//     * @param repassword repassword
//     * @return Deferred<LoginResponse>
//     */
//    @POST("/user/register")
//    @FormUrlEncoded
//    suspend fun registerWanAndroid(
//        @Field("username") username: String,
//        @Field("password") password: String,
//        @Field("repassword") repassowrd: String
//    ): LoginResponse
//
//    /**
//     * 获取自己收藏的文章列表
//     * @param page page
//     * @return Deferred<HomeListResponse>
//     */
//    @GET("/lg/collect/list/{page}/json")
//    suspend fun getLikeList(
//        @Path("page") page: Int
//    ): HomeListResponse
//
//    /**
//     * 收藏文章
//     * @param id id
//     * @return Deferred<HomeListResponse>
//     */
//    @POST("/lg/collect/{id}/json")
//    suspend fun addCollectArticle(
//        @Path("id") id: Int
//    ): HomeListResponse
//
//    /**
//     * 收藏站外文章
//     * @param title title
//     * @param author author
//     * @param link link
//     * @return Deferred<HomeListResponse>
//     */
//    @POST("/lg/collect/add/json")
//    @FormUrlEncoded
//    suspend fun addCollectOutsideArticle(
//        @Field("title") title: String,
//        @Field("author") author: String,
//        @Field("link") link: String
//    ): HomeListResponse
//
//    /**
//     * 删除收藏文章
//     * @param id id
//     * @param originId -1
//     * @return Deferred<HomeListResponse>
//     */
//    @POST("/lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    suspend fun removeCollectArticle(
//        @Path("id") id: Int,
//        @Field("originId") originId: Int = -1
//    ): HomeListResponse


//    /**
//     * 我的常用网址
//     * @return FriendListResponse
//     */
//    @GET("/lg/collect/usertools/json")
//    suspend fun getBookmarkList(): FriendListResponse


}
