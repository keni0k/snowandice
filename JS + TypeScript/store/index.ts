import { reducer as formReducer, FormStateMap } from 'redux-form';
import * as ApplicationHeaderStore from './ApplicationHeader';
import * as ApplicationStore from './Application';
import * as Services from './Services';
import { createBrowserHistory } from "history";
import { restoreMapsFromArrays } from "../common/utils/system";
import configureStore from "../configureStore";
import ResourceStore, { ResourceState } from "../common/ResourceStore";
import { Street } from "../models/Schema";

export const streetsResourceStore = new ResourceStore<Street>('segments', (state: RootState) => state.application.api);

// The top-level state object
export interface RootState {
  applicationHeader: ApplicationHeaderStore.ApplicationHeaderState;
  application: ApplicationStore.ApplicationState;
  services: Services.ServicesState;
  form: FormStateMap,
  streets: ResourceState<Street>
}

// Whenever an action is dispatched, Redux will update each top-level application state property using
// the reducer with the matching name. It's important that the names match exactly, and that the reducer
// acts on the corresponding RootState property type.
export const reducers = {
  applicationHeader: ApplicationHeaderStore.reducer,
  application: ApplicationStore.reducer,
  services: Services.reducer,
  form: formReducer,
  streets: streetsResourceStore.reducer
};

// This type can be used as a hint on action creators so that its 'dispatch' and 'getState' params are
// correctly typed to match your store.
export interface AppThunkAction<TAction> {
  (dispatch: (action: TAction) => void, getState: () => RootState): void;
}

export type ActionCreatorsMapObject<T> = {
  [K in keyof T]: any;
}

export function createStore(){
  // Create browser history to use in the Redux store
  const baseUrl = document.getElementsByTagName('base')[0].getAttribute('href')!;
  const history = createBrowserHistory({basename: baseUrl});

  // Get the application-wide store instance, prepopulating with state from the server where available.
  const initialState = (window as any).initialReduxState as RootState;
  restoreMapsFromArrays(initialState);

  return configureStore(history, initialState);
}
