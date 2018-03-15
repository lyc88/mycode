package com.whyuan.controller;
/**
*@Author: whyuan
*@Description: 请求类型的对象映射。
*@Date: Created in 10:59 2017/12/29
*@Modified By:
*/
public class Action {
    private ActionName actionName;//枚举请求类型

    public enum ActionName{
        SELECT,ADD,UPDATE,DELETE
    }

    public ActionName getActionName() {
        return actionName;
    }

}
