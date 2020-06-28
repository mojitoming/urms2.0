layui.use(['element', 'layer', 'form'], function () {
    let element = layui.element; //导航的 hover 效果、二级菜单等功能，需要依赖 element 模块
    let layer = layui.layer;
    let $ = layui.$;
    let form = layui.form;

    // 动态拼接菜单
    let $sidebar = $('#sidebar');
    $.ajax({
        type: 'GET',
        url: $WEB_ROOT_PATH + '/index-api/privilege/page',
        async: true,
        dataType: 'json',
        success: function (data) {
            let pageList = data.pageList;
            let liStr;
            $sidebar.empty();
            pageList.forEach(e => {
                liStr =
                    '<li class="layui-nav-item">' +
                    '    <a href="javascript:" data-id="' + e.moduleId + '" data-title="' + e.moduleName + '" data-url="' + e.moduleAction + '" data-type="tabAdd">' + e.moduleName + '</a>' +
                    '</li>';
                $sidebar.append(liStr);
            });

            element.render('nav');
        },
        error: function (data) {

        }
    });

    //监听导航点击
    element.on('nav(sidebar)', function (elem) {
        initHide();

        let tabData = $(this);
        let $tabLi = $(".layui-tab-title li[lay-id]");
        //这时会判断右侧 .layui-tab-title 属性下的有 lay-id 属性的 li 的数目，即已经打开的 tab 项数目
        if ($tabLi.length <= 0) {
            //如果比零小，则直接打开新的tab项
            active.tabAdd(tabData.attr("data-url"), tabData.attr("data-id"), tabData.attr("data-title"));
        } else {
            //否则判断该 tab 项是否已经存在
            let isData = false; //初始化一个标志，为 false 说明未打开该 tab 项 为 true 则说明已有
            $.each($tabLi, function () {
                //如果点击左侧菜单栏所传入的 id 在右侧 tab 项中的 lay-id 属性可以找到，则说明该 tab 项已经打开
                if ($(this).attr("lay-id") === tabData.attr("data-id")) {
                    isData = true;

                    return false;
                }
            });
            if (isData === false) {
                //标志为 false 新增一个 tab 项
                active.tabAdd(tabData.attr("data-url"), tabData.attr("data-id"), tabData.attr("data-title"));
            }
        }
        //最后不管是否新增 tab，最后都转到要打开的选项页面上
        active.tabChange(tabData.attr("data-id"));
    });

    let active = {
        //在这里给 active 绑定几项事件，后面可通过 active 调用这些事件
        tabAdd: function (url, id, name) {
            if (isEmpty(url)) return false;

            url = url.indexOf('http') === 0 ? url : $WEB_ROOT_PATH + url;
            //新增一个 Tab 项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的i d，是标签中 data-id 的属性值
            //关于 tabAdd 的方法所传入的参数可看 layui 的开发文档中基础方法部分
            element.tabAdd('main-tab', {
                title: name,
                content: '<iframe id="' + id + '" scrolling="auto" frameborder="0" src="' + url + '"></iframe>',
                id: id //规定好的 id
            });
            FrameWH();  //计算 iframe 层的大小
        },
        tabChange: function (id) {
            //切换到指定Tab项
            element.tabChange('main-tab', id); //根据传入的id传入到指定的tab项
        },
        tabDelete: function (id) {
            element.tabDelete("main-tab", id);//删除
        }
    };

    // 监听关闭tab
    element.on('tabDelete(main-tab)', function (data) {
        let $tabLi = $(".layui-tab-title li[lay-id]");
        if ($tabLi.length <= 0) {
            initShow();
        }
    });

    let initHide = function () {
        $('#init-content').hide();
        $('#tab-content').show();
    };

    let initShow = function () {
        $('#tab-content').hide();
        $('#init-content').show();
    };

    // 自适应高度
    let windowH = $(window).height();
    autoHeight();

    function autoHeight() {
        $('.layui-tab').height(windowH);
    }

    function FrameWH() {
        let tabTitle = $('.layui-tab-title').height() + 6;

        $("iframe").css("height", (windowH - tabTitle) + "px");
    }

    // 左下角
    let $system = $('#system-control > div');
    $system.hover(
        function () {
            $(this).removeClass('cursor-out');
            $(this).addClass('cursor-in')
        },
        function () {
            $(this).removeClass('cursor-in');
            $(this).addClass('cursor-out')
        }
    );

    // 退出
    $('#logout').click(function (e) {
        window.location.href = $WEB_ROOT_PATH + '/logout';

        e.stopPropagation();
    });

    // 用户信息
    // blind click event
    let $doc = $(document);
    let $infoDetail = $('#info-detail');
    let isDisplayed = false;

    $doc.on('click', '#info', function () {
        toggleInfoDetail();
    });
    // click other area hide
    $doc.mouseup(function (e) {
        let $systemControl = $('#system-control');
        if (!$systemControl.is(e.target) && $systemControl.has(e.target).length === 0
            &&
            !$infoDetail.is(e.target) && $infoDetail.has(e.target).length === 0) {
            hideInfoDetail();
        }
    });

    let toggleInfoDetail = function () {
        if (isDisplayed) {
            hideInfoDetail();
        } else {
            showInfoDetail();
        }
    };

    let showInfoDetail = function () {
        $infoDetail.animate({
                left: 200,
            },
            'normal', function () {
                $('#triangle').show();
            });

        isDisplayed = true;
    };

    let hideInfoDetail = function () {
        $('#triangle').hide();
        $infoDetail.animate({
            left: -40
        });

        isDisplayed = false;
    };

    // change password
    $('#change-password > button').click(function () {
        hideInfoDetail();

        layer.open({
            type: 1,
            title: false, //不显示标题栏
            closeBtn: false,
            area: '300px;',
            shade: 0.8,
            id: 'LAY_layuipro', //设定一个id，防止重复弹出
            btn: ['保存', '取消'],
            btnAlign: 'c',
            moveType: 1, //拖拽模式，0或者1
            content: '<div style="padding: 20px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' +
                '<form class="layui-form">' +
                '   <div class="layui-form-item">' +
                '       <label class="layui-form-label">当前密码</label>' +
                '       <div class="layui-input-block">' +
                '           <input type="password" name="password" placeholder="请输入当前密码" autocomplete="off" class="layui-input" required lay-verify="password">' +
                '       </div>' +
                '   </div>' +
                '   <div class="layui-form-item">' +
                '       <label class="layui-form-label">新密码</label>' +
                '       <div class="layui-input-block">' +
                '           <input type="password" name="newPwd" placeholder="请输入新密码" autocomplete="off" class="layui-input" required lay-verify="newPwd">' +
                '       </div>' +
                '   </div>' +
                '   <div class="layui-form-item">' +
                '       <label class="layui-form-label">确认密码</label>' +
                '       <div class="layui-input-block">' +
                '           <input type="password" name="confirmPwd" placeholder="请再次输入新密码" autocomplete="off" class="layui-input" required lay-verify="confirmPwd">' +
                '       </div>' +
                '   </div>' +
                '   <button id="btn-change-pwd" class="layui-btn" lay-submit lay-filter="btnChangePwd" style="display:none">立即提交</button>' +
                '</form>' +
                '</div>',
            yes: function (index, layero) {
                $('#btn-change-pwd').click();
            }
        });
    });
    //监听提交
    form.on('submit(btnChangePwd)', function (data) {
        let password = $('input[name="password"]').val();
        let newPwd = $('input[name="newPwd"]').val();
        let userId = $('#user-id').val();

        let url = $WEB_ROOT_PATH + '/users/new-password';
        $.ajax({
            type: 'post',
            url: url,
            async: true,
            dataType: 'json',
            data: {
                'user.userId': userId,
                'user.password': password,
                'newPwd': newPwd,
                // _method: 'PUT'
            },
            success: function (data) {
                if (data.operateSuccess) {
                    layer.msg('密码修改成功，请重新登录！', function () {
                        $('#logout').click();
                    });
                } else {
                    layer.msg(data.msg);
                }
            },
            error: function (data) {

            }
        });

        return false;
    });

    form.verify({
        password: function (value, item) {
            if (isEmpty(value)) {
                return '请输入当前密码';
            }
        },
        newPwd: function (value, item) {
            if (isEmpty(value)) {
                return '请输入新密码';
            }
        },
        confirmPwd: function (value, item) {
            if (isEmpty(value)) {
                return '请再次输入新密码';
            }

            let newPwd = $('input[name="newPwd"]').val();
            if (value !== newPwd) {
                return '两次输入的新密码不一致，请重新输入！';
            }
        }
    });
});
