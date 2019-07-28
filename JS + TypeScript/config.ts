import * as moment from "moment";
require(`moment/locale/ru`);

const locale = "ru";
moment.locale(locale);

export default {
  backend: 'http://snowandice.herokuapp.com',
  mapCenter: {
    lat: 52.29778,
    lng: 104.29639
  },
  locale,
}
