import { Reducer } from 'redux';
import { createReducer, createAction } from 'redux-act';
import { push, RouterAction } from 'react-router-redux';
import { AppThunkAction } from './';

export const setApplicationHeaderAction = createAction<string>('SET_APPLICATION_HEADER');

export interface ApplicationHeaderState {
  headerText: string
}

export const actionCreators = {
  push: (url: string): AppThunkAction<RouterAction> => (dispatch) => {
    dispatch(push(url));
  },
};

const unloadedState: ApplicationHeaderState = {headerText: ''};

export const reducer = createReducer<ApplicationHeaderState>(function (on) {
  on(setApplicationHeaderAction,  (state: ApplicationHeaderState, header) => {
    return {headerText: header};
  });
}, unloadedState) as Reducer<ApplicationHeaderState>;