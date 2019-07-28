export const CONSTANTS = {
  "LOCALE": "LOCALE",
  "NAMESPACE_PREFIX": "PIRS",
};

export class LocalStorageService {
  constructor(namespacePrefix = CONSTANTS.NAMESPACE_PREFIX, keysPropertyName = 'settings.keys') {
    this._namespacePrefix = namespacePrefix + '.';
    this._keysPropertyName = this._namespacePrefix + keysPropertyName;
    this._storedKeys = localStorage.getItem(this._keysPropertyName) || '';
    this._values = {};
    if (this._storedKeys) {
      this._storedKeys
        .split(',')
        .forEach( k => {
          try {
            this._values[k] = JSON.parse(localStorage.getItem(this._namespacePrefix + k) || '').value;
          } catch (e){
            this._values[k] = localStorage.getItem(this._namespacePrefix + k)
          }
        });
    }
  }

  private readonly _namespacePrefix: string;
  private readonly _keysPropertyName: string;
  private readonly _storedKeys: string;
  private _values: {[k: string]: any};

  get keys() {
    return Object.keys(this._values);
  }

  set(key: string, value: any) {
    const targetKey = this._namespacePrefix + key;
    this._values[key] = value;
    localStorage.setItem(targetKey, JSON.stringify({value: value}));
    localStorage.setItem(this._keysPropertyName, Object.keys(this._values).reduce((a, b) => a + ',' + b));
  }

  get(key: string) {
    return this._values[key];
  }

  reset() {
    this.keys.forEach(k => localStorage.removeItem(k));
    this._values = {};
  }
}

export default new LocalStorageService();