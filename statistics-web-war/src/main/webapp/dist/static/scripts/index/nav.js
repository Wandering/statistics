define('static/scripts/index/nav', ['sea-modules/jquery/cookie/jquery.cookie', 'sea-modules/bootstrap/bootstrap', 'static/scripts/index/message', 'static/scripts/index/common/urlConfig', 'static/scripts/index/menuManager/menu_manager'], function (require, exports, module) {

    require('sea-modules/jquery/cookie/jquery.cookie');
    var token = $.cookie('bizData');

    require('sea-modules/bootstrap/bootstrap');
    var message = require('static/scripts/index/message');

    var UrlConfig = require('static/scripts/index/common/urlConfig');

    var widthLess1024 = function () {
        if ($(window).width() < 1024) {
            $("#sidebar, #navbar").addClass("collapsed");
            $("#navigation").find(".dropdown.open").removeClass("open");
            $("#navigation").find(".dropdown-menu.animated").removeClass("animated");
            if ($("#sidebar").hasClass("collapsed")) {
                $("#content").animate({
                    left: "0px",
                    paddingLeft: "55px"
                }, 150)
            } else {
                $("#content").animate({
                    paddingLeft: "55px"
                }, 150)
            }
        } else {
            $("#sidebar, #navbar").removeClass("collapsed");
            if ($("#sidebar").hasClass("collapsed")) {
                $("#content").animate({
                    left: "210px",
                    paddingLeft: "265px"
                }, 150)
            } else {
                $("#content").animate({
                    paddingLeft: "265px"
                }, 150)
            }
        }
    };
    var widthLess768 = function () {
        if ($(window).width() < 768) {
            if ($(".collapsed-content .search").length === 1) {
                $("#main-search").appendTo(".collapsed-content .search")
            }
            if ($(".collapsed-content li.user").length === 0) {
                $(".collapsed-content li.search").after($("#current-user"))
            }
        } else {
            $("#current-user").show();
            if ($(".collapsed-content .search").length === 2) {
                $(".nav.refresh").after($("#main-search"))
            }
            if ($(".collapsed-content li.user").length === 1) {
                $(".quick-actions >li:last-child").before($("#current-user"))
            }
        }
    };

    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    var systemCode = GetQueryString('systemCode');
    /**
     * 处理左边菜单
     */
    var Nav = {
        navStr: [],
        menuCode: {
            /**
             * 公司权限管理
             */
            companyAuthorityManager: function () {
                $('#page_0').html('');
                require.async(['static/scripts/index/authority/company'], function (module) {
                    Nav.treeMenuEntry(module, '请联系产品管理员添加产品');
                });
            },
            /**
             * 权限管理
             */
            authorityManager: function () {
                $('#page_0').html('');
                Nav.changeSection('tmpl_1');
                try {
                    require.async(['static/scripts/index/authority/authority'], function (module) {
                        module();
                    });
                } catch (e) {
                }
            },
            /**
             * 资源管理
             */
            resourceManager: function () {
                $('.mask').fadeIn();
                setTimeout(function () {
                    $('.mask').fadeOut();
                }, 5000);
                $('#page_0').html('');
                require.async(['static/scripts/index/common/initTreeGrid','static/scripts/index/resource/resource_list'], function (module, listGridModule) {
                    var options = {
                        succ: Nav.getTreeDataSuccess,
                        err: function (ret) {
                            Nav.getTreeDataError(ret, '请联系系统管理员添加菜单信息');
                        },
                        getTreeDataURL: UrlConfig.getMenuTree + '&systemCode=' + systemCode,
                        listGridModule: listGridModule
                    };
                    module(options);
                });
            },
            /**
             * 角色管理
             */
            roleManager: function () {
                Nav.changeSection('tmpl_1');
                try {
                    require.async(['static/scripts/index/role/role'], function (module) {
                        module();
                    });
                } catch (e) {
                }
            },
            /**
             * 菜单管理
             */
            menuManager: function () {
                $('#page_0').html('');
                var module = require('static/scripts/index/menuManager/menu_manager');
                Nav.treeMenuEntry(module);
            },
            /**
             * ==================================
             * 货物管理 - (goodsSelect)
             * ==================================
             */
            goodsSelect: function () {
                $('#page_0').html('');
                Nav.changeSection('goodsSelect');
                try {
                    require.async(['static/scripts/index/goodsManager/goodsSelectList'], function (module) {
                        module();
                    });
                } catch (e) {
                }
            },
            /**
             * ==================================
             * 数据监控 - (behaviorStatistics)
             * ==================================
             */
            behaviorStatistics: function () {
                $('#page_0').html('');
                Nav.changeSection('dataMonitored');
                try {
                    require.async(['static/scripts/index/dataMonitored/dataMonitoredChart','static/scripts/index/dataMonitored/dataMonitoredList'], function (module,monitoredList) {
                        module();
                        monitoredList();
                    });
                } catch (e) {
                }
            },

            /**
             * ==================================
             * 分成规则 - (proportionManager)
             * ==================================
             */
            separateManager: function () {
                $('#page_0').html('');
                Nav.changeSection('proportionManager');
                try {
                    require.async(['static/scripts/index/proportionManager/proportionList'], function (module) {
                        module();
                    });
                } catch (e) {
                }
            },

            /**
             * ==================================
             * 订单管理	orderManager
             * ==================================
             */
            orderManager: function () {
                $('#page_0').html('');
                Nav.changeSection('orderManager');
                try {
                    require.async(['static/scripts/index/orderManager/orderManager'], function (module) {
                        module();
                    });
                } catch (e) {
                }
            },






            /**
             * 实时统计
             */
            realTimeStatistics: function () {
                $('#page_0').html('');
                require.async(['static/scripts/index/realTimeDebt/realTimeDebt'], function (realTimeDebt) {
                    realTimeDebt();
                });
            },
            /**
             * 历史记录
             */
            historicalRecords: function () {
                Nav.changeSection('tmpl_3');
                require.async(['static/scripts/index/pastRecordsDebt/pastDebtChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             * 基本情况
             */
            base: function () {
                Nav.changeSection('tmpl_3');
                require.async(['static/scripts/index/basicStatus/basicStatusChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             * 登录人数统计
             */
            userLoginNum: function () {
                Nav.changeSection('tmpl_3');
                require.async(['static/scripts/index/userLogin/userLoginChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             * 登录天数统计
             */
            userLoginDayNum: function () {
                $('#page_0').html('');
                $('#page_0').append('<div class="form-inline" id="form_search">'
                    + '<div class="control-group date-condition">'
                    + '<label class="control-label">时间:</label>'
                    + '<input size="16" type="text" readonly class="form_date" id="time">'
                    + '</div>'
                    + '</div>'
                    + '<div class="chart-content">'
                    + '<div class="chart" id="myChart"></div>'
                    + '</div>');
                require.async(['static/scripts/index/userLoginDayNum/userLoginDayNumChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             * web登录人数统计
             */
            webUserLoginNum: function () {
                Nav.changeSection('tmpl_3');
                require.async(['static/scripts/index/webuserLogin/webuserLoginChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             *Web登录天数统计
             */
            webUserLoginDayNum: function () {
                $('#page_0').html('');
                $('#page_0').append('<div class="form-inline" id="form_search">'
                    + '<div class="control-group date-condition">'
                    + '<label class="control-label">时间:</label>'
                    + '<input size="16" type="text" readonly class="form_date" id="time">'
                    + '</div>'
                    + '</div>'
                    + '<div class="chart-content">'
                    + '<div class="chart" id="myChart"></div>'
                    + '</div>');
                require.async(['static/scripts/index/webuserLoginDayNum/webuserLoginDayNumChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             * 用户登录次数
             */
            userLoginNumTimes: function () {
                Nav.changeSection('tmpl_3');
                require.async(['static/scripts/index/webuserLoginNum/webuserLoginNumChart'], function (pastRecordsDebt) {
                    pastRecordsDebt();
                });
            },
            /**
             * 单个账号统计
             */
            individualAccountStatistics: function () {
                $('#page_0').html('');
                require.async(['static/scripts/index/individualAccountStatistics/treeAccount'], function (module) {
                    Nav.treeMenuEntry(module);
                });
            },
            /**
             * 活跃统计
             */
            activeStatistics: function () {
                $('#page_0').html('');
                require.async(['static/scripts/index/activeStatistics/activeStatisticsChart'], function (module) {
                    module({
                        url: '/statistics/active/queryUserActiveInfo',
                        column: [{
                            data: 'userName',
                            title: '公众账号'
                        }, {
                            data: 'articleNum',
                            title: '发布文章数'
                        }, {
                            data: 'activityNum',
                            title: '发布活动数'
                        }, {
                            data: 'activeDayNum',
                            title: '发布文章/活动天数'
                        }, {
                            data: 'activeDayNum',
                            title: '活跃等级'
                        }]
                    });
                });
            },
            /**
             * 总体情况
             */
            overallSituation: function () {
                $('#page_0').html('');
                require.async(['static/scripts/index/overallSituation/treeSituation'], function (module) {
                    Nav.treeMenuEntry(module);
                });
            },
            /**
             * 活动统计
             */
            activityStatistics: function () {
                $('#page_0').html('');
                require.async(['static/scripts/index/articleOrActivity/statistics'], function (module) {
                    module({
                        url: '/statistics/fans/queryOpFans?hhjh',
                        column: [{
                            data: 'accountName',
                            title: '公众账号'
                        }, {
                            data: 'eventName',
                            title: '活动名称'
                        }, {
                            data: 'checkTime',
                            title: '审核时间'
                        }, {
                            data: 'pushNumber',
                            title: '推送人数'
                        }, {
                            data: 'signNumber',
                            title: '报名人数'
                        }]
                    });
                });
            }
        },
        getTreeDataSuccess: function () {
            $('.mask').fadeOut();
            Nav.changeSection('tmpl_0');
        },
        getTreeDataError: function (ret, msg) {
            $('.mask').fadeOut();
            var tipMsg = '';
            var clickHandle = null;
            if ('0100010' === ret.rtnCode) {
                tipMsg = msg;
            } else {
                tipMsg = ret.msg;
            }
            if ('0100014' === ret.rtnCode) {
                clickHandle = function () {
                    window.location.href = UrlConfig.login;
                }
            }

            message({
                title: '温馨提示',
                msg: tipMsg,
                type: 'alert',
                clickHandle: clickHandle
            });
        },
        treeMenuEntry: function (module, msg, getTreeDataURL, listGridModule) {
            try {
                $('.mask').fadeIn();
                setTimeout(function () {
                    $('.mask').fadeOut();
                }, 5000);
                var options = {
                    succ: this.getTreeDataSuccess,
                    err: function (ret) {
                        Nav.getTreeDataError(ret, msg);
                    },
                    getTreeDataURL: getTreeDataURL || '',
                    listGridModule: listGridModule || null
                };
                module(options);
            } catch (e) {
            }
        },
        changeSection: function (tmplId) {
            $('#page_0').html($('#' + tmplId).html());
        },
        /**
         * 获取菜单数据并初始化
         * @param {Object} contentId
         * @param {Object} rootClassName
         */
        getNavData: function (contentId, rootClassName) {
            var that = this;
            $.get(UrlConfig.getMenuTree + '&systemCode=' + systemCode, function (data) {
                if ('0000000' === data.rtnCode) {
                    console.info('一级大菜单:', data);
                    var menuData = data.bizData.treeBeanList;
                    var htmlStr = that.initNav(menuData, rootClassName);
                    $('#' + contentId).html(htmlStr);
                    that.initFisrt();
                    that.addEvent();
                } else {
                    var clickHandle = null;
                    if ('0100014' === data.rtnCode || '0100015' === data.rtnCode) {
                        clickHandle = function () {
                            window.location.href = UrlConfig.login;
                        }
                    }
                    message({
                        title: '温馨提示',
                        msg: data.msg,
                        type: 'alert',
                        clickHandle: clickHandle
                    });
                }
            });
        },
        initNav: function (data, className, flag) {
            this.navStr.push('<ul class="' + className + '">');
            var classNames = ['fa-tachometer', 'fa-list', 'fa-pencil', 'fa-th-large', 'fa-desktop', 'fa-play-circle',
                'fa-bar-chart-o', 'fa-tint', 'fa-th', 'fa-star', 'fa-bookmark'
            ];
            for (var i = 0, len = data.length; i < len; i++) {
                var children = data[i].children || [];
                if (children.length > 0) {
                    var parentIcon = '';
                    if ('menu' === className) {
                        parentIcon = '<i class="fa ' + classNames[i] + '"></i>';
                    }
                    this.navStr.push('<li data-code="' + data[i].leafIcon + '" class="dropdown">');
                    this.navStr.push('<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">' + parentIcon +
                        data[i].name + '<b class="fa fa-plus dropdown-plus"></b>' +
                        '</a>');
                    this.initNav(children, 'dropdown-menu', true);
                    this.navStr.push('</li>');
                } else {
                    var parentIcon = '';
                    if ('menu' === className) {
                        parentIcon = '<i class="fa ' + classNames[i] + '"></i>';
                    }
                    var fa = flag ? '<i class="fa fa-caret-right"></i>' : '';
                    this.navStr.push('<li data-id="' + data[i].id + '" data-code="' + data[i].leafIcon + '"><a href="javascript:void(0)">' + parentIcon + fa + data[i].name + '</a></li>');
                }
            }
            this.navStr.push('</ul>');
            //console.info('一级菜单Dom',this.navStr.join(''));
            return this.navStr.join('');
        },
        /**
         * 第一次初始化菜单
         */
        initFisrt: function () {
            var firstLi = $("#navigation .menu > li").first();
            firstLi.addClass('active');
            if (firstLi.hasClass('dropdown')) {
                firstLi.addClass('open');
                firstLi.find('li').first().addClass('resource active');
                this.sendredirectByCode(firstLi.find('li').first());
                return;
            } else {
                firstLi.addClass('resource');
            }
            this.sendredirectByCode(firstLi);
        },
        /**
         * 通过菜单code做相应的逻辑
         * @param {Object} ele
         */
        sendredirectByCode: function (ele) {
            var code = ele.attr('data-code');
            console.log();
            if (typeof this.menuCode[code] === 'function') {
                this.menuCode[code]();
            }
            console.info('菜单code', code);
        },
        /**
         * 给菜单注册事件
         */
        addEvent: function () {
            var that = this;
            $("#navigation .menu > li").on('click', function (e) {
                $(this).siblings().removeClass('open');
                $("#navigation .menu li").removeClass('resource');
                $('#navigation .dropdown-menu > li').removeClass('resource active');
                if ($(this).hasClass('dropdown')) {
                    if (!$(this).hasClass("open")) {
                        $(this).addClass('open');
                        var fisrtLi = $(this).find('li').first();
                        fisrtLi.addClass('resource active');
                        that.sendredirectByCode(fisrtLi);
                    } else {
                        $(this).removeClass('open');
                        $(this).find('li').removeClass('active');
                        $(this).find('li').removeClass('resource');
                    }
                } else {
                    $(this).addClass('resource');
                    that.sendredirectByCode($(this));
                }

                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings().removeClass('resource active');
                }
                e.stopPropagation();
            });
            $('#navigation .dropdown-menu > li').on('click', function (e) {
                if (!$(this).hasClass('resource active')) {
                    $(this).addClass('resource active').siblings().removeClass('resource active');
                    that.sendredirectByCode($(this));
                }
                ;
                e.stopPropagation();
            });

            $("#navigation .menu > li").hover(function () {
                $(this).addClass("hovered");
                $("#sidebar").addClass("open")
            }, function () {
                $(this).removeClass("hovered");
                $("#sidebar").removeClass("open")
            });
        },
        resize: function () {
            widthLess1024();
            widthLess768();
        }
    };

    module.exports = {
        resize: function () {
            Nav.resize();
        },
        init: function (contentId, rootClassName) {
            Nav.getNavData(contentId, rootClassName);
        }
    };
});