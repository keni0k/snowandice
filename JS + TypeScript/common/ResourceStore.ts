import { Reducer } from 'redux';
import { createReducer, createAction, SimpleActionCreator, Dispatch, Action } from 'redux-act';
import Resource, { IKey, Model } from "./Resource";
import { Api } from "./Api";

// This type can be used as a hint on action creators so that its 'dispatch' and 'getState' params are
// correctly typed to match your store.
export interface AppThunkAction<TAction, TStore = any> {
  (dispatch: Dispatch, getState: () => TStore): void;
}

export interface ResourceState<TModel extends Model> {
  map: Map<IKey, TModel>
  array: TModel[]
}

export interface ResourceActions<TModel> {
  add: SimpleActionCreator<TModel, TModel>
  addArray: SimpleActionCreator<TModel[], TModel[]>
  update: SimpleActionCreator<TModel, TModel>
  remove: SimpleActionCreator<TModel, TModel>
}

export interface ResourceActionCreators<TModel> {
  query: Function
  get: Function
  add: Function
  update: Function
  remove: Function
}

export default class ResourceStore<TModel extends Model>{
  constructor(public resourceName: string, public getApi: (state: any) => Api, public resourcePath?: string){
    this.actions = {
      add: createAction<TModel, TModel>(`ADD_${resourceName.toUpperCase()}`),
      addArray: createAction<TModel[], TModel[]>(`ADD_${resourceName.toUpperCase()}_ARRAY`),
      update: createAction<TModel, TModel>(`UPDATE_${resourceName.toUpperCase()}`),
      remove: createAction<TModel, TModel>(`REMOVE_${resourceName.toUpperCase()}`),
    };
    this.reducer = createReducer<ResourceState<TModel>>((on) => {
      on(this.actions.addArray, (state, items) => {
        const map = new Map(state.map);
        items.forEach(item => {
          map.set(item.id as IKey, item);
        });

        const array = [...state.array, ...items];
        return {
          map,
          array
        };
      });
      on(this.actions.add, (state, item) => {
        if(!item.id) throw Error('Model without id can`t be added to store');
        const map = new Map(state.map);
        map.set(item.id, item);
        const array = [...state.array, item];

        return {
          map,
          array
        };
      });
      on(this.actions.remove,  (state, item) => {
        if(!item.id) throw Error('Model without id can`t be removed from store');
        const map = new Map(state.map);
        map.delete(item.id);
        const array = state.array.filter( _ => _ !== item);

        return {
          map,
          array
        };
      });
    }, {
      map: new Map(),
      array: []
    });
    this.actionCreators = {
      query: (params: any): AppThunkAction<Action<any, TModel[]>> => async (dispatch: Dispatch, getState: () => any) => {
        const models = await this.getResource(getState).query(params);
        return dispatch(this.actions.addArray(models))
      },
      get: (id: IKey): AppThunkAction<Action<IKey, TModel>> => async (dispatch: Dispatch, getState: () => any) => {
        const model = await this.getResource(getState).get(id);
        return dispatch(this.actions.add(model))
      },
      add: (model: TModel): AppThunkAction<Action<TModel, TModel>> => async (dispatch: Dispatch, getState: () => any) => {
        const createdModel = await this.getResource(getState).create(model);
        return dispatch(this.actions.add({...model, ...createdModel}))
      },
      update: (model: TModel): AppThunkAction<Action<TModel, TModel>> => async (dispatch: Dispatch, getState: () => any) => {
        const updatedModel = await this.getResource(getState).update(model);
        return dispatch(this.actions.update({...model, ...updatedModel}))
      },
      remove: (model: TModel): AppThunkAction<Action<TModel, TModel>> => async (dispatch: Dispatch, getState: () => any) => {
        const removedModel = await this.getResource(getState).delete(model);
        return dispatch(this.actions.remove({...model, ...removedModel}))
      }
    }
  }
  getResource = (getState: Function) => {
    const api = this.getApi(getState());
    return new Resource<TModel>(api, this.resourcePath || this.resourceName, this.resourceName);
  };
  reducer: Reducer<ResourceState<TModel>>;
  actions: ResourceActions<TModel>;
  actionCreators: ResourceActionCreators<TModel>;
}
