import { Network } from "./utils/network";
import HttpClient = Network.HttpClient;

export interface Credentials {login: string, password: string}

export class Api extends HttpClient{
  constructor(hostUrl: string, credentials?: Credentials){
    super();

    this.host = hostUrl.endsWith('/') ? hostUrl.substring(0, hostUrl.length - 1) : hostUrl;
    this.credentials = credentials;
  }

  callApi<T = any>(url: string, options: any){
    options.headers = options.headers || {};

    if(this.credentials){
      options.headers['Authorization'] = 'Basic ' + btoa(`${this.credentials.login}:${this.credentials.password}`);
    }

    return super.callApi<T>(this.host + (url.startsWith('/') ? url : '/' + url), options);
  }

  host: string;
  private readonly credentials?: Credentials;
}
