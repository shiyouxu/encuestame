/**
 * Wipe Support.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
dojo.require("dojox.fx");
define([
     "dojo/_base/declare",
     "dojo/topic",
     "dojo/dom-class",
     "me/core/enme"],
    function(
    declare,
    domClass,
    topic,
    _ENME) {

  return declare(null, {

      /*
      * node.
      */
     node : null,
     /*
      * duration.
      */
     duration : 200,
     /*
      * height.
      */
     height : 300,

     /*
      * default status.
      */
     _open : false,

     /*
      * default group.
      */
     group : "default",

     /*
      * id.
      */
     id : "",

     /*
      * Constructor of wipe.
      */
     constructor: function(node, duration, height, group, id) {
         topic.subscribe("/encuestame/wipe/close", this, "_close");
         topic.subscribe("/encuestame/wipe/close/group", this, "_group");
         this.node = node;
         this.duration = (duration == null) ? this.duration : duration;
         this.height = (height == null) ? this.height : height;
         this.group = (group == null) ? this.group : group;
         this.id = (id == null) ? this.id : id;
     },

    /*
     * on wite in.
     */
    wipeInOne: function() {
        dojox.fx.wipeTo({
            node: this.node,
            duration: this.duration,
            height: this.height
        }).play();
    },

    /*
     * close the wipe.
     */
    _group : function(group) {
        if (group === this.group) {
            this.wipeOutOne();
            this._open = false;
        }
    },

    /*
     * close the wipe.
     */
    _close : function(id, group) {
        if (id !== this.id && group === this.group) {
            this.wipeOutOne();
            this._open = false;
        }
    },

    /*
     * on wipe out.
     */
    wipeOutOne : function() {
        if (this.node) {
            dojox.fx.wipeOut({
                node: this.node,
                duration: this.duration
            }).play();
        }
    },

    /*
     * provide toggle suport to wipe panel.
     */
    togglePanel : function(node) {
        if (this._open) {
            this.wipeOutOne();
         } else {
            this.wipeInOne();
            node == null ? null : domClass.addClass(node, "selected");
         }
         this._open =!this._open;
    }

  });
});