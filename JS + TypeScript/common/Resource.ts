import { Api } from "./Api";

export type IKey = string | number;

interface WithProps{[key: string]: any}

export interface Model extends WithProps{
  id?: IKey
}

export class Resource<TModel extends Model> {
  constructor(api: Api, path: string, name?: string){
    this.api = api;
    this.path = path.endsWith('/') ? path : path + '/';
    this.name = name || path;
  }

  api: Api;
  path: string;
  name: string;

  query: (queryParams?: any) => Promise<TModel[]> = async (queryParams) => {
    return await this.api.get<TModel[]>(this.path, queryParams)
  };

  get: (id: IKey) => Promise<TModel> = async (id) => {
    return await this.api.get<TModel>(this.path + id)
  };

  create = (model: TModel) => {
    return this.api.post<TModel>(this.path, model)
  };

  update = (model: TModel) => {
    return this.api.put<TModel>(this.path + model.id, model)
  };

  delete = (model: TModel) => {
    return this.api.delete(this.path + model.id, {})
  };
}

export default Resource
