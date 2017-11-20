package com.babuwyt.siji.finals;

/**
 * Created by lenovo on 2017/9/22.
 */

public class BaseURL {

//    public static final String BASE_URL = "http://www.babuwyt.com:8080/tmsapi/";
    public static final String BASE_URL = "http://123.206.75.242:8090/tmsapi/";
//    private static final String BASE_URL = "http://192.168.2.17:8080/api/";
//    private static final String BASE_URL = "http://192.168.2.151:8080/tmsapi/";
    public static final String BASE_IMAGE_URI = "http://bbkj-1253538594.picgz.myqcloud.com/";
    /**
     *获取验证码
     */
    public static final String GETA_UTNCODE = BASE_URL + "system/send";
    /**
     * 登录
     */
    public static final String LOGIN = BASE_URL + "system/login/phone";
    /**
     * 获取个人信息
     */
    public static final String GETPERSONAL_INFO = BASE_URL + "personal/get/personalinfo";
    /**
     * 获取首页订单数量
     */
    public static final String SELECT_SEND_CARORDERNUM = BASE_URL + "roborder/selectsendcarordernum";
    /**
     * 获取首页当前任务
     */
    public static final String SENCARORDERINFO = BASE_URL + "sendcarorderinfo/select/sencarorderinfo";
    /**
     * 抢单大厅
     */
    public static final String SELECTOBORDER = BASE_URL + "roborder/selectroborder";
    /**
     * 获取用户协议
     */
    public static final String GETTEXT = BASE_URL + "appdrivertext/gettext/+1";
    /**
     *获取历史订单头部金额
     */
    public static final String GETACOUNT = BASE_URL + "personal/count/acount";
    /**
     *获取历史订单列表
     */
    public static final String GETACOUNT_LIST = BASE_URL + "personal/get/account";
    /**
     *查看照片
     */
    public static final String SIGNPICTURE = BASE_URL + "signpicture/select/signpicture";
    /**
     *查看地址
     */
    public static final String SELECT_ADDRESS = BASE_URL + "loadpick/select/toapp";
    /**
     *查看查看详情 1 收入 2 扣除
     */
    public static final String SELECT_INCOME = BASE_URL + "sendcarorderinfo/select/income";
    /**
     *签到
     */
    public static final String QIANDAO = BASE_URL + "sendcarorderinfo/drivercheckinfo";
    /**
     *绑定油卡
     */
    public static final String BINDINGYOUKA = BASE_URL + "sendcarorderinfo/oilcardbind";
    /**
     *装货拍照和签收拍照检测
     * &1 装
     * &2 签收
     */
    public static final String TASKSTATE = BASE_URL + "sendcarorderinfo/select/taskstate";
    /**
     *H获取签收照片  装货照片
     */
    public static final String GETPICTRUES = BASE_URL + "sendcarorderinfo/select/pictures";
    /**
     *装货拍照上传
     */
    public static final String PUSH_INSERT = BASE_URL + "sendcarorderinfo/loadPicture/insert";
    /**
     *个人信息认证
     */
    public static final String AUTHENTICATION = BASE_URL + "personal/driver/authentication";
    /**
     *获取车辆类型
     */
    public static final String SELECT_CAR_TYPE = BASE_URL + "systemcode/trucktype/selectAll";
    /**
     *获取订单详情
     */
    public static final String GET_ORDER_DETAILS = BASE_URL + "roborder/selectOwnerSendCarOrder";
    /**
     * 钱包详情
     */
    public static final String GET_QIANBAO_DETAILS = BASE_URL + "personal/get/account/detail";
    /**
     *查看是否绑定银行卡 未绑定为空
     */
    public static final String BANKINFO = BASE_URL + "BankCard/bankInfo";
    /**
     *大小额鉴权
     */
    public static final String MONEY_DECERTUFC= BASE_URL + "BankCard/moneyDecertific";
    /**
     *查询所有银行
     */
    public static final String ALLBANKIFO = BASE_URL + "BankCard/queryAllBankInfo";
    /**
     *查询所有银行
     */
    public static final String ALLBANKIFO1 = BASE_URL + "BankCard/allBankIfo";
    /**
     *获取绑定银行时所需要的验证码
     */
    public static final String BINDINGBANKCAR = BASE_URL + "BankCard/bindingbankcar";
    /**
     *回填验证码绑定银行卡
     */
    public static final String BINDMESBACKFILL = BASE_URL + "BankCard/bindMesBackfill";
    /**
     *回填鉴权金额
     */
        public static final String DECERTIFIC_MONEY = BASE_URL + "BankCard/decertificMoney";
    /**
     *查询大小额联行号
     */
    public static final String BANKNOINFO = BASE_URL + "BankCard/BanknoInfo";
    /**
     *充值
     */
    public static final String PAYMENTDUES = BASE_URL + "BankCard/paymentDues";
    /**
     *提现
     */
    public static final String CASHMONEY = BASE_URL + "BankCard/cashMoney";
    /**
     *提现验证码
     */
    public static final String MSG_DYNAMICCODE = BASE_URL + "BankCard/msgDynamicCode";
    /**
     *我的钱包
     */
    public static final String TRANSACTION_SELECT = BASE_URL + "transaction/select";
    /**
     *缴纳保证金
     */
    public static final String MEMSAMONG_TRANSACTIONPRIVATE = BASE_URL + "BankCard/memsAmongTransaction";

    /**
     * 检测版本
     *
     * (1 : 司机App  2 : 现场App  3 : 合伙人App  4 : 货主App)
     */
    public static final String CHECKVERSION=BASE_URL+"appcommon/getappversion";

    /**
     *退还保证金通知接口
     */
    public static final String SENDMESSAGE=BASE_URL+"personal/driver/sendmessage";
}
