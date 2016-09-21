
var rule_prefix = "use_rule";

$(document).ready(function(){
    initStyle();

    $.ajax({
        type: "GET",
        url: "/rule/QUERYALL",
        data: {},
        dataType: "json",
        success: function(data){
            var table = "";
            $.each(data , function(idx, obj) {
                table +=
                    "<tr>" +
                    "<td class='ruleindex'>" + obj.ruleId + "</td>" +
                    "<td>" + obj.ruleName + "</td>" +
                    "<td>" + obj.script + "</td>" +
                    "<td> <button id='" + rule_prefix +  obj.ruleId + "'>reload</button> </td>" +
                    "</tr>"
            });

            $('#my-form').append(table);

            bindingClickEvent(data);
        }
    });
});

function initStyle(){
    var t9 = new PopupLayer({trigger:"#ele9",popupBlk:"#blk9",closeBtn:"#close9",useOverlay:true,useFx:true,
        offsets:{
            x:0,
            y:-41
        }
    });
    t9.doEffects = function(way){
        if(way == "open"){
            this.popupLayer.css({opacity:0.3}).show(400,function(){
                this.popupLayer.animate({
                    left:($(document).width() - this.popupLayer.width())/2,
                    top:(document.documentElement.clientHeight - this.popupLayer.height())/2 + $(document).scrollTop(),
                    opacity:0.8
                },1000,function(){this.popupLayer.css("opacity",1)}.binding(this));
            }.binding(this));
        }
        else{
            this.popupLayer.animate({
                left:this.trigger.offset().left,
                top:this.trigger.offset().top,
                opacity:0.1
            },{duration:500,complete:function(){this.popupLayer.css("opacity",1);this.popupLayer.hide()}.binding(this)});
        }
    }

};

function bindingClickEvent(data){
    $.each(data , function(idx, obj) {
        var rule_button_id = rule_prefix + obj.ruleId;
        $('#' + rule_button_id).bind("click",function(){
            $.ajax({
                type: "GET",
                url: "/rule/RELOAD",
                data: {
                    "ruleIndex" : $(this).parent().siblings('.ruleindex').html()
                },
                dataType: "json",
                success: function(data){

                    alert("data");

                }
            });
        });
    });
};

function commitRule (){
    $.ajax({
        type: "GET",
        url: "/rule/ADD",
        data: {
            "ruleName" : $('#ruleName').val(),
            "script" : $('#rulrScript').val()
        },
        dataType: "json",
        success: function(data) {

            alert(data);
            if(data == 1) {
                location.reload();
            }
        }
    });
};
