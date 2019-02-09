package com.example.utils;

import com.example.cart.CartInfo;

import javax.servlet.http.HttpServletRequest;

public class UtilsForWeb {

    public static Consts consts = new Consts();
    private static String[] categories = {"Смартфоны и сотовые телефоны", "Аудио/Видео/ТВ", "Автотовары", "Компьютеры", "Ноутбуки", "Инструменты", "Оргтехника", "Расходные материалы", "Радиодетали", "Пиротехника"};
    private static String[][] subcategories = {
            /*смартфоны и сотовые телефоны*/{"Смартфоны", "Сотовые телефоны", "Чехлы", "Зарядные устройства", "Наушники", "Аккумуляторы", "Защитные плёнки", "Защитные стёкла", "Брелки, стилусы", "Комплектующие"},
            /*аудио/видео/тв*/{"Телевизоры", "Пульты ДУ", "Спутниковое ТВ", "Цифровые ресиверы", "Радиоприемники", "Фототехника", "Плееры", "Портативные колонки", "Комплектующие"},
            /*автотовары*/{"Магнитолы", "GPS навигаторы", "Акустика", "Антенны", "Видеорегистраторы", "Инверторы", "Автодержатели", "Другое"},
            /*компьютеры*/{"Компьютеры", "Мониторы", "Переферия", "Комплектующее", "Программное обеспечение", "Сетевое оборудование", "Шнуры/кабели"},
            /*ноутбуки*/{"Ноутбуки", "Аксессуары", "Сумки и чехлы", "Аккумуляторы", "Адаптеры и блоки питания", "Комплектующие"},
            /*инструменты*/{"Измерительные инструменты", "Инструменты для ремонта", "Мультиметры", "Отвертки индикаторы", "Паяльное оборудывание"},
            /*оргтехника*/{"Принтеры и МФУ", "Проекторы", "Сетевые фильтры и ибп", "Телефоны проводные"},
            /*расходные материалы*/{"Картриджи, краски и тонеры", "Чистящие средства", "Элементы питания"}
    };

    public static String getCategoryName(int category) {
        return category>=0?categories[category]:"категория_не_задана";
    }

    public static String[] getCategories(){
        return categories;
    }

    public static String[] getSubcategories(int category){
        return subcategories[category];
    }

    public static int getCategoriesCount(){
        return categories.length;
    }

    public static int getSubcategoriesCount(int category){
        if (category>=categories.length) return 1;
        return subcategories[category].length;
    }

    public static String getSubcategoryName(int category, int subcategory) {
        return category>=0&&subcategory>=0?subcategories[category][subcategory]:"подкатегория_не_задана";
    }

    public static String getCategoryUrl(int category) {
        String[] urls = {"../resources/img/icons/rest.png", "../resources/img/icons/science.png", "../resources/img/icons/history.png",
                "../resources/img/icons/art.png", "../resources/img/icons/quests.png", "../resources/img/icons/extreme.png",
                "../resources/img/icons/production.png", "../resources/img/icons/gastronomy.png"};
        return urls[category];
    }

    public int productsCount(){
        return 10;
    }

    public int getProductsCount(int page, int productsSize){
        return (page+1)*productsCount()<productsSize?(page+1)*productsCount():productsSize;
    }

    public static CartInfo getCartInSession(HttpServletRequest request) {

        // Get Cart from Session.
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");

        // If null, create it.
        if (cartInfo == null) {
            cartInfo = new CartInfo();

            // And store to Session.
            request.getSession().setAttribute("myCart", cartInfo);
        }

        return cartInfo;
    }

    public static int getCartTotals(HttpServletRequest request){
        return getCartInSession(request).getAmountTotal();
    }

}
