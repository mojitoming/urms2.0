layui.link($WEB_ROOT_PATH + '/plugins/extend-module/iframe/iFrame.css');
layui.define('element', function (exports) {
    let element = layui.element,
        $ = layui.$;

    let $container;

    let obj = {
        init: function (param) {
            let param0 = {
                container: '#content-iframe',
                firstObj: null,
                offsetH: 0,
                offsetW: 0
            }

            param = combineJson(param, param0);

            $container = $(param.container);
            // 初始化第一个页面
            if (param.firstObj) {
                let $firstObj = $(param.firstObj);
                let firstId = $firstObj.attr('data-id'),
                    firstUrl = $WEB_ROOT_PATH + $firstObj.attr('data-url');
                $container.html(`<iframe id="${firstId}" name="${firstId}" scrolling="auto" frameborder="0" src="${firstUrl}"></iframe>`);
            }

            // 监听导航单击
            // 监听导航点击
            element.on('nav(nav-bar)', function (elem) {
                let $this = $(this);

                $container.empty();
                let iframeId = $this.attr('data-id');
                let iframeUrl = $WEB_ROOT_PATH + $this.attr('data-url');
                let iframeStr = `<iframe id="${iframeId}" name="${iframeId}" scrolling="auto" frameborder="0" src="${iframeUrl}"></iframe>`;

                $container.html(iframeStr);
                autoWH($container, param.offsetW, param.offsetH);

            });
            autoWH($container, param.offsetW, param.offsetH);
        }
    };

    exports('iFrame', obj);
});