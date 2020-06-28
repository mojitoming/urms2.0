/**
 * 获得应用的根路径
 */
const $WEB_ROOT_PATH = (() => {
    let strFullPath = window.document.location.href;
    let strPath = window.document.location.pathname;
    let pos = strFullPath.indexOf(strPath);
    let prePath = strFullPath.substring(0, pos);
    let postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);

    return prePath + postPath;
})();


/**
 * Annotation:
 * 是否为空判断
 *
 * @Author: Adam Ming
 * @Date: Jun 2, 2020 at 3:36:02 PM
 */
const isEmpty = function (value) {
    return value === null || value === '' || value === undefined;
};

/**
 * Annotation:
 * 数字千分位格式化
 *
 * @Author: Adam Ming
 * @Date: Jun 2, 2020 at 3:36:31 PM
 */
const toThousands = function (num) {
    let result = '';
    num = (num || 0).toString();

    let dot = num.indexOf('.') > -1 ? num.indexOf('.') : num.length;
    let numDecimal = num.substr(dot);
    let numInteger = num.substring(0, dot);

    while (numInteger.length > 3) {
        result = ',' + numInteger.slice(-3) + result;
        numInteger = numInteger.slice(0, numInteger.length - 3);
    }

    if (numInteger) {
        result = numInteger + result;
    }

    result += numDecimal;

    return result;
};

/**
 * Annotation:
 * JSON Combine
 * json1 合并到 json2 上
 *
 * @Author: Adam Ming
 * @Date: May 11, 2020 at 7:18:53 PM
 */
const combineJson = function (json1, json2) {
    for (let key in json1) {
        if (typeof json1[key] === 'object') {
            json2[key] = combineJson(json1[key], json2[key]);
        } else {
            json2[key] = json1[key];
        }
    }

    return json2;
};

/**
 * Annotation:
 * 页面自适应
 *
 * @Author: Adam Ming
 * @Date: Jun 2, 2020 at 3:18:00 PM
 */
const autoWH = function (obj, offsetW, offsetH) {
    let windowW = window.innerWidth;
    let windowH = window.innerHeight;

    let _mainHeader = document.getElementsByClassName('main-header')[0];
    let mainHeaderH = _mainHeader ? _mainHeader.offsetHeight : 0;

    let _firstMenu = document.getElementsByClassName('first-menu')[0];
    let firstMenuH = _firstMenu ? _firstMenu.offsetHeight : 0;

    let _secondMenu = document.getElementsByClassName('second-menu')[0];
    let secondMenuW = _secondMenu ? _secondMenu.offsetWidth : 0;

    let _thirdMenu = document.getElementsByClassName('third-menu')[0];
    let thirdMenuH = _thirdMenu ? _thirdMenu.offsetHeight : 0;

    obj[0].setAttribute('style', `height: ${windowH - mainHeaderH - firstMenuH - thirdMenuH - offsetH}px; width: ${windowW - secondMenuW - offsetW}px`);
};

/**
 * Annotation:
 * Date 原型格式化
 *
 * @Author: Adam Ming
 * @Date: Apr 24, 2020 at 5:06:58 PM
 */
Date.prototype.format = function (fmt) {
    let o = {
        "M+": this.getMonth() + 1,                   //月份
        "d+": this.getDate(),                        //日
        "h+": this.getHours(),                       //小时
        "m+": this.getMinutes(),                     //分
        "s+": this.getSeconds(),                     //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()                  //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (let k in o) {
        if (new RegExp("(" + k + ")/i").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

/**
 * Annotation:
 * 获取各级菜单宽度
 *
 * @Author: Adam Ming
 * @Date: Jun 4, 2020 at 6:22:09 PM
 */
const getMenuWidth = function (level) {
    let menuClass;
    switch (level) {
        case 0:
            menuClass = 'main-header';

            break;
        case 1:
            menuClass = 'first-menu';

            break;
        case 2:
            menuClass = 'second-menu';

            break;
    }

    let menu = document.getElementsByClassName(menuClass)[0];

    return menu ? menu.offsetWidth : 0;
}

const $MENU_WH = {
    get width() {
        return window.top.document.getElementById("sidebar").offsetWidth;
    },
    get height() {
        return window.top.document.getElementById("sidebar").offsetHeight;
    }
};