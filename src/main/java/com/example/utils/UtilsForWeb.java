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
        return categories[category];
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
        return subcategories[category].length;
    }

    public static String getSubcategoryName(int category, int subcategory) {
        return subcategories[category][subcategory];
    }

    public static String getCategoryUrl(int category) {
        String[] urls = {"../resources/img/icons/rest.png", "../resources/img/icons/science.png", "../resources/img/icons/history.png",
                "../resources/img/icons/art.png", "../resources/img/icons/quests.png", "../resources/img/icons/extreme.png",
                "../resources/img/icons/production.png", "../resources/img/icons/gastronomy.png"};
        return urls[category];
    }

    public static String getCity(int city, int language) {
        String[] ruRussia = {"Москва", "Санкт-Петербург", "Сочи", "Казань", "Калининград", "Нижний Новгород", "Краснодар", "Ярославль", "Иркутск"};//Kislovodsk, vologda
        String[] enRussia = {"Moscow", "St. Petersburg", "Sochi", "Kazan", "Kaliningrad", "Nizhny Novgorod", "Krasnodar", "Yaroslavl", "Irkutsk"};
        if (language == 0) return ruRussia[city];
        else return enRussia[city];
    }

    public static String getCountryUrl(int country){
        String[] countries = {"../resources/img/icons/russia-ico.png"};
        return countries[country];
    }

    public static String getCityUrl(int country, int city) {
        String[] citiesOfRussia = {"../resources/img/icons/moscow-ico.png", "../resources/img/icons/spb-ico.png", "../resources/img/icons/sochi-ico.png",
                "../resources/img/icons/kazan-ico.png", "../resources/img/icons/kaliningrad-ico.png", "../resources/img/icons/nizhny-novgorod-ico.png",
                "../resources/img/icons/krasnodar-ico.png", "../resources/img/icons/yaroslavl-ico.png", "../resources/img/icons/irkutsk-ico.png"};
        String[] citiesOfItaly = {"Moscow", "St. Petersburg", "Sochi", "Kazan", "Kaliningrad", "Nizhny Novgorod", "Krasnodar", "Yaroslavl", "Irkutsk"};
        switch (country) {
            case 0: return citiesOfRussia[city];
            default: return citiesOfRussia[city];
        }
    }

    public int getCitiesCount(int country){
        switch (country) {
            case 0: return 9;
            default: return 9;
        }
    }

    public int getCountriesCount(){
        return 1;
    }

    public static String getTheCaseOfANumeral(int type, int number){
        int cases[] = {2, 0, 1, 1, 1, 2};
        String[] titles = new String[]{"экскурсия", "экскурсии", "экскурсий"};
        String[] solve;
        switch (type){
            case 0: solve = titles; break;
            default: solve = titles; break;
        }
        return solve[ (number%100>4 && number%100<20)? 2 : cases[(number%10<5)?number%10:5]];
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
