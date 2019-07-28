import config from "../config";
const locales: any = {
  ru: require('./ru').default
};
const translation = locales[config.locale];
export default translation;