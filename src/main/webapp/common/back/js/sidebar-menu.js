$.sidebarMenu = function (menu) {
    var animationSpeed = 300;

    $(menu).on('click', 'li a', function (e) {
        var $this = $(this);
        var checkElement = $this.next();

        if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
            checkElement.slideUp(animationSpeed, function () {
                checkElement.removeClass('menu-open');
            });
            checkElement.parent("li").removeClass("active");
        }

        //If the menu is not visible
        else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
            //Get the parent menu
            var parent = $this.parents('ul').first();
            //Close all open menus within the parent
            var ul = parent.find('ul:visible').slideUp(animationSpeed);
            //Remove the menu-open class from the parent
            ul.removeClass('menu-open');
            //Get the parent li
            var parent_li = $this.parent("li");

            //Open the target menu and add the menu-open class
            checkElement.slideDown(animationSpeed, function () {
                //Add the class active to the parent li
                checkElement.addClass('menu-open');
                parent.find('li.active').removeClass('active');
                parent_li.addClass('active');
            });
        }
        //if this isn't a link, prevent the page from being redirected
        if (checkElement.is('.treeview-menu')) {
            e.preventDefault();
        }
    });
};

$(function() {
    var currentUrl = window.location.pathname;
    console.log(currentUrl);
    $(".nav-item-link").each(function(i, item) {
        var loca = $(item).attr("href");
        if (loca && loca.split("?")[0] === currentUrl) {
            $(item).addClass("link-active");
            $(item).parent().css("background", "#00C1DE");
            if ($(item).parent().parent().parent().hasClass("treeview")) {
                $(item).parent().parent().parent().addClass("active");
                return false;
            }
        }
    })

});
