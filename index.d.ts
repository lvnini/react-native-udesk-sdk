/**
 * 用户信息，用于传入这些用户信息，供客服查看
 */
export interface IUserInfo {
    nickname?: string;
    email?: string;
    phone?: string;
    description?: string;
    domain:string;
    appKey:string;
    appId:string;
    sdkToken:string;  //是客户的唯一标识，用来识别身份，可以传userId
}

/**
 * 开始聊天
 * @param {IUserInfo} userInfo 用户信息
 */
export function startChat(userInfo?: IUserInfo): Promise<boolean>

/**
 * 商品订单资料
 */
export interface IItemInfo {
  title: string;
  subTitle: string;
  img: string;
  url: string;
}
/**
 * 发送咨询信息，例如商品订单
 */
export function sendCommodityMessage(userInfo?: IUserInfo,itemInfo?: IItemInfo): Promise<boolean>