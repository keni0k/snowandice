import { AnyAction, Reducer } from 'redux';
import { createReducer, createAction, Action } from 'redux-act';
import { AppThunkAction } from "./index";
import { Credentials } from "../common/Api";
import { StreetsApi } from "./StreetsApi";

export const setApiAction = createAction<StreetsApi>('SET_API');
export const setClientInitializedAction = createAction<boolean>('SET_CLIENT_INITIALIZED');
export const setAuthenticationError = createAction<string | undefined>('SET_AUTHENTICATION_ERROR');

export interface ApplicationState {
  clientInitialized: boolean,
  authenticationError?: string,
  api: StreetsApi,
}

export const actionCreators = {
  logout: (): AppThunkAction<AnyAction> => async (dispatch, getState) => {

  },
  login: (credentials: Credentials): AppThunkAction<Action<any>> => async (dispatch, getState) => {

  },
};

const unloadedState: ApplicationState = {clientInitialized: false, api: new StreetsApi('')};

export const reducer = createReducer<ApplicationState>(function (on) {
  on(setClientInitializedAction,  (state, payload: boolean) => {
    return {...state, clientInitialized: payload};
  });
  on(setApiAction,  (state: ApplicationState, payload) => {
    return {...state, api: payload};
  });
  on(setAuthenticationError,  (state, payload) => {
    return {...state, authenticationError: payload};
  });
}, unloadedState) as Reducer<ApplicationState>;
