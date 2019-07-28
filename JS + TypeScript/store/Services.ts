import { Reducer } from 'redux';
import { createReducer, createAction } from 'redux-act';
import "reflect-metadata";

export const addService = createAction<AddServicePayload<IService>>('ADD_SERVICE');

export interface IService {

}

export type AddServicePayload<TService> = {
  service: TService
}

export interface ServicesState {
}

const unloadedState: ServicesState = {};

export const reducer = createReducer<ServicesState>(function (on) {
  on(addService,  (state: ServicesState, servicePayload: AddServicePayload<IService>) => {
    const params = Reflect.getMetadata('design:paramtypes', servicePayload.service);
    console.log(params);
    return {};
  });
}, unloadedState) as Reducer<ServicesState>;
