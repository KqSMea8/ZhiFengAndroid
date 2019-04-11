package com.zf.mart.api


import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * Api 接口
 */

interface ApiService {

    /**
     * 上传头像
     */
    @POST("api/user/update_head_pic")
    @Multipart
    fun uploadMemberIcon(@Part partList: MultipartBody.Part): Observable<BaseBean<String>>

    /**
     * 登录
     */
    @POST("/api/user/login")
    @FormUrlEncoded
    fun login(
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Observable<BaseBean<LoginBean>>

    /**
     * 注册
     */
    @POST("user/reg")
    @FormUrlEncoded
    fun register(@Field("mobile") mobile: String, @Field("password") password: String): Observable<BaseBean<Unit>>

    /**
     * 用户信息
     */
    @GET("/api/user/userinfo")
    fun getUserInfo(): Observable<BaseBean<UserInfoBean>>

    /**
     * 订单列表
     */
    @GET("api/order/order_list")
    fun getOrderList(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Observable<BaseBean<List<OrderListBean>>>

    /**
     * 地址列表
     */
    @GET("api/user/address_list")
    fun getAddressList(): Observable<BaseBean<List<AddressBean>>>

    /**
     * 添加地址
     */

     @POST("api/User/add_address")
     @FormUrlEncoded
     fun setAddressList(
        @Field("consignee") consignee: String,
        @Field("mobile") mobile: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("district") district: String,
        @Field("address") address: String,
        @Field("is_default") is_default: String
        ):Observable<BaseBean<List<AddAddressBean>>>

    /**
     * 删除地址
     */
    @GET("api/User/del_address")
    fun delAddress(@Query("id") id: String): Observable<BaseBean<Unit>>

    /**
     * 修改地址
     * */

    /**
     * 地址三级联动
     */
    @POST("api/region/get_region")
    @FormUrlEncoded
    fun getRegion( @Field("id") id: String):Observable<BaseBean<List<RegionBean>>>



    /**
     * 订单详情
     */
    @GET("api/order/order_detail")
    fun getOrderDetail(@Query("id") id: String): Observable<BaseBean<OrderDetailBean>>

    /**
     * 购物车列表
     */
    @GET("api/cart/cartlist")
    fun getCartList(): Observable<BaseBean<List<ShopList>>>

    /**
     * 分类左边标题
     */
    @GET("api/goods/categoryList")
    fun getClassifyList(): Observable<BaseBean<List<ClassifyBean>>>

    /**
     * 商品搜索列表
     */
    @GET("api/Search/search")
    fun getSearchList(
        @Query("q") q: String,
        @Query("id") id: String,
        @Query("brand_id") brand_id: String,
        @Query("sort") sort: String,
        @Query("sel") sel: String,
        @Query("price") price: String,
        @Query("start_price") start_price: String,
        @Query("end_price") end_price: String,
        @Query("sort_asc") sort_asc: String,
        @Query("page") page: Int, //第几页
        @Query("per_page") per_page: Int //每页多少条
    ): Observable<BaseBean<SearchBean>>

    /**
     * 分类
     */
    @GET("api/goods/Products")
    fun getClassifyProduct(@Query("cat_id") cat_id: String): Observable<BaseBean<List<ClassifyProductBean>>>

}