dojo.provide("encuestame.org.class.commons.dashboard.chart.DashboardColumn2D");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.class.commons.dashboard.chart.DashboardColumn2D",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.dashboard.chart", "template/dashboardColumn2D.inc"),

        widgetsInTemplate: true,

        postMixInProperties: function(){

        },

        postCreate: function() {

        },
    }
);