jQuery(document).ready(function($){
    
    // jQuery sticky Menu
    
	$(".mainmenu-area").sticky({topSpacing:0});

    // Bootstrap Mobile Menu fix
    $(".navbar-nav li a").click(function(){
        $(".navbar-collapse").removeClass('in');
    });    
    
    // jQuery Scroll effect
    var scroll = new SmoothScroll('a[href*="#"]', {header: '.nav-offset'});

    //
    $("#phone_number").mask("+7 (999) 999-9999");
    $("#phone_callback").mask("+7 (999) 999-9999");


    // Carousel in index
    $('.owl-carousel').owlCarousel({
        loop:true,
        margin:0,
        nav:true,
        items:1,
        autoplay:true,
        autoplayTimeout:4000,
        navText: ["<i class='fa fa-chevron-left'></i>","<i class='fa fa-chevron-right'></i>"]
    });

    // Bootstrap ScrollPSY
    $('body').scrollspy({ 
        target: '.navbar-collapse',
        offset: 95
    });

    $('#modalPrices').on('show.bs.modal', function (event) {
        var item = $(event.relatedTarget);
        var name = item.data('name');
        var modal = $(this);
        modal.find('#modal-title').text(name);
    });

    $('#modalWidget').on('show.bs.modal', function (event) {
        var phone = $('#phone_top').val();
        $.get("/orders/widget_html", {phone: phone},
            onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            alert(data);
        }
        var button = $(event.relatedTarget);
        var modal = $(this);
        modal.find('#phone_widget').val(phone);
    });

    $('#find_modal').click(function () {
        var phone = $('#phone_widget').val();
        $.get("/orders/widget_html", {phone: phone},
            onAjaxSuccess
        );
        function onAjaxSuccess(data) {
            $('#for_widget_response').html(data);
        }
    });

});

