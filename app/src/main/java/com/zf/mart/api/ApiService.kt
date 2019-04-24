package com.zf.mart.api


import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            @Field("label") label: String,
            @Field("is_default") is_default: String
    ): Observable<BaseBean<AddAddressBean>>

    /**
     * 删除地址
     */
    @GET("api/User/del_address")
    fun delAddress(@Query("id") id: String): Observable<BaseBean<Unit>>

    /**
     * 修改地址
     * */
    @POST("api/User/edit_address")
    @FormUrlEncoded
    fun editAddress(
            @Field("id") id: String,
            @Field("consignee") consignee: String,
            @Field("mobile") mobile: String,
            @Field("province") province: String,
            @Field("city") city: String,
            @Field("district") district: String,
            @Field("address") address: String,
            @Field("label") label: String,
            @Field("is_default") is_default: String
    ): Observable<BaseBean<EditAddressBean>>

    /**
     * 地址三级联动
     */
    @POST("api/region/get_region")
    @FormUrlEncoded
    fun getRegion(@Field("id") id: String): Observable<BaseBean<List<RegionBean>>>


    /**
     * 订单详情
     */
    @GET("api/order/order_detail")
    fun getOrderDetail(@Query("id") id: String): Observable<BaseBean<OrderDetailBean>>

    /**
     * 购物车列表
     */
    @GET("api/cart/cartlist")
    fun getCartList(@Query("page") page: Int, @Query("num") num: Int): Observable<BaseBean<CartBean>>

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

    /**
     * 拼团列表
     */
    @FormUrlEncoded
    @POST("api/groupbuy/grouplist")
    fun getGroupList(@Field("page") page: Int, @Field("num") num: Int): Observable<BaseBean<List<GroupBean>>>

    /**
     * 团购商品详情
     */
    @FormUrlEncoded
    @POST("api/groupbuy/detail")
    fun getGroupDetail(@Field("team_id") team_id: String): Observable<BaseBean<GroupDetailBean>>

    /**
     * 获取正在拼团的前5人
     */
    @FormUrlEncoded
    @POST("api/groupbuy/getTeamFive")
    fun getGroupMember(
            @Field("team_id") team_id: String
    ): Observable<BaseBean<GroupDetailBean>>

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("api/User/update_username")
    fun updateUserInfo(
            @Field("nickname") nickname: String,
            @Field("mobile") mobile: String,
            @Field("sex") sex: String,
            @Field("birthyear") birthyear: String,
            @Field("birthmonth") birthmonth: String,
            @Field("birthday") birthday: String
    ): Observable<BaseBean<ChangeUserBean>>

    /**
     * 购物车加减
     */
    @FormUrlEncoded
    @POST("api/Cart/changeNum")
    fun cartCount(
            @Field("cart[id]") id: String,
            @Field("cart[goods_num]") goods_num: String
    ): Observable<BaseBean<CartSelectBean>>

    /**
     * 竞拍列表
     */
    @FormUrlEncoded
    @POST("api/activity/auction_list")
    fun getAuctionList(
            @Field("page") page: Int,
            @Field("num") num: Int
    ): Observable<BaseBean<AuctionBean>>

    /**
     * 竞拍详情
     */
    @FormUrlEncoded
    @POST("api/auction/auction_detail")
    fun getAuctionDetail(
            @Field("id") id: String
    ): Observable<BaseBean<AuctionDetailBean>>

    /**
     * 获取抢购活动时间列表
     */
    @GET("api/activity/get_flash_sale_time")
    fun getSecKillTimeList(): Observable<BaseBean<SecKillTimeBean>>

    /**
     * 获取抢购活动列表
     */
    @FormUrlEncoded
    @POST("api/activity/flash_sale_list")
    fun getSecKillList(
            @Field("start_time") start_time: String,
            @Field("end_time") end_time: String,
            @Field("page") page: Int,
            @Field("num") num: Int
    ): Observable<BaseBean<SecKillListBean>>

    /**
     * 获取抢购活动详情
     */
    @FormUrlEncoded
    @POST("api/activity/flash_sale_info")
    fun getSecKillDetail(
            @Field("id") id: String
    ): Observable<BaseBean<SecKillDetailBean>>

    /**
     * 获取最新竞拍
     * 竞拍人
     */
    @FormUrlEncoded
    @POST("api/auction/GetAucMaxPrice")
    fun getAuctionPrice(
            @Field("aid") aid: String
    ): Observable<BaseBean<AuctionPriceBean>>

    /**
     *  竞拍
     *  出价
     */
    @FormUrlEncoded
    @POST("api/auction/offerPrice")
    fun requestBid(
            @Field("auction_id") auction_id: String,
            @Field("price") price: String
    ): Observable<BaseBean<Unit>>


    /**
     * 优惠券列表
     */
    @FormUrlEncoded
    @POST("api/Activity/coupon_list")
    fun getDiscount(
            @Field("status") status: String
    ): Observable<BaseBean<List<DiscountBean>>>

    /**
     * 我关注的商品
     */
    @GET("api/User/collect_list")
    fun getMyFollow(): Observable<BaseBean<List<MyFollowBean>>>

    /**
     *  获取商品评论
     */
    @FormUrlEncoded
    @POST("api/goods/getGoodsComment")
    fun getGoodEva(
            @Field("goods_id") goods_id: String,
            @Field("commentType") commentType: Int,
            @Field("page") page: Int,
            @Field("num") num: Int
    ): Observable<BaseBean<GoodEvaBean>>

    /**
    <<<<<<< HEAD
     *  获取商品详情
     */
    @FormUrlEncoded
    @POST("api/goods/goodsInfo")
    fun getGoodsDetail(
            @Field("goods_id") goods_id: String
    ): Observable<BaseBean<GoodsDetailBean>>

    /**
     *  获取商品运费
     */
    @FormUrlEncoded
    @POST("api/goods/dispatching")
    fun getGoodsFreight(
            @Field("goods_id") goods_id: String,
            @Field("region_id") region_id: String,
            @Field("buy_num") buy_num: String
    ): Observable<BaseBean<GoodsFreightBean>>

    /**
     *  获取商品属性
     */
    @FormUrlEncoded
    @POST("api/goods/goodsAttr")
    fun getGoodsAttr(
            @Field("goods_id") goods_id: String
    ): Observable<BaseBean<GoodsAttrBean>>

    /**
     * 我的---足迹
     */
    @GET("api/User/visit_log")
    fun getMyFoot(): Observable<BaseBean<List<MyFootBean>>>

    /**
     * 我的---足迹编辑
     */
    @FormUrlEncoded
    @POST("api/User/del_visit_log")
    fun setMyFoot(@Field("visit_ids") visit_ids: String): Observable<BaseBean<Unit>>

    /**
     * 我的---清空足迹
     */
    @GET("api/User/clear_visit_log")
    fun clearMyFoot(): Observable<BaseBean<Unit>>

    /**
     * 我的--分销会员页
     */
    @POST("api/User/distribut_index")
    fun getBonus(): Observable<BaseBean<BonusBean>>

    /**
     * 我的--会员
     */
    @GET("api/User/team_list")
    fun getMyVip(): Observable<BaseBean<List<MyVipBean>>>

    /**
     * 购物车选中状态
     */
    @Headers("Content-type:application/json")
    @POST("api/Cart/AsyncUpdateCart")
    fun requestCartSelect(
            @Body cart: RequestBody
    ): Observable<BaseBean<CartSelectBean>>


    /**
     * 购物车全选或者反选
     */
    @FormUrlEncoded
    @POST("api/Cart/selectedOrAll")
    fun requestCartCheckAll(
            @Field("all_flag") all_flag: Int
    ): Observable<BaseBean<CartSelectBean>>

    /**
     * 删除购物车商品
     */
    @Headers("Content-type:application/json")
    @POST("api/cart/delcart")
    fun requestDeleteCart(@Body id: RequestBody): Observable<BaseBean<CartSelectBean>>

}