import {
  applyMiddleware,
  combineReducers,
  compose,
  createStore,
  ReducersMapObject,
  Store,
  StoreEnhancer,
  StoreEnhancerStoreCreator,
} from 'redux';

import thunk from 'redux-thunk';
import { routerMiddleware, routerReducer } from 'react-router-redux';
import { RootState, reducers } from './store';
import { History } from 'history';
import logger from 'redux-logger';

export default function configureStore(history: History, initialState?: RootState) {
    // Build middleware. These are functions that can process the actions before they reach the store.
    const windowIfDefined = typeof window === 'undefined' ? null : window as any;
    // If devTools is installed, connect to it
    const devToolsExtension = windowIfDefined && windowIfDefined.devToolsExtension as () => StoreEnhancer;
    const createStoreWithMiddleware = compose(
        applyMiddleware(logger),
        applyMiddleware(thunk, routerMiddleware(history)),
        devToolsExtension ? devToolsExtension() : <S>(next: StoreEnhancerStoreCreator<S>) => next
    )(createStore);

    // Combine all reducers and instantiate the app-wide store instance
    const allReducers = buildRootReducer(reducers);
    const store = createStoreWithMiddleware(allReducers, initialState) as Store<RootState>;

    // Enable Webpack hot module replacement for reducers
    // @ts-ignore
  if (module.hot) {
    // @ts-ignore
    module.hot.accept('./store', () => {
          store.replaceReducer(buildRootReducer(require('./store').reducers.reducers));
        });
    }

    return store;
}

export function buildRootReducer(allReducers: ReducersMapObject) {
    return combineReducers<RootState>(Object.assign({}, allReducers, { routing: routerReducer }));
}
