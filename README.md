# rn-udesk

[![GitHub license](https://img.shields.io/badge/license-MIT-blue)](./LICENSE)
[![npm](https://img.shields.io/badge/npm-1.0.9-green)](https://www.npmjs.com/package/react-native-udesk-sdk)

此项目由[rn-udesk](https://github.com/huxinmin/react-native-udesk) fork 而来

## Getting started
`$ npm install react-native-udesk-sdk --save`

### Mostly automatic installation
`$ react-native link react-native-udesk-sdk`

## Usage

```js
import ReactNativeUdesk from 'react-native-udesk-sdk'

// 以下是ReactNativeUdesk可使用的方法

/**
 * 用户信息，用于传入这些用户信息，供客服查看
 */
export interface IUserInfo {
  nickname?: string;
  email?: string;
  phone?: string;
  description?: string;
  domain: string;
  appKey: string;
  appId: string;
  sdkToken: string; //是客户的唯一标识，用来识别身份，可以传userId
}

/**
 * 开始聊天
 * @param {IUserInfo} userInfo 用户信息
 */
 ReactNativeUdesk.startChat(userInfo)

/**
 * 发送咨询信息，例如商品订单
 */

export interface IItemInfo {
  title: string;
  subTitle: string;
  img: string;
  url: string;
}
 ReactNativeUdesk.sendCommodityMessage(userInfo, itemInfo);
```
