$(document).ready(function () {
    load();
});

var allPriceTree;
var selectPriceTree;

var treeData;

function load() {
    webix.ready(function () {
        init();
    })
}


function isLastChild(id) {
    for (var i in treeData) {
        if (treeData[i].parentId == id) {
            return false;
        }
    }
    return true;
}


function mainContainerShow() {
    if ($$("editOrderLayot"))
        $$("editOrderLayot").hide();

    if ($$("mainlayot")) {
        $$("mainlayot").show();
        $$("viewOrderTableWrap").hide();
        $$("ordersTableWrap").show();
    }
    $("#editApp").hide();
    $("#createApp").hide();
    $("#viewApp").hide();
}


function init() {

    var layout = webix.ui({
        id: "mainlayot",
        container: "mainContainer",
        rows: [
            {
                id: "ordersTableWrap",
                autowidth: true,
                minWidth: 450,
                margin: 10,
                rows: [
                    {
                        cols: [
                            {},
                            {
                                view: "button",
                                label: "Создать новую заявку",
                                width: 200,
                                click: function () {
                                    editOrder()
                                }
                            }
                        ]
                    },
                    {
                        view: "datatable",
                        id: "ordersTable",
                        minHeight: 500,
                        drag: false,
                        scroll: 'y',
                        hover: "appTableHover",
                        url: "/aoz/wr/kitchen/getOrdersList",
                        rowHeight: 40,
                        columns: [
                            {
                                header: " ",
                                css: "nonePadding",
                                width: 38,
                                template: "<span class='#button# fa #icon#'  ></span>"
                            },
                            {id: "id", header: "id", width: 120, hidden: true},
                            {template: "Заявка № #id#", header: "Наименование", fillspace: 1},
                            {id: "begDate", header: "Начало", width: 120},
                            {id: "endDate", header: "Окончание", width: 120}
                        ],
                        scheme: {
                            $init: function (obj) {
                                obj.icon = " fa-eye";
                                obj.button = "viewOrder";
                                if (isNullOrEmpty(obj.endDate)) {
                                    obj.icon = " fa-pencil-square-o ";
                                    obj.button = "editOrder";
                                }
                            }
                        },
                        on: {
                            onAfterLoad: function () {
                                this.hideOverlay();
                                if (!this.count())
                                    this.showOverlay("<span class='no_data_found'>Нет данных для отображения</span>");

                            },
                            onBeforeLoad: function () {
                                this.showOverlay('<h5><i class="fa fa-spinner fa-spin"></i><span>&nbsp;Пожалуйста подождите, идет загрузка данных...</span></h5>');
                            }
                        },
                        onClick: {
                            viewOrder: function (e, item, cell) {
                                var id = item.row;
                                var item = this.getItem(id);
                                viewOrder(item, id);
                            },
                            editOrder: function (e, item, cell) {
                                var id = item.row;
                                var item = this.getItem(id);
                                editOrder(item, id);
                            }
                        },
                        pager: {
                            container: "ordersTablePaging",
                            size: 15,
                            group: 10
                        }
                    }, {
                        id: "ordersTablePaging",
                        view: "template",
                        height: 38,
                        content: "ordersTablePaging"
                    }
                ]
            },
            {
                id: "viewOrderTableWrap",
                autowidth: true,
                hidden: true,
                minWidth: 450,
                margin: 10,
                rows: [
                    {
                        view: "datatable",
                        id: "viewOrderTable",
                        height: 500,
                        drag: true,
                        scroll: 'y',
                        hover: "appTableHover",
                        rowHeight: 40,
                        columns: [
                            {id: "id", header: "Код", width: 120, hidden: true},
                            {id: "code", header: "Код", width: 120},
                            {id: "name", header: "Наименование", fillspace: 1}
                        ],
                        on: {
                            onAfterLoad: function () {
                                this.hideOverlay();
                                if (!this.count())
                                    this.showOverlay("<span class='no_data_found'>Нет данных для отображения</span>");

                            },
                            onBeforeLoad: function () {
                                this.showOverlay('<h5><i class="fa fa-spinner fa-spin"></i><span>&nbsp;Пожалуйста подождите, идет загрузка данных...</span></h5>');
                            }
                        },
                        pager: {
                            container: "viewOrderTablePaging",
                            size: 15,
                            group: 10
                        }
                    }, {
                        id: "viewOrderTablePaging",
                        view: "template",
                        height: 38,
                        content: "viewOrderTablePaging"
                    }
                ]
            }
        ]
    });

    webix.event(window, "resize", function () {
        layout.adjust();
    });

}


function editOrderLayotCreate(id) {
    var layout = webix.ui({
        id: "editOrderLayot",
        container: "editOrderContainer",
        cols: [
            {
                autowidth: true,
                minWidth: 450,
                margin: 10,
                rows: [
                    {
                        view: "treetable",
                        select: 'row',
                        scroll: "y",
                        id: "allPriceTree",
                        autoheight: true,
                        minHeight: 500,
                        columns: [
                            {
                                id: "id",
                                header:" ",
                                width: 10,
                                hidden:true
                            },{
                                id: "name",
                                header: ["Наименование", {content: "textFilter"}],
                                sort: "string",
                                fillspace: 1,
                                template: "{common.space()} {common.icon()} {common.folder()} #name#"
                            },
                            {
                                header: " ",
                                css: "nonePadding",
                                width: 30,
                                id: "angle"
                            }
                        ],
                        scheme: {
                            $init: function (obj) {
                                if (isLastChild(obj.code) && !isNullOrEmpty(obj.parentId)) {
                                    obj.angle = "<span class='angleRight fa fa-angle-right'  ></span>"
                                } else {
                                    obj.angle = "";
                                }
                            }
                        },
                        onClick: {
                            angleRight: function (e, item, cell) {
                                var id = item.row;
                                var obj = this.getItem(id);
                                angleRightClick(obj, id)
                            }
                        }
                    }
                ]
            }, {view: "resizer"},
            {
                autowidth: true,
                minWidth: 450,
                margin: 10,
                rows: [
                    {
                        view: "treetable",
                        select: 'row',
                        scroll: "y",
                        id: "selectPriceTree",
                        autoheight: true,
                        editable: true,
                        minHeight: 500,
                        columns: [
                            {
                                id: "id",
                                header:" ",
                                width: 10,
                                hidden:true
                            },
                            {
                                header: "<span class='angleLeft fa fa-angle-double-left' onclick='angleDoubleLeftClick()'></span>",
                                css: "nonePadding",
                                width: 30,
                                template: "<span class='angleLeft fa fa-angle-left'  ></span>"
                            },
                            {
                                id: "name",
                                header: ["Наименование", {content: "textFilter"}],
                                sort: "string",
                                fillspace: 1,
                                template: "{common.space()} {common.icon()} {common.folder()} #name#"
                            },
                            {
                                id: "count",
                                header: " ",
                                width: 50,
                                editor: "text",
                                suggest: [
                                    {
                                        on: {
                                            onChange: function (nV, oV) {
                                                console.log(nV, oV)
                                            }
                                        }
                                    }
                                ]
                            },
                            {
                                header: " ",
                                width: 30,
                                template:"#unit.name#"
                            }
                        ],
                        onClick: {
                            angleLeft: function (e, item, cell) {
                                console.log(e, item, cell);
                                var id = item.row;
                                var obj = this.getItem(id);
                                angleLeftClick(obj, id)
                            }
                        }
                    }
                ]
            }
        ]
    });

    allPriceTree = $$("allPriceTree");
    selectPriceTree = $$("selectPriceTree");
    webix.event(window, "resize", function () {
        layout.adjust();
    });
    loadDataTree(id);
}

function loadDataTree(id) {
    get_ajax('/aoz/wr/kitchen/getVProductList', 'GET', {id: id}, function (gson) {
        if (gson && gson.data) {
            treeData = gson.data;
            loadTreeData('allPriceTree', getTreeData(gson.data, 'code', 'parentId'));
        }
    });
}

function viewOrder(item, id) {
    $("#createApp").hide();
    $("#editApp").hide();
    $("#viewApp").show();
    $$("viewOrderTableWrap").show();
    $$("ordersTableWrap").hide();
    $$("viewOrderTable").define("url", "/aoz/wr/kitchen/getOrderListById?id=" + id);
}

function editOrder(item, id) {
    $$("mainlayot").hide();
    if (id) {
        $("#editApp").show();
        $("#createApp").hide();
    } else {
        $("#editApp").hide();
        $("#createApp").show();
    }
    $("#viewApp").hide();
    editOrderLayotCreate(id);
}

function angleRightClick(item, id) {
    allPriceTree.remove(id);
    selectPriceTree.parse(item);
}

function angleLeftClick(item, id) {
    selectPriceTree.remove(id);
    allPriceTree.parse(item);
}

function angleDoubleLeftClick() {
    console.log("angleDoubleLeftClick")
}