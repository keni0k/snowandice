import * as React from 'react';
import { Route } from 'react-router-dom';
import { wrap } from './components/Layout';
import { wrap as wrapRoot } from './pages/Root';
import MapPage from './pages/MapPage';
import StreetsPage from './pages/StreetsPage';
import { theme } from "./theme";
import { MuiThemeProvider } from "@material-ui/core";

export const routes = (
  <div className="route-content fill-parent app-section">
      <MuiThemeProvider theme={theme}>
        <Route
          path={['/routes']}
          component={wrap(wrapRoot(MapPage))}
        />
        <Route
          path={['/streets']}
          component={wrap(wrapRoot(StreetsPage))}
        />
      </MuiThemeProvider>
  </div>
);
