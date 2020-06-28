// 关键字搜索模块
layui.link($WEB_ROOT_PATH + '/plugins/aliconfont/iconfont.css');
layui.link($WEB_ROOT_PATH + '/plugins/extend-module/sidenav/sideNav.css');
layui.define(['element', 'layer'], function (exports) {
    let $ = layui.$,
        element = layui.element,
        layer = layui.layer;
    // 搜索部分······start
    let $sideNav = $('.gms-side-nav');
    let keywords =
        '<div class="gms-keyword-search">' +
        '   <input type="text" name="title" placeholder="搜索" autocomplete="off" class="layui-input gms-keyword-search-input">' +
        '   <i id="search-btn" class="layui-icon layui-icon-search"></i>' +
        '   <i id="add-btn" class="layui-icon layui-icon-addition"></i>' +
        '</div>' +
        '<div id="left-list" class="layui-collapse"></div>';
    $sideNav.empty();
    $sideNav.html(keywords);

    // 定义页面传参
    class DataCarrier {
        constructor(dscID, dscTitle, dscRemark, dsID, dsTitle, dscCnt, status, statusPublic, iconType) {
            this.dscID = dscID; // 数据样本集ID
            this.dscTitle = dscTitle; // 数据样本集名称
            this.dscRemark = dscRemark; // 数据样本集备注
            this.dsID = dsID; // 应用数据集ID
            this.dsTitle = dsTitle; // 应用数据集名称
            this.dscCnt = dscCnt;
            this.status = status;
            this.statusPublic = statusPublic;
            this.iconType = iconType;
            this.isModified = false;
        }

        toString() {
            return `[dscID: ${this.dscID}, dscTitle: ${this.dscTitle}, dscRemark: ${this.dscRemark}, dsID: ${this.dsID}, dsTitle: ${this.dsTitle}]`;
        }
    }

    // 搜索回车监听
    $('.gms-keyword-search-input').on('keydown', function (event) {
        // 绑定回车事件
        if (event.key === 'Enter') {
            let keywords = $(this).val();
            getLeftDspl(keywords);
        }
    });

    // 新增数据样本集合
    $('#add-btn').on('click', function (e) {
        layui.dataCarrier = new DataCarrier();
        openModifyLayer('新增数据样本集合');

        e.stopPropagation();
    });

    // 搜索部分······end

    // 获取左侧数据样本列表
    // getLeftDspl();

    function getLeftDspl(keywords) {
        let url = $WEB_ROOT_PATH + '/dspl-api/infos';
        $.ajax({
            type: 'get',
            url: url,
            async: true,
            dataType: 'json',
            data: {
                'gdsc.dscTitle': keywords
            },
            success: function (data) {
                leftDsplInfosAssembly(data);
            },
            error: function (data) {

            }
        });
    }

    // 左侧数据样本列表 assemble
    function leftDsplInfosAssembly(data) {
        let dsplInfos = data.dataSampleVoList;

        let dscId = '', dscTitle = '', dscRemark = '', dsId = '', dsTitle = '', status, statusPublic;
        let dsCnt = 0, dsExclCnt = 0, dscCnt = 0;
        let dsCntKilo, dsExclCntKilo, dscCntKilo, dscPercent;

        let contentCollapse = '', // 列表内容
            iconStr = '';

        for (let i = 0; i < dsplInfos.length; i++) {
            dscId = dsplInfos[i].dscId;
            dscTitle = dsplInfos[i].dscTitle; // 数据样本名称
            dscRemark = dsplInfos[i].remark; // 数据样本备注
            dsId = dsplInfos[i].dsId; // 应用数据集ID
            dsTitle = dsplInfos[i].dsTitle; // 应用数据集名称
            dsCnt = dsplInfos[i].dsCnt; // 应用数据集病例数
            dsExclCnt = dsplInfos[i].dsExclCnt; // 数据样本排除病例数
            dscCnt = dsCnt - dsExclCnt; // 数据样本病例数
            status = dsplInfos[i].status; // 应用状态
            statusPublic = dsplInfos[i].statusPublic; // 发布状态

            // 千分位化
            dsCntKilo = toThousands(dsCnt);
            dsExclCntKilo = toThousands(dsExclCnt);
            dscCntKilo = toThousands(dscCnt);
            // 百分化 样本病例数占比
            dscPercent = dsCnt === 0 ? 0 : toDecimal((dscCnt) / dsCnt * 100);

            let show = ''; // 是否展开，默认展开3个
            if (i < 3) {
                show = ' layui-show';
            }

            // 处理下数据
            if (!dscRemark || dscRemark === 'null') {
                dscRemark = '';
            }

            if (status === 'INIT' && statusPublic === 'INIT') {
                iconStr =
                    '<span class="iconfont icon-editor" icon-type="edit"></span>' +
                    '<span class="iconfont icon-shanchu2" icon-type="delete"></span>' +
                    '<span class="iconfont icon-fenxiang" icon-type="release"></span>' +
                    '<span class="iconfont icon-load" icon-type="init"></span>';
            } else if (status === 'NORM') {
                iconStr = '<span class="iconfont icon-icon-test2" icon-type="lock"></span>';
            } else if (status === 'DISA') {
                iconStr = '<span class="iconfont icon-icon-test1" icon-type="unlock"></span>';
            }

            // 数据组装
            contentCollapse += // 列表内容
                '    <div class="layui-colla-item">' +
                '        <h2 class="layui-colla-title">' +
                `            <span class="colla-title long-text" title="${dscTitle}">${dscTitle}</span>` + // 使用模板字符串
                '            <span class="colla-icon">' +
                '                <span class="icon-operation">' +
                '                    <span class="iconfont icon-youjiantou" icon-type="detail"></span>' +
                iconStr +
                '                </span>' +
                '            </span>' +
                '        </h2>' +
                `        <div class="layui-colla-content${show}">` +
                `            <p>应用数据集：${dsTitle}（${dsCntKilo} 例）</p>` +
                `            <p>样本病例数：${dscCntKilo} 例，占比 ${dscPercent}%</p>` +
                '            <p>数据达标率：xxx%</p>' +
                '        </div>' +
                `        <input type="hidden" class="data-carrier" dsc-id="${dscId}" dsc-title="${dscTitle}" ds-id="${dsId}" ds-title="${dsTitle}"` +
                ` dsc-remark="${dscRemark}" dsc-cnt="${dscCnt}" status="${status}" status-public="${statusPublic}">` + // 数据载体
                '    </div>';
        }

        let $leftList = $('#left-list');
        $leftList.empty();
        $leftList.html(contentCollapse);
        element.render('collapse');

        // bind event
        bindEvent();
        firstCall();
    }

    function bindEvent() {
        // 操作按钮 click

        layui.dataCarrier = new DataCarrier();
        $('.layui-colla-title .colla-icon .icon-operation span').on('click', function (e) {
            let $this = $(this);
            let iconType = $this.attr('icon-type');

            let $dataCarrier = $this.parents('.layui-colla-item').children('.data-carrier');
            let dscID = $dataCarrier.attr('dsc-id'),
                dscTitle = $dataCarrier.attr('dsc-title'),
                dscRemark = $dataCarrier.attr('dsc-remark'),
                dsID = $dataCarrier.attr('ds-id'),
                dsTitle = $dataCarrier.attr('ds-title'),
                dscCnt = $dataCarrier.attr('dsc-cnt'),
                status = $dataCarrier.attr('statue'),
                statusPublic = $dataCarrier.attr('status-public');

            layui.dataCarrier = new DataCarrier(dscID, dscTitle, dscRemark, dsID, dsTitle, dscCnt, status, statusPublic, iconType);

            switch (iconType) {
                case 'detail': // 查看详情
                    showDetail();
                    break;
                case 'edit': // 修改
                    openModifyLayer('修改数据样本集合');
                    break;
                case 'delete': // 删除
                case 'release': // 发布
                case 'lock': // 停用
                case 'unlock': // 启用
                    layui.dataCarrier.iconType = iconType;
                    openStaticLayer(iconType);
                    break;
                case 'init': // 初始状态
                    layer.msg('初始状态！');
                    break;
            }

            e.stopPropagation();
        });

        // 查看详情
        function showDetail() {
            // detail 信息单独存储，方便后面的使用
            layui.detail = new DataCarrier();
            Object.assign(layui.detail, layui.dataCarrier);
            $('#ds-info').click();
        }
    }

    // 新增 && 修改
    function openModifyLayer(title) {
        layer.open({
            type: 2,
            title: title,
            skin: 'demo-class',
            area: ['460px', '460px'],
            offset: '5px',
            content: [$WEB_ROOT_PATH + '/dspl/modification', 'no'],
            btn: ['取消', '确定'],
            yes(index, layero) {
                layer.close(index);

                return false;
            },
            btn2(index, layero) {
                // layer.msg('confirm');
                // 获取 iframe 页 DOM 元素
                let submitModify = layer.getChildFrame('#submit-modify', index);
                submitModify.click();

                return false;
            },
            end() {
                // TODO 暂时使用页面刷新，之后研究数据绑定，完成局部刷新
                if (layui.dataCarrier.isModified) {
                    window.location.reload();
                }

                // 清空
                layui.dataCarrier = new DataCarrier();
            }
        });
    }

    // 删除
    function openStaticLayer(iconType) {
        let title, btn, confirmMsg;
        switch (iconType) {
            case 'delete':
                title = '删除数据样本集合';
                btn = ['取消', '删除'];
                confirmMsg = '确定删除该数据集？！';
                break;
            case 'release':
                title = '发布数据样本集合';
                btn = ['取消', '发布'];
                confirmMsg = '确定发布该数据集？！';
                break;
            case 'lock':
                title = '停用数据样本集合';
                btn = ['取消', '停用'];
                confirmMsg = '确定停用该数据集？！';
                break;
            case 'unlock':
                title = '启用数据样本集合';
                btn = ['取消', '启用'];
                confirmMsg = '确定启用该数据集？！';
                break;
        }

        layer.open({
            type: 2,
            title: title,
            skin: 'demo-class',
            area: ['460px', '460px'],
            offset: '5px',
            content: [$WEB_ROOT_PATH + '/dspl/deletion', 'no'],
            btn: btn,
            yes(index, layero) {
                layer.close(index);

                return false;
            },
            btn2(index, layero) {
                let _index = index;
                layer.confirm(confirmMsg, {icon: 3, title: '警告', offset: '80px'}, function (index) {
                    // 获取 iframe 页 DOM 元素
                    let submitDelete = layer.getChildFrame('#submit-delete', _index);
                    submitDelete.click();

                    layer.close(index);
                });

                return false;
            },
            end() {
                if (layui.dataCarrier.isModified) {
                    window.location.reload();
                }
                // 清空
                layui.dataCarrier = new DataCarrier();
            }
        });
    }

    function firstCall() {
        let firstDetail = $sideNav.find('span[icon-type="detail"]').get(0);
        firstDetail.click();
    }

    exports('sideNav');
});