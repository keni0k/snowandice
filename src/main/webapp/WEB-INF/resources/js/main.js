jQuery(document).ready(function ($) {

    // jQuery sticky Menu
    $(".mainmenu-area").sticky({topSpacing: 0});

    // Bootstrap Mobile Menu fix
    $(".navbar-nav li a").click(function () {
        $(".navbar-collapse").removeClass('in');
    });

    // jQuery Scroll effect
    var scroll = new SmoothScroll('a[href*="#"]', {header: '.nav-offset'});

    // blazy download images
    var bLazy = new Blazy();

    //
    $("#phone_number").mask("+7 (999) 999-9999");
    $("#phone_callback").mask("+7 (999) 999-9999");


    // Carousel in index
    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 0,
        nav: true,
        items: 1,
        autoplay: true,
        autoplayTimeout: 4000,
        navText: ["<i class='fa fa-chevron-left'></i>", "<i class='fa fa-chevron-right'></i>"]
    });

    // Bootstrap ScrollPSY
    $('body').scrollspy({
        target: '.navbar-collapse',
        offset: 95
    });

    $('#modalPrices').on('show.bs.modal', function (event) {
        var item = $(event.relatedTarget);
        var name = item.data('name');
        var enName = item.data('en');
        $('.lds-ellipsis').css("display", "block");
        $('#for_ajax').html('');
        $.get("/fix", {name: enName})
            .done(function (msg) {
                $('.lds-ellipsis').css("display", "none");
                $('#for_ajax').html(msg);
            })
            .fail(function (xhr, status, error) {
                $('.lds-ellipsis').css("display", "none");
                $('#for_ajax').html('<b>Информация  будет добавлена позже. Вы можете позвонить нам или заказать обратный звонок</b>');
            });
        var modal = $(this);
        modal.find('#modal-title').text(name);
    });

    $('#modalWidget').on('show.bs.modal', function (event) {
        var phone = $('#phone_top').val();
        $('.lds-ellipsis').css("display", "block");
        $.post("/orders/widget_html", {phone: phone})
            .done(function (msg) {
                $('.lds-ellipsis').css("display", "none");
                $('#for_widget_response').html(msg);
            })
            .fail(function (xhr, status, error) {
                $('.lds-ellipsis').css("display", "none");
                $('#for_widget_response').html('<h4>Произошла ошибка. Повторите запрос позже.</h4>');
            });
        var button = $(event.relatedTarget);
        var modal = $(this);
        modal.find('#phone_widget').val(phone);
    });

    $('#find_modal').click(function () {
        var phone = $('#phone_widget').val();
        $('.lds-ellipsis').css("display", "block");
        $.post("/orders/widget_html", {phone: phone})
            .done(function (msg) {
                $('.lds-ellipsis').css("display", "none");
                $('#for_widget_response').html(msg);
            })
            .fail(function (xhr, status, error) {
                $('.lds-ellipsis').css("display", "none");
                $('#for_widget_response').html('<h4>Произошла ошибка. Повторите запрос позже.</h4>');
            });
    });

// YANDEX METRICA
    (function (m, e, t, r, i, k, a) {
        m[i] = m[i] || function () {
            (m[i].a = m[i].a || []).push(arguments)
        };
        m[i].l = 1 * new Date();
        k = e.createElement(t), a = e.getElementsByTagName(t)[0], k.async = 1, k.src = r, a.parentNode.insertBefore(k, a)
    })
    (window, document, "script", "https://mc.yandex.ru/metrika/tag.js", "ym");

    ym(54206233, "init", {
        clickmap: true,
        trackLinks: true,
        accurateTrackBounce: true,
        webvisor: true
    });

});

