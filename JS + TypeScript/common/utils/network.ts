import { fetch, addTask } from 'domain-task';
import { RequestOptions } from 'http';

interface RequestOptionsWithBody extends RequestOptions{
  body?: any,
  cache: RequestCache
}

export module Network {
  export class ServerError extends Error {
    serverError: object;
    status: number;
  }

  enum methods {
    POST = 'POST',
    GET = 'GET',
    PUT = 'PUT',
    DELETE = 'DELETE',
    PATCH = 'PATCH'
  }

  export function callApi<T = any>(url: string, options: RequestOptionsWithBody): Promise<T> {
    const isFormData = options.body instanceof FormData;

    if (options){
      if (!options.headers){
        options.headers = {}
      }

      if(!isFormData){
        options.headers['Accept'] = options.headers['Accept'] || 'application/json';
        options.headers['Content-Type'] = 'application/json';
      }

      options.body = isFormData ? options.body : JSON.stringify(options.body);
    }

    // @ts-ignore
    const fetchTask = fetch(url, options)
      .then<T>(async (response: any) => {
        if (response.ok){
          let json = {};
          try{
            json = await response.json();
          } catch(e){

          }
          return json;
        } else if (String(response.status).startsWith('4')) {
          const requestError = new ServerError('Bad Request.');
          try{
            requestError.serverError = await response.json();
            requestError.status = response.status
          } catch (e) {}
          throw requestError;
        }
        else if (String(response.status).startsWith('5')) {
          const requestError = new ServerError('Server Error.');
          try {
            requestError.serverError = await response.json();
            requestError.status = response.status
          } catch (e){}
          throw requestError;
        }
        else {
          throw new Error('Unknown error.')
        }
      });

    addTask(fetchTask); // Ensure server-side prerendering waits for this to complete

    return fetchTask;
  }

  export class HttpClient{

    callApi<T = any>(url: string, options: RequestOptionsWithBody): Promise<T> {
      return callApi(url, options)
    }

    post<T>(url: string, body: any) {
      return this.callApi<T>(url, {method: methods.POST, cache: 'no-cache', body})
    }

    put<T>(url: string, body: object) {
      return this.callApi<T>(url, {method: methods.PUT, cache: 'no-cache', body})
    }

    patch(url: string, body: object) {
      return this.callApi(url, {method: methods.PATCH, cache: 'no-cache', body})
    }

    delete(url: string, body: object) {
      return this.callApi(url, {method: methods.DELETE, cache: 'no-cache', body})
    }

    get<T = any>(url: string, query: any = {}) {
      const queryParams = Object.keys(query).reduce((queryString, queryKey) => {
        if (queryString.length)
          queryString += '&';

        let value = query[queryKey];
        if (value instanceof Date) value = value.toUTCString();

        queryString += `${queryKey}=${value}`;
        return queryString;
      }, '');
      return this.callApi<T>(url + '?' + queryParams, {method: methods.GET, cache: 'no-cache'})
    }
  }
}

