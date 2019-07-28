import * as React from 'react';
import * as ReactDOM from 'react-dom';
import { AppContainer, setConfig, cold } from 'react-hot-loader';
import { Provider } from 'react-redux';
import { Router } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import configureStore from './configureStore';
import { RootState, streetsResourceStore } from './store';
import * as RoutesModule from './routes';
import { restoreMapsFromArrays } from "./common/utils/system";
import {
  setApiAction,
  setClientInitializedAction
} from "./store/Application";
import config from "./config";
import MuiPickersUtilsProvider from "material-ui-pickers/MuiPickersUtilsProvider";
import MomentUtils from "@date-io/moment";
import { StreetsApi } from "./store/StreetsApi";
import * as mocks from './mocks'

setConfig({
  onComponentRegister: (type) =>
    (String(type).indexOf('useState') > 0 || String(type).indexOf('useEffect') > 0) && cold(type),
});

let routes = RoutesModule.routes;

// Create browser history to use in the Redux store
const baseUrl = document.getElementsByTagName('base')[0].getAttribute('href')!;
const history = createBrowserHistory({basename: baseUrl});

// Get the application-wide store instance, prepopulating with state from the server where available.
const initialState = (window as any).initialReduxState as RootState;
restoreMapsFromArrays(initialState);
const store = configureStore(history, initialState);

const api = new StreetsApi(config.backend);
store.dispatch(setApiAction(api));

/*const skillsResource = new Resource<SkillBlock, Store>(api, store, '/api/skills', 'skills');*/

/*store.dispatch(setResources({
  skills: skillsResource
}));*/

function renderApp() {
  // This code starts up the React app when it runs in a browser. It sets up the routing configuration
  // and injects the app into a DOM element.
  ReactDOM.render(
    <AppContainer>
      <MuiPickersUtilsProvider utils={MomentUtils}>
        <Provider store={ store }>
          <Router history={ history }>
            {routes}
          </Router>
        </Provider>
      </MuiPickersUtilsProvider>
    </AppContainer>,
    document.getElementById('react-app')
  );
}

renderApp();
store.dispatch(setClientInitializedAction(true));
store.dispatch(streetsResourceStore.actions.addArray(mocks.streets));
